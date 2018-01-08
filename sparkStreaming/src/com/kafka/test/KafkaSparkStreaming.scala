package com.kafka.test

import java.text.SimpleDateFormat
import java.util.Date

import kafka.serializer.StringDecoder
import org.apache.log4j.{Level, Logger}
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, Minutes, StreamingContext}
import org.apache.spark.{SparkContext, SparkConf}
import org.json4s.DefaultFormats
import org.json4s._
import org.json4s.jackson.JsonMethods._
/**
  * Created by root on 2016/7/22 0024.
  */
class KafkaSparkStreaming {

}
object StreamingConsumerExample {

  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    val kafkaParams = Map(
      "metadata.broker.list" -> "node5:9092,node6:9092,node7:9092,node7:9092",
      "zookeeper.connect" -> "node5:2181,node6:2181,node7:2181,node8:2181",
      "group.id" -> "1",
      "auto.offset.reset" -> "smallest" //需要读历史数据，也就是从头开始读
    )

    val zkQuorum: String = "node5:2181,node6:2181,node7:2181,node8:2181"
    val topics = "top1,top2"
    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("testkafka")
    val topicsSet = topics.split(",").toSet
    val sc  = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc, Seconds(10)) // 设定流处理的间隔，这里是1分钟

    //      ssc.checkpoint("hdfs://master.hadoop:9000/tmp")
//          val lines = KafkaUtils.createDirectStream(ssc, kafkaParams, topicsSet) //从kafka读取数据
    val lines = KafkaUtils.createStream(ssc,zkQuorum,"1",Map("top1"->1,"top2"->1))
    val result = lines.flatMap(_._2.split(" ")).map((_,1)).reduceByKey(_+_)
    result.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
