package flinkTest

import org.junit.Test

/**
 * Hello world!
 *
 */
object TableApiT {

    @Test
    def test22(): Unit={
//        val env = ExecutionEnvironment.getExecutionEnvironment
//        // for batch programs use ExecutionEnvironment instead of StreamExecutionEnvironment
////        val env = StreamExecutionEnvironment.getExecutionEnvironment
//
//        // create a TableEnvironment
//        val tableEnv = TableEnvironment.getTableEnvironment(env)
//
//        // register a Table
//        tableEnv.registerTable("table1", ...)           // or
//        tableEnv.registerTableSource("table2", ...)     // or
//        tableEnv.registerExternalCatalog("extCat", ...)
//
//        // create a Table from a Table API query
//        val tapiResult = tableEnv.scan("table1").select(...)
//        // Create a Table from a SQL query
//        val sqlResult  = tableEnv.sqlQuery("SELECT ... FROM table2 ...")
//
//        // emit a Table API result Table to a TableSink, same for SQL result
//        tapiResult.writeToSink(...)
//
//        // execute
//        env.execute()




    }




//  def main(args: Array[String]): Unit = {
  
//    val env = ExecutionEnvironment.getExecutionEnvironment
//    val text = env.fromElements(
//      "Who's there?",
//      "I think I hear them. Stand, ho! Who's there?")
//
//    val counts = text.flatMap { _.toLowerCase.split("\\W+") filter { _.nonEmpty } }
//            .map { (_, 1) }
//            .groupBy(0)
//            .sum(1)
//
//    counts.print()
    
//  }
}
