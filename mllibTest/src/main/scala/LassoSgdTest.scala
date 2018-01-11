import org.apache.spark.mllib.regression.{LassoWithSGD, LinearRegressionWithSGD}
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Title: 
  * Description: 
  *
  * @author lianxy
  * @date 2017/8/19
  */
object LassoSgdTest {
    def main(args: Array[String]) {
        val conf = new SparkConf()                                     //创建环境变量
            .setMaster("local")                                              //设置本地化处理
            .setAppName("LinearRegression ")                               //设定名称
        val sc = new SparkContext(conf)                                 //创建环境变量实例
        val trains = MLUtils.loadLibSVMFile(sc, "D:\\00-BIGDATA\\mllibTest\\data\\train_svm\\part-00000")
        val tests = MLUtils.loadLibSVMFile(sc, "D:\\00-BIGDATA\\mllibTest\\data\\train_svm\\part-00001")
        //        val model = LinearRegressionWithSGD.train(trains, 10)
//        val model = LinearRegressionWithSGD.train(trains, 20, 0.01, 0.1)
        val model = LassoWithSGD.train(trains, 10, 0.1, 0.1)

        println("weights = " + model.weights)
        println("intercept = " + model.intercept)

        val labels = tests.map(t => t.label)
        val predictions = tests.map(t => model.predict(t.features))
        val labelPredictions = labels.zip(predictions)
        val count = labelPredictions.count()
        val mse = labelPredictions.map(lp => Math.pow(lp._1 - lp._2, 2)).reduce(_ + _) / count
        println("mse = " + mse)


    }

}
