import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by itmoneys on 2016/9/15.
 */
object laji_mail {
  def main (args: Array[String]) {
    val sf = new SparkConf();
    sf.setAppName("aa")
    sf.setMaster("local")
    val sc = new SparkContext(sf);
    sc.textFile("H:\\尚学堂学习资料\\20160409-mr\\wc.txt").flatMap(_.split("\t")).foreach(println(_))




  }
}
