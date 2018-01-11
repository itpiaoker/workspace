import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.ml.feature.{IDF, HashingTF, Tokenizer}
import org.apache.spark.sql.{Row, SQLContext}

import scala.collection.immutable.HashMap

/**
  * Title: 
  * Description: 
  *
  * @author lianxy
  * date 2017/10/30
  */
object TF_IDF_Test {
    def main(args: Array[String]) {
        val conf = new SparkConf().setAppName("als").setMaster("local[*]")
        val sc = new SparkContext(conf)
        val sqlContext = new SQLContext(sc)
        val aline = sc.textFile("C:\\Users\\lianxy\\Desktop\\a.txt").filter(ss => ss != null && !ss.isEmpty)
//        val bline = sc.textFile("C:\\Users\\lianxy\\Desktop\\b.txt").filter(ss => ss != null && !ss.isEmpty)
//        val  zz = aline.zip(bline)
//        val seqs = aline.map(x => {
//            var arrs = Map[Int, String]()
//            arrs.+=(0 -> x._1)
//            arrs.+=(0 -> x._1)
//            arrs.toSeq
//        })

//        aline.foreach(println(_))


        val rows = aline.map(x =>{
            Row(x)
        })

        val schema = new StructType(
            Array(
                new StructField("sentence", StringType, true)
            )
        )

        val sentenceData = sqlContext.createDataFrame(rows, schema)
        val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
        val wordsData = tokenizer.transform(sentenceData)
//        wordsData.foreach {println}

        val hashingTF = new HashingTF().setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(2000)
        val featurizedData = hashingTF.transform(wordsData)
//        featurizedData.foreach {println}
        val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
        val idfModel = idf.fit(featurizedData)
        val rescaledData = idfModel.transform(featurizedData)
//        rescaledData.printSchema()
        rescaledData.select("words", "features").take(3).foreach(println)



    }

}
