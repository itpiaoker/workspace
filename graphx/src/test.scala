import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

class test {}

object test {
  def main(args: Array[String]) {
    Logger.getLogger("org.apache").setLevel(Level.ERROR)
    val conf = new SparkConf().setAppName("").setMaster("local")
    val sc = new SparkContext(conf)
    sc.textFile("D:\\课程\\spark\\spark项目第一天\\课程资料\\phoneme.dat")
    val trainData: RDD[LabeledPoint] = sc.textFile("phoneme.dat").map(s => {
      val split = s.split(",")
      val values = split(1).split(" ").map(_.toDouble)
      if (split.length == 6) {
        new LabeledPoint(split(5).toDouble, Vectors.dense(split.take(5).map(_.toDouble)))
      } else {
        null
      }
    }).filter(_ != null)

  }
}