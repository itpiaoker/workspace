import org.apache.spark._
import org.apache.spark.mllib.recommendation.{ALS, Rating}

object CollaborativeFilter {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("CollaborativeFilter ")	//设置环境变量
    val sc = new SparkContext(conf)                                 //实例化环境
    val data = sc.textFile("D:\\0A软件开发pdf核心丛书\\大数据\\spark\\SPARK+MLLIB机器学习\\源代码\\C05\\u1.txt")								//设置数据集
    val ratings = data.map(_.split(' ') match {						//处理数据
    case Array(user, item, rate) => 							//将数据集转化
        Rating(user.toInt, item.toInt, rate.toDouble)				//将数据集转化为专用Rating
    })
    val rank = 2											//设置隐藏因子
    val numIterations = 2										//设置迭代次数
    val model = ALS.train(ratings, rank, numIterations, 0.01)			//进行模型训练
//    val rs = model.recommendProducts(2,5)						//为用户2推荐一个商品
//    rs.foreach(println)										//打印结果
    model.save(sc, "D:\\0A软件开发pdf核心丛书\\大数据\\spark\\SPARK+MLLIB机器学习\\源代码\\C05\\u1_model")


  }
}
