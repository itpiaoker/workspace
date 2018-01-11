import org.apache.spark.{SparkConf, SparkContext}

/**
  * Title: 
  * Description: 
  *
  * @author lianxy
  * date 2017/6/16
  */
object UFOTest {
    def main(args: Array[String]) {
        val conf = new SparkConf().setAppName("aaa").setMaster("local[4]")
        val sc = new SparkContext(conf)




        val lines = sc.textFile("D:/R-workspace/data/ufo/ufo_awesome.tsv")
//        println(lines.count())
        val line = lines.map(_.split("\t"))
//        println(line.first())

        val filter = line.filter(f=>{
            f.length == 4 || f.length == 5
        })

        val filter2 = filter.map(x => {
            if(x.size == 5){
                x(3) = x(3).concat(x(4))
            }
        })


        filter.take(100).foreach(x => println(x))

//        val ufos = filter.map(x => UFO(x(0), x(1), x(2), x(3), x(4)))
//        ufos.take(100).foreach(x => println(x.date))






    }

    case class UFO(date: String, time: String, location: String, shortDesc: String, longDesc: String)

}
