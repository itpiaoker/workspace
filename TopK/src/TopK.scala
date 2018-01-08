import org.apache.log4j.{Logger, Level}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by itmoneys on 2016/9/14.
 */
object TopK {
    def main(args: Array[String]){
        Logger.getLogger("org.apache").setLevel(Level.ERROR)
        val sc = new SparkContext(new SparkConf().setAppName("test").setMaster("local"))
        sc.textFile("H:\\尚学堂学习资料\\20160409-mr\\wc.txt").flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).sortBy(_._2,false,1).foreach(println(_))

    }
}