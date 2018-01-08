import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.classification.{LogisticRegressionWithSGD, SVMWithSGD}
import org.apache.spark.mllib.feature.{StandardScalerModel, StandardScaler}
import org.apache.spark.mllib.linalg.{Vectors, Vector}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by root on 2016/7/29 0029.
  */
class TestSVM {

}
object TestSVM {
  def main(args: Array[String]) {
    Logger.getLogger("org.apache").setLevel(Level.ERROR)
    //    val conf = new SparkConf().setAppName("test").setMaster("spark://192.168.57.4:7077")
    //      .setJars(List("D:\\课程\\spark课件\\testScala\\out\\artifacts\\testScala_jar\\testScala.jar"))
    val conf = new SparkConf().setAppName("test").setMaster("local")
    val sc = new SparkContext(conf)
    //     sc.textFile("D:\\课程\\spark\\spark项目第一天\\课程资料\\phoneme.dat")
    val trainData: RDD[LabeledPoint] = sc.textFile("D:\\课程\\spark\\spark项目第一天\\课程资料\\phoneme.dat").map(s => {
      val split = s.split(",")
      if (split.length == 6) {
        new LabeledPoint(split(5).toDouble, Vectors.dense(split.take(5).map(_.toDouble)))
      } else {
        null
      }
    }).filter(_ != null)
    val features: RDD[Vector] = trainData.map(_.features)
    val stand: StandardScalerModel = new StandardScaler(withMean = true, withStd = true).fit(features)
    val realTrain: RDD[LabeledPoint] = trainData.map(sample => {
      sample.copy(features = stand.transform(sample.features))
    }).cache()
    val model = SVMWithSGD.train(realTrain, 100, 0.83, 0.006)
    val acc = trainData.sample(false, 0.2, 1).map(sample => {
      val label: Double = sample.label
      val features = sample.features
      val predict = label.equals(model.predict(features)) match {
        case true => 1.0
        case false => 0.0
      }
      predict
    }).mean()
    print("SVMacc===========" + acc)

    val lrModel = LogisticRegressionWithSGD.train(realTrain, 100, 0.28)
    val lrAcc = trainData.sample(false, 0.2, 1).map(sample => {
      val label: Double = sample.label
      val features = sample.features
      val predict = label.equals(lrModel.predict(features)) match {
        case true => 1.0
        case false => 0.0
      }
      predict
    }).mean()
    print("LRacc===========" + lrAcc)
  }
}











import scala.util.Random

import com.github.fommil.netlib.BLAS.{getInstance => blas}

import org.apache.spark.annotation.DeveloperApi
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint


