import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Title: 
  * Description: 
  *
  * @author lianxy
  * @date 2017/4/12
  */
object Kmeanss2 {
    def main(args: Array[String]) {
        val conf = new SparkConf().setAppName("data").setMaster("local[4]")
        val sc = new SparkContext(conf)
        val base = "D:\\0A软件开发pdf核心丛书\\大数据\\spark\\spark高级数据分析\\data\\05\\"
        val rawData =  sc.textFile(base + "kddcup.data.corrected")
//        println(rawData.count())
//        rawData.map(_.split(',').last).countByValue().toSeq.sortBy(_._2).reverse.foreach(println)



        val labelsAndData = rawData.map(line=>{
            val buffer = line.split(',').toBuffer
            buffer.remove(1, 3)
            val label = buffer.remove(buffer.length-1)
            val vector = Vectors.dense(buffer.map(_.toDouble).toArray)
            (label, vector)
        })

        val data = labelsAndData.values.cache()
        val means = new KMeans()
        val model = means.run(data)
//        model.clusterCenters.foreach(println)

        val clusterLabelcount = labelsAndData.map {
            case (label, datum) =>
            val cluster = model.predict(datum)
            (cluster, label)
        }.countByValue()

        clusterLabelcount.toSeq.sorted.foreach{
            case ((cluster, label), count) =>
                println(f"$cluster%1s$label%18s$count%8s")
        }

//        labelsAndData.map((label,datum)=>{
//            val cluster = model.predict(datum)
//            (cluster, label)
//        })




    }


}
