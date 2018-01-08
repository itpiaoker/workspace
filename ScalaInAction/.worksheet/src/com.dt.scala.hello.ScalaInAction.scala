package com.dt.scala.hello
/**
 * Author: 	Wang Jialin
 * Contact Information:
 * 	WeChat:	18610086859
 *  QQ:		1740415547
 * 	Email: 18610086859@126.com
 *  Tel:	18610086859
 */
object ScalaInAction {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(245); 
  println("Welcome to the Scala worksheet");$skip(33); 
   val nums = new Array[Int](10);System.out.println("""nums  : Array[Int] = """ + $show(nums ));$skip(34); 
    val a = new Array[String](10);System.out.println("""a  : Array[String] = """ + $show(a ));$skip(35); 
   val s = Array("Hello", "World");System.out.println("""s  : Array[String] = """ + $show(s ));$skip(21); 
    s(0) = "Goodbye";$skip(6); val res$0 = 
    s
    import scala.collection.mutable.ArrayBuffer;System.out.println("""res0: Array[String] = """ + $show(res$0));$skip(79); 
    val b = ArrayBuffer[Int]();System.out.println("""b  : scala.collection.mutable.ArrayBuffer[Int] = """ + $show(b ));$skip(11); val res$1 = 
    b += 1;System.out.println("""res1: com.dt.scala.hello.ScalaInAction.b.type = """ + $show(res$1));$skip(22); val res$2 = 
    b += (1, 2, 3, 5);System.out.println("""res2: com.dt.scala.hello.ScalaInAction.b.type = """ + $show(res$2));$skip(27); val res$3 = 
    b ++= Array(8, 13, 21);System.out.println("""res3: com.dt.scala.hello.ScalaInAction.b.type = """ + $show(res$3));$skip(6); val res$4 = 
    b;System.out.println("""res4: scala.collection.mutable.ArrayBuffer[Int] = """ + $show(res$4));$skip(23); 
    
