import scala.io.Source

object Test01{
  def main(args: Array[String]){
    val file = Source.fromFile("C:\\Users\\itmoneys\\Desktop\\laji_mail.txt")
    for (line <- file.getLines){ println(line)}
    file.close
  }
}
