import com.sun.org.apache.bcel.internal.generic.NEW
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Title: 
  * Description: 
  *
  * @author lianxy
  * date 2017/6/16
  */
object Test01 {


    def main(args: Array[String]) {
        val conf = new SparkConf().setAppName("aaa").setMaster("local[4]")
        val sc = new sparkcont(conf)
//        val memoryStatus = sc.getExecutorMemoryStatus.foreach(println(_))
        sc.getAllPools.foreach(println(_))

//        val storageStatus: Array[StorageStatus] = sc.getExecutorStorageStatus
//
//        storageStatus.foreach(x => println(x.diskUsed))
//        storageStatus.foreach(x => println(x.memRemaining))
//        storageStatus.foreach(x => println(x.memUsed))
//        storageStatus.foreach(x => println(x.maxMem))


                 if


        sc.startTime
//        val lines = sc.textFile("D:/R-workspace/data/ufo/ufo_awesome.tsv")
//        val linesPair = lines.map(x => (x, 1))
//        linesPair.join(linesPair)


//        lines.reduce()






//        println(lines.count())
//        println(lines.first())

//        val line = lines.map(_.split(","))
//        line.filter(f=>{
//
//        })

    }
}
