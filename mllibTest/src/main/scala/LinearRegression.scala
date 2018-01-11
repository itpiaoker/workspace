import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.{LinearRegressionModel, LabeledPoint, LinearRegressionWithSGD}
import org.apache.spark.{SparkConf, SparkContext}

object LinearRegression {
    val conf = new SparkConf()                                     //创建环境变量
        .setMaster("local")                                              //设置本地化处理
        .setAppName("LinearRegression ")                               //设定名称
    val sc = new SparkContext(conf)                                 //创建环境变量实例

    def main(args: Array[String]) {
//        val data = sc.textFile("D:\\00-BIGDATA\\mllibTest\\data\\SPSS与统计分析---宇传华\\data8-1.txt")							//获取数据集路径
//        val parsedData = data.map { line =>							//开始对数据集处理
//                val parts = line.split('\t')									//根据逗号进行分区
//                LabeledPoint(parts(1).toDouble, Vectors.dense(parts(0).split(' ').map(_.toDouble)))
//            }.cache()                                                     //转化数据格式


        //        val model = LinearRegressionWithSGD.train(parsedData, 10)	//建立模型
//        val model = LinearRegressionWithSGD.train(parsedData, 10, 0.3)	//建立模型
//        val model = LinearRegressionWithSGD.train(parsedData, 10, 0.3, 0.3)	//建立模型

//        val lr = new LinearRegression()



//        val wV = Vectors.dense(0.139)
//        val model = new LinearRegressionModel(wV, 1.662)
//        val l = parsedData.map(_.label)
//        val p = parsedData.map(l => model.predict(l.features))
//        val lp = l.zip(p)

//        val lrsgd = new LinearRegressionWithSGD()

//        println(model.weights)
//        println(model.intercept)
//        lp.foreach(x => println(x._1 + "\t" + x._2))




//        val result = model.predict(Vectors.dense(2))					//通过模型预测模型
//        println(result)											//打印预测结果
    }

}