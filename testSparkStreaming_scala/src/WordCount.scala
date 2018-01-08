import org.apache.log4j.{Level, Logger}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by root on 2016/7/24 0024.
  */
class WordCount {

}
object WordCount{
  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    val conf = new SparkConf().setAppName("test").setMaster("local[2]")
//    val ssc = new StreamingContext(conf,Seconds(5))
//     val input: ReceiverInputDStream[String] = ssc.socketTextStream("master",9999,StorageLevel.MEMORY_AND_DISK)
//      val word: DStream[(String, Int)] = input.flatMap(_.split(" ")).map((_,1))
////        val res: DStream[(String, Int)] = word.transform(x=>{
////          x
////        })
////        word.reduceByKey(_+_).print()



    val ssc = new StreamingContext(conf, Seconds(2))
    ssc.checkpoint("d:/sparkStreamOut")

    val updateFunc = (values: Seq[Int], state: Option[Int]) => {
      val currentCount = values.sum
      val previousCount = state.getOrElse(0)
      //print(currentCount)
      //print(previousCount)
      Some(currentCount + previousCount)
    }
    val lines =ssc.socketTextStream("node5",9999,StorageLevel.MEMORY_AND_DISK)
    val words = lines.flatMap(_.split(" ")).map(x => (x, 1))
    val stateDstream: DStream[(String, Int)] = words.updateStateByKey[Int](updateFunc)
    stateDstream.print()
    ssc.start()
    ssc.awaitTermination()
  }
}