import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

object Kmeanss {
  def main(args: Array[String]) {

val conf = new SparkConf()                                     //创建环境变量
        .setMaster("local")                                             //设置本地化处理
        .setAppName("Kmeans ")                              		//设定名称
    val sc = new SparkContext(conf)                                 //创建环境变量实例
      val lines = sc.textFile("D:\\00-BIGDATA\\mllibTest\\src\\Kmeans.txt")
      val lineRDD = lines.map(line=>{
          Vectors.dense(line.split(' ').map(x=>{x.toDouble}))
      })
//    val data = MLUtils.loadLibSVMFile(sc, "D:\\00-BIGDATA\\mllibTest\\src\\Kmeans.txt")			//输入数据集
//    val parsedData = data.map(s => Vectors.dense(s.split(' ').map(x=>{x.toDouble}))).cache()												//数据处理

//      val aa = data.map(l=>{
//          l.features
//      })


    val numClusters = 2										//最大分类数
    val numIterations = 20									//迭代次数
    val model = KMeans.train(lineRDD, numClusters, numIterations)	//训练模型
    model.clusterCenters.foreach(println)							//打印中心点坐标

  }
}
