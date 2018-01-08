//
///*
// * ����൱��������һ����������
// * aa  88
// * bb  75
// * cc  56
// * aa  25
// * bb  45
// * cc  10
// * ���ݴ�hbase��ȡ����������������һ����������Щ�������ͬһ�������ݵ����ֵ����Сֵ��ƽ��ֵ
// *
// *
// * */
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Iterator;
//import java.util.List;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.HBaseConfiguration;
//import org.apache.hadoop.hbase.client.Result;
//import org.apache.hadoop.hbase.client.Scan;
//import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
//import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
//import org.apache.hadoop.hbase.protobuf.ProtobufUtil;
//import org.apache.hadoop.hbase.protobuf.generated.ClientProtos;
//import org.apache.hadoop.hbase.util.Base64;
//import org.apache.hadoop.hbase.util.Bytes;
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.FlatMapFunction;
//import org.apache.spark.api.java.function.Function;
//import org.apache.spark.api.java.function.PairFunction;
//import org.apache.spark.api.java.function.VoidFunction;
//import org.apache.spark.sql.functions;
//
//import scala.Tuple2;
//
//public class MREquipTemp {
//
//	private static final Log LOG = LogFactory.getLog(MREquipTemp.class);
//
//	public static void main(String[] args) {
//		// System.setProperty("hadoop.home.dir", "...");
//		SparkConf conf = new SparkConf().setAppName("MREquipment").setMaster(
//				"local");
//		final JavaSparkContext sc = new JavaSparkContext(conf);
//		Configuration hconf = HBaseConfiguration.create();
//		hconf.set("hbase.zookeeper.property.clientPort", "2181");
//		hconf.set("hbase.zookeeper.quorum", "hh1");
//		Scan scan = new Scan();
//		scan.addFamily(Bytes.toBytes("thermometerTest"));
//		scan.addColumn(Bytes.toBytes("thermometerTest"), Bytes.toBytes("type"));
//		scan.addColumn(Bytes.toBytes("thermometerTest"), Bytes.toBytes("WT"));
//		try {
//			ClientProtos.Scan proto = ProtobufUtil.toScan(scan);
//			String tableName = "thermometer";
//			LOG.info("��ȡHbase�ı�Ϊ��" + tableName);
//			hconf.set(TableInputFormat.INPUT_TABLE, tableName);
//			String scanToString = Base64.encodeBytes(proto.toByteArray());
//			hconf.set(TableInputFormat.SCAN, scanToString);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.err.println("4");
//		// ����ȡ����������ת����rdd
//		JavaPairRDD<ImmutableBytesWritable, Result> hbaseRDD = sc
//				.newAPIHadoopRDD(hconf, TableInputFormat.class,
//						ImmutableBytesWritable.class, Result.class);
//		System.err.println(hbaseRDD.count());
//
//		// ��ȡ����������� ��aa 88 aa-type 88 - WT
//		JavaRDD<String> equRdd = hbaseRDD
//				.map(new Function<Tuple2<ImmutableBytesWritable, Result>, String>() {
//					@Override
//					public String call(Tuple2<ImmutableBytesWritable, Result> t)
//							throws Exception {
//						String type = Bytes.toString(t._2.getValue(
//								"thermometerTest".getBytes(), "type".getBytes()));
//						String tmprature = Bytes.toString(t._2.getValue(
//								"thermometerTest".getBytes(), "WT".getBytes()));
//						return type + " " + tmprature;
//					}
//				});
//
//		JavaPairRDD<String, String> pairs = equRdd
//				.mapToPair(new PairFunction<String, String, String>() {
//					@Override
//					public Tuple2<String, String> call(String str)
//							throws Exception {
//						String[] s = str.split(" ");
//						return new Tuple2<String, String>(s[0], s[1]);
//					}
//				});
//
//		// ����ֵΪ��null�����ַ���
//		JavaPairRDD<String, String> filtrRDD = pairs
//				.filter(new Function<Tuple2<String, String>, Boolean>() {
//					@Override
//					public Boolean call(Tuple2<String, String> t)
//							throws Exception {
//						if (t._2 == null || "null".equals(t._2)) {
//							return false;
//						}
//						System.err.println("12");
//						return true;
//					}
//				});
//
//		// �Թ��˺�����ݽ��з��飬��ͬһ�����ͷֳ�һ��
//		JavaPairRDD<String, Iterable<String>> group = filtrRDD.groupByKey();
//
//		// ������������ӳ���Ԫ��ӣ�
//		FlatMapFunction<Tuple2<String, Iterable<String>>, Tuple2<String, String>> flatMapFunction = new FlatMapFunction<Tuple2<String, Iterable<String>>, Tuple2<String, String>>() {
//			@Override
//			public Iterable<Tuple2<String, String>> call(
//					Tuple2<String, Iterable<String>> tuple2) throws Exception {
//				List<Tuple2<String, String>> list = new ArrayList<Tuple2<String, String>>();
//				List<String> aa = new ArrayList<String>();
//				String key = tuple2._1();
//
//				System.err.println("key:" + key);
//				Iterable<String> ss = tuple2._2();
//
//				Iterator<String> iterator = ss.iterator();
//
//				System.err.println("iterator:" + iterator.toString());
//
//				if (iterator != null) {
//					while (iterator.hasNext()) {
//
//						String str = (String) iterator.next();
//						aa.add(str);
//					}
//				}
//				System.err.println("���ֵ:" + " " + Collections.max(aa));
//				System.err.println("��Сֵ:" + " " + Collections.min(aa));
//				return list;
//			}
//		};
//		group.flatMap(flatMapFunction);
//
//		/*
//		 * group.foreach(new VoidFunction<Tuple2<String,Iterable<String>>>() {
//		 *
//		 * @Override public void call(Tuple2<String, Iterable<String>> t) throws
//		 * Exception { Iterator<String> it = t._2.iterator(); Double sum = 0.0;
//		 * long index=0; while(it.hasNext()){ index++;
//		 * sum+=Double.valueOf(it.next()); }
//		 * System.out.println(t._1+"---"+(sum/index)); } });
//		 */
//		sc.close();
//	}
//}