    b.trimEnd(5);$skip(6); val res$5 = 
    b;System.out.println("""res5: scala.collection.mutable.ArrayBuffer[Int] = """ + $show(res$5));$skip(19); 
    b.insert(2, 6);$skip(6); val res$6 = 
    b;System.out.println("""res6: scala.collection.mutable.ArrayBuffer[Int] = """ + $show(res$6));$skip(25); 
    b.insert(2, 7, 8, 9);$skip(6); val res$7 = 
    b;System.out.println("""res7: scala.collection.mutable.ArrayBuffer[Int] = """ + $show(res$7));$skip(16); val res$8 = 
    b.remove(2);System.out.println("""res8: Int = """ + $show(res$8));$skip(6); val res$9 = 
    b;System.out.println("""res9: scala.collection.mutable.ArrayBuffer[Int] = """ + $show(res$9));$skip(19); 
    b.remove(2, 3);$skip(6); val res$10 = 
    b;System.out.println("""res10: scala.collection.mutable.ArrayBuffer[Int] = """ + $show(res$10));$skip(14); val res$11 = 
    b.toArray;System.out.println("""res11: Array[Int] = """ + $show(res$11));$skip(6); val res$12 = 
    b;System.out.println("""res12: scala.collection.mutable.ArrayBuffer[Int] = """ + $show(res$12));$skip(60); 
  for (i <- 0 until a.length)
    	println(i + ": " + a(i));$skip(31); 

val c = Array(2, 3, 5, 7, 11);System.out.println("""c  : Array[Int] = """ + $show(c ));$skip(49); 
    val result = for (elem <- c) yield  2 * elem;System.out.println("""result  : Array[Int] = """ + $show(result ));$skip(49); val res$13 = 

for (elem <- c if elem % 2 == 0) yield 2 * elem;System.out.println("""res13: Array[Int] = """ + $show(res$13));$skip(32); val res$14 = 
c.filter(_ % 2 == 0).map(2 * _);System.out.println("""res14: Array[Int] = """ + $show(res$14));$skip(23); val res$15 = 

Array(1, 7, 2, 9).sum;System.out.println("""res15: Int = """ + $show(res$15));$skip(55); val res$16 = 

ArrayBuffer("Mary", "had", "a", "little", "lamb").max;System.out.println("""res16: String = """ + $show(res$16));$skip(32); 
val d = ArrayBuffer(1, 7, 2, 9);System.out.println("""d  : scala.collection.mutable.ArrayBuffer[Int] = """ + $show(d ));$skip(27); 
    val bSorted = d.sorted;System.out.println("""bSorted  : scala.collection.mutable.ArrayBuffer[Int] = """ + $show(bSorted ));$skip(27); 

val e = Array(1, 7, 2, 9);System.out.println("""e  : Array[Int] = """ + $show(e ));$skip(32); 
scala.util.Sorting.quickSort(e);$skip(2); val res$17 = 
e;System.out.println("""res17: Array[Int] = """ + $show(res$17));$skip(21); val res$18 = 

e.mkString(" and ");System.out.println("""res18: String = """ + $show(res$18));$skip(26); val res$19 = 
e.mkString("<", ",", ">");System.out.println("""res19: String = """ + $show(res$19));$skip(41); 


val matrix = Array.ofDim[Double](3, 4);System.out.println("""matrix  : Array[Array[Double]] = """ + $show(matrix ));$skip(22); 
    matrix(2)(1) = 42;$skip(11); val res$20 = 
    matrix;System.out.println("""res20: Array[Array[Double]] = """ + $show(res$20));$skip(45); 
    val triangle = new Array[Array[Int]](10);System.out.println("""triangle  : Array[Array[Int]] = """ + $show(triangle ));$skip(81); 
    	for (i <- 0 until triangle.length)
    	triangle(i) = new Array[Int](i + 1);$skip(14); val res$21 = 
    	triangle;System.out.println("""res21: Array[Array[Int]] = """ + $show(res$21));$skip(38); 



val symbols = Array("[", "-", "]");System.out.println("""symbols  : Array[String] = """ + $show(symbols ));$skip(28); 
  val counts = Array(2,5,2);System.out.println("""counts  : Array[Int] = """ + $show(counts ));$skip(34); 
  val pairs = symbols.zip(counts);System.out.println("""pairs  : Array[(String, Int)] = """ + $show(pairs ));$skip(40); 
for ((x,y) <- pairs) Console.print(x*y);$skip(51); 


val map = Map("book"->10,"gun"->18,"ipad"->1000);System.out.println("""map  : scala.collection.immutable.Map[String,Int] = """ + $show(map ));$skip(36); val res$22 = 
for((k,v) <- map) yield (k,v * 0.9);System.out.println("""res22: scala.collection.immutable.Map[String,Double] = """ + $show(res$22));$skip(88); 

val scores = scala.collection.mutable.Map("Scala" -> 7, "Hadoop" -> 8, "Spark" -> 10 );System.out.println("""scores  : scala.collection.mutable.Map[String,Int] = """ + $show(scores ));$skip(51); 
  val hadoopScore = scores.getOrElse("Hadooop", 0);System.out.println("""hadoopScore  : Int = """ + $show(hadoopScore ));$skip(23); val res$23 = 


scores += ("R" -> 9);System.out.println("""res23: com.dt.scala.hello.ScalaInAction.scores.type = """ + $show(res$23));$skip(21); val res$24 = 
  scores -= "Hadoop";System.out.println("""res24: com.dt.scala.hello.ScalaInAction.scores.type = """ + $show(res$24));$skip(100); 
val sortedScore = scala.collection.immutable.SortedMap("Scala" -> 7, "Hadoop" -> 8, "Spark" -> 10 );System.out.println("""sortedScore  : scala.collection.immutable.SortedMap[String,Int] = """ + $show(sortedScore ));$skip(39); 

val tuple =(1,2,3.14,"Rocky","Spark");System.out.println("""tuple  : (Int, Int, Double, String, String) = """ + $show(tuple ));$skip(23); 
  val third = tuple._3;System.out.println("""third  : Double = """ + $show(third ));$skip(49); 


val (first,second,thirda,fourth,fifth) = tuple;System.out.println("""first  : Int = """ + $show(first ));System.out.println("""second  : Int = """ + $show(second ));System.out.println("""thirda  : Double = """ + $show(thirda ));System.out.println("""fourth  : String = """ + $show(fourth ));System.out.println("""fifth  : String = """ + $show(fifth ));$skip(34); 




val (f1, s2, _, _, _) = tuple;System.out.println("""f1  : Int = """ + $show(f1 ));System.out.println("""s2  : Int = """ + $show(s2 ));$skip(36); val res$25 = 

"Rocky Spark".partition(_.isUpper)


import scala.util.matching._;System.out.println("""res25: (String, String) = """ + $show(res$25));$skip(69); 


val regex="""([0-9]+) ([a-z]+)""".r;System.out.println("""regex  : scala.util.matching.Regex = """ + $show(regex ));$skip(32); 
    val line = "20150623 Spark";System.out.println("""line  : String = """ + $show(line ));$skip(32); 
    val regex(num, blog) = line;System.out.println("""num  : String = """ + $show(num ));System.out.println("""blog  : String = """ + $show(blog ))}






}
