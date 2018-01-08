/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package storm.starter.trident;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.generated.StormTopology;
import backtype.storm.task.IMetricsContext;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.CombinerAggregator;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.builtin.MapGet;
import storm.trident.operation.builtin.Sum;
import storm.trident.state.ReadOnlyState;
import storm.trident.state.State;
import storm.trident.state.StateFactory;
import storm.trident.state.map.ReadOnlyMapState;
import storm.trident.tuple.TridentTuple;

import java.util.*;

public class TridentReach {
  public static Map<String, List<String>> TWEETERS_DB = new HashMap<String, List<String>>() {{
    put("foo.com/blog/1", Arrays.asList("sally", "bob", "tim", "george", "nathan"));
    put("engineering.twitter.com/blog/5", Arrays.asList("adam", "david", "sally", "nathan"));
    put("tech.backtype.com/blog/123", Arrays.asList("tim", "mike", "john"));
  }};

  public static Map<String, List<String>> FOLLOWERS_DB = new HashMap<String, List<String>>() {{
    put("sally", Arrays.asList("bob", "tim", "alice", "adam", "jim", "chris", "jai"));
    put("bob", Arrays.asList("sally", "nathan", "jim", "mary", "david", "vivian"));
    put("tim", Arrays.asList("alex"));
    put("nathan", Arrays.asList("sally", "bob", "adam", "harry", "chris", "vivian", "emily", "jordan"));
    put("adam", Arrays.asList("david", "carissa"));
    put("mike", Arrays.asList("john", "bob"));
    put("john", Arrays.asList("alice", "nathan", "jim", "mike", "bob"));
  }};

  public static class StaticSingleKeyMapState extends ReadOnlyState implements ReadOnlyMapState<Object> {
    public static class Factory implements StateFactory {
      Map _map;

      public Factory(Map map) {
        _map = map;
      }

      @Override
      public State makeState(Map conf, IMetricsContext metrics, int partitionIndex, int numPartitions) {
        return new StaticSingleKeyMapState(_map);
      }

    }

    Map _map;

    public StaticSingleKeyMapState(Map map) {
      _map = map;
    }


    @Override
    public List<Object> multiGet(List<List<Object>> keys) {
      List<Object> ret = new ArrayList();
      for (List<Object> key : keys) {
        Object singleKey = key.get(0);
        ret.add(_map.get(singleKey));
      }
      return ret;
    }

  }

  public static class One implements CombinerAggregator<Integer> {
    @Override
    public Integer init(TridentTuple tuple) {
      return 1;
    }

    @Override
    public Integer combine(Integer val1, Integer val2) {
      return 1;
    }

    @Override
    public Integer zero() {
      return 1;
    }
  }

  public static class ExpandList extends BaseFunction {

    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
      List l = (List) tuple.getValue(0);
      if (l != null) {
        for (Object o : l) {
          collector.emit(new Values(o));
        }
      }
    }

  }

  public static StormTopology buildTopology(LocalDRPC drpc) {
    TridentTopology topology = new TridentTopology();
    //封装静态数据map
    TridentState urlToTweeters = topology.newStaticState(new StaticSingleKeyMapState.Factory(TWEETERS_DB));
    TridentState tweetersToFollowers = topology.newStaticState(new StaticSingleKeyMapState.Factory(FOLLOWERS_DB));
    //这里的reach是注册在drpc上的函数名，为了后面的drpcclient能够通过这个名字发送请求数据，比如foo.com/blog/1
    //查询，用前面传过来的字符串在urlToTweeters进行查询，结果Arrays.asList("sally", "bob", "tim", "george", "nathan")
    topology.newDRPCStream("reach", drpc).stateQuery(urlToTweeters, new Fields("args"), new MapGet(), new Fields(
            //这里是通过一个展开算子来展开list得到每一个元素，比如"sally"  "bob" 等等
        "tweeters")).each(new Fields("tweeters"), new ExpandList(), new Fields("tweeter")).shuffle().stateQuery(
            //根据tweeter得到每个tweeter的followers，比如  put("sally", Arrays.asList("bob", "tim", "alice", "adam", "jim", "chris", "jai"));
        tweetersToFollowers, new Fields("tweeter"), new MapGet(), new Fields("followers")).each(new Fields("followers"),
            //展开每个tweeter的followers，bob" "tim" "alice" "adam"
        new ExpandList(), new Fields("follower")).groupBy(new Fields("follower")).aggregate(new One(), new Fields(
            //按照follower来group之后等于是去重，每个分组不管几个人都是new One()，进行聚合累加，得到每个url的受众
        "one")).aggregate(new Fields("one"), new Sum(), new Fields("reach"));
    return topology.build();
  }

  public static void main(String[] args) throws Exception {
    LocalDRPC drpc = new LocalDRPC();

    Config conf = new Config();
    LocalCluster cluster = new LocalCluster();

    cluster.submitTopology("reach", conf, buildTopology(drpc));

    Thread.sleep(2000);

    System.out.println("REACH: " + drpc.execute("reach", "aaa"));
    System.out.println("REACH: " + drpc.execute("reach", "foo.com/blog/1"));
    System.out.println("REACH: " + drpc.execute("reach", "engineering.twitter.com/blog/5"));


    cluster.shutdown();
    drpc.shutdown();
  }
}
