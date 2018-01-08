import org.apache.spark.sql.SQLContext
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by root on 2016/7/24 0024.
  */
class TestTransform {

}
object TestTransform{
  case class Status(avg:Double = 0.0, count:Int = 0) {
    var countTrend = 0.0
    var avgTrend = 0.0
    def %(prev:Status): Status = {
      if (prev.count > 0) {
        this.countTrend = (this.count - prev.count).toDouble / prev.count
      }
      if (prev.avg > 0) {
        this.avgTrend = (this.avg - prev.avg) / prev.avg
      }
      this
    }
    override def toString = {
      s"Trend($avg, $count, $avgTrend, $countTrend)"
    }
  }
  def updatestatefunc(newValue: Seq[Status], oldValue: Option[Status]): Option[Status] = {
    val prev = oldValue.getOrElse(Status())
    val current = if (newValue.size > 0) newValue.last % prev else Status()
    Some(current)
  }
  def main(args: Array[String]) {

    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("test")
    val sc  = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc, Seconds(10))
    ssc.checkpoint("E:/test123")
    val sqc = new SQLContext(sc)
    import sqc._
    val lines = ssc.socketTextStream("master", 9999)
    lines.transform( rdd => {
      if (rdd.count > 0) {
        sqc.jsonRDD(rdd).registerTempTable("test")
        val sqlreport = sqc.sql("SELECT message, COUNT(message) AS host_c, AVG(lineno) AS line_a FROM logstash WHERE path = '/var/log/system.log' AND lineno > 70 GROUP BY message ORDER BY host_c DESC LIMIT 100")
        sqlreport.map(r => (r(0).toString -> Status(r(2).toString.toDouble, r(1).toString.toInt)))
      } else {
        rdd.map(l => ("" -> Status()))
      }
    }).updateStateByKey(updatestatefunc).print()
    ssc.start()
    ssc.awaitTermination()
  }
}
