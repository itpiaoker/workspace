package flinkTest

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.scala._
import org.junit.Test

/**
 * Hello world!
 *
 */
class SocketStreamT {

    @Test
    def test22(): Unit={
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        val text = env.socketTextStream("192.168.71.11", 9999)
    
        val counts = text.flatMap { _.toLowerCase.split("\\W+") filter { _.nonEmpty } }
                .map { (_, 1) }
                .keyBy(0)
                .timeWindow(Time.seconds(5))
                .sum(1)
    
        counts.print()
    
        env.execute("Window Stream WordCount")
    }

}
