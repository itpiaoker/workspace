import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.mllib.linalg.distributed.RowMatrix
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Title: 
  * Description: 
  *
  * @author lianxy
  * date 2017/8/2
  */
object BayesSex {

    def main(args: Array[String]) {
        val conf = new SparkConf()
        conf.setAppName("11111111111")
        conf.setMaster("local[4]")
        val sc = new SparkContext(conf)
//        val RDD[Map] = null
//        val label = MLUtils.loadLibSVMFile(sc, "D:\\00-BIGDATA\\mllibTest\\src\\bayesSex.txt")
        val label = MLUtils.loadLabeledPoints(sc, "D:\\00-BIGDATA\\mllibTest\\src\\bayesSex.txt")









//        val vec = label.map(_.features)
//        val row = new RowMatrix(vec)
//        val m = row.computePrincipalComponents(3)


//        val vec = label.map(_.features)
//        val rs = Statistics.chiSqTest(label)
//
//        rs.foreach(r=>{
//            print(r.method + "\t\t")
//            print(r.degreesOfFreedom + "\t\t")
//            print(r.pValue + "\t\t")
//            print(r.statistic + "\t\t")
//            print(r.nullHypothesis + "\t\t")
//            println("=====================")
//        })


//        val x = label.map(_.features).map(_.toArray(0))
//        val y = label.map(_.features).map(_.toArray(1))
//
////        Statistics.corr(x, y, "pearson")
//        val a = Statistics.corr(x, y)
//        println(a)

//        val data = label.randomSplit(Array(0.6, 0.4), seed = 11L)
//        val trainData = data(0)
//        val testData = data(1)
//
//        val model = NaiveBayes.train(trainData)
//        val predict = testData.map(t => model.predict(t.features))
//        val predictAndLeabel  = predict.zip(testData.map(t => t.label))
//        predictAndLeabel.foreach(x => println(x._2 + "\t" + x._1))


//        val summary = Statistics.colStats(label.map(_.features))
//        println(summary.normL1)
//        println(summary.normL2)
//        println(summary.mean)
//        println(summary.count)
//        println(summary.max)
//        println(summary.min)
//        println(summary.numNonzeros)
//        println(summary.variance)





    }
}
