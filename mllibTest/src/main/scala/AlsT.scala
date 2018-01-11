import java.util

import org.apache.commons.lang3.StringUtils
import org.apache.spark.mllib.recommendation.{ALS, Rating}
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Title: 
  * Description: 
  *
  * @author lianxy
  * date 2017/10/30
  */
object AlsT {

    def main(args: Array[String]) {
        val conf = new SparkConf().setAppName("als").setMaster("local[*]")
        val sc = new SparkContext(conf)
        val line = sc.textFile("D:\\大数据学习资料\\20160409-mr\\tianchi\\sam_tianchi_2014002_rec_tmall_log.txt")
        val lines = line.map(_.split("\t"))
//          lines.map(a => a(2)).distinct().foreach(println(_))
        val pinFen = lines.map(l=>{
            val ss = new util.ArrayList[String]()
            ss.add(l(0))
            ss.add(l(1))
            ss.add(l(2))
            ss.add(l(3))

            l(2) match {

                case "click" =>{
                    ss.add("1")
                }
                case "alipay" =>{
                    ss.add("20")
                }
                case "cart" =>{
                    ss.add("13")
                }
                case "collect" =>{
                    ss.add("7")
                }
                case _ =>{}
            }


            new Rating(ss.get(1).substring(1, ss.get(1).length).toInt, ss.get(0).substring(1, ss.get(0).length).toInt, ss.get(4).toDouble)

        })



        val model = ALS.train(pinFen, 2, 10)
//        model.recommendProducts(3278, 10).foreach(println(_))
        model.productFeatures.foreach(x => {
            println(x._1 + "\t" +x._2(0) + "\t" + x._2(1))
        })




    }
}
