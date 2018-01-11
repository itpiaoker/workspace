import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.random.RandomRDDs

/**
  * Title: 
  * Description: 
  *
  * @author lianxy
  * date 2017/8/18
  */
object MockData {

    def main(args: Array[String]) {

//        val conf = new SparkConf()
//        conf.setAppName("aaa")
//        conf.setMaster("local[4]")
//        val sc = new SparkContext(conf)
//        //    val rdd = RandomRDDs.normalRDD(sc, 1000000L, 2)
//        val rdd = RandomRDDs.normalVectorRDD(sc, 1000000L, 2, 2)
//        //    val arrs = rdd.map(f => f.toArray)
//        //    arrs.map(a => LabeledPoint(a(0), f(1)))
//
//        val rddStr = rdd.map(f => f(0) + "\t" + f(1))
//        val l = rdd.map(f => LabeledPoint(f(0), Vectors.dense(f(1))))
//        MLUtils.saveAsLibSVMFile(l, "D:\\00-BIGDATA\\mllibTest\\data\\train_svm")
//        rddStr.saveAsTextFile("D:\\00-BIGDATA\\mllibTest\\data\\train_str")















    }

}
