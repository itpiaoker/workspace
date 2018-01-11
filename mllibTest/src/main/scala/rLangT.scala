//import org.rosuda.REngine.Rserve.RConnection
//
///**
//  * Title:
//  * Description:
//  *
//  * @author lianxy
//  * date 2017/8/21
//  */
//object rLangT {
//
//    def main(args: Array[String]) {
//        val connection = new RConnection("localhost", 5011)
//        val vetor = "c(1,2,3,4)"
//        val mean = connection.eval("meanVal<-mean(" + vetor + ")").asString()
//        System.out.println("the mean of given vector is="+ mean)
//
//        connection.eval("source('D:/R-workspace/test/myAdd.R')")
//
//        val num1=20
//        val num2=10
//        val sum=connection.eval("myAdd("+num1+","+num2+")").asInteger()
//        System.out.println("the sum="+sum)
//
//
//
//
//
//
//
//
//    }
//
//}
