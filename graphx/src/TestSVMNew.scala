import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.classification.{SVMModel, SVMWithSGD}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by root on 2016/7/30 0030.
  */
class TestSVMNew {

}
object TestSVMNew{
  def main(args: Array[String]) {
    Logger.getLogger("org.apache").setLevel(Level.ERROR)
    val conf = new SparkConf().setAppName("test").setMaster("local")
    val sc = new SparkContext(conf)
//    val trainData: RDD[LabeledPoint] = MLUtils.loadLabeledPoints(sc,"D:\\课程\\spark\\spark第五天\\课程资料\\test\\part-00000")
//    val testData: RDD[LabeledPoint] = MLUtils.loadLabeledPoints(sc,"D:\\课程\\spark\\spark第五天\\课程资料\\testdata.txt")
//    val labels = testData.map(_.label)
//    val features = testData.map(_.features)
//    val model: SVMModel = SVMWithSGD.train(trainData,10)
//    val acc = model.predict(features).zip(labels).filter(x=>{x._1.equals(x._2)}).count()/labels.count().toDouble
//    println("acc============"+acc)
    val trainData = sc.textFile("D:\\0A尚学堂学习资料\\20160730-Graphx+MLlib\\phoneme\\phoneme.dat").map(_.split(","))
    val train: RDD[LabeledPoint] = trainData.map(sample=>{
      val label: Double = sample(5).toDouble
      val vector = Vectors.dense(sample.take(5).map(_.toDouble))
      new LabeledPoint(label,vector)
    })
    val model: SVMModel = SVMWithSGD.train(train,100,0.8,0.005)
        val acc = model.predict(train.map(_.features)).zip(train.map(_.label)).filter(x=>{x._1.equals(x._2)}).count()/train.map(_.label).count().toDouble
        println("acc============"+acc)
  }

}
