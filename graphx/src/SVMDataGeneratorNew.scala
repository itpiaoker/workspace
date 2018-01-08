
import com.github.fommil.netlib.BLAS
import org.apache.spark.SparkContext
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import com.github.fommil.netlib.BLAS.{getInstance => blas}

import scala.util.Random

/**
  * Created by root on 2016/7/29 0029.
  */
class SVMDataGeneratorNew {

}
object SVMDataGeneratorNew{
  def main(args: Array[String]) {
    if (args.length < 2) {
      println("Usage: SVMGenerator " +
        "<master> <output_dir> [num_examples] [num_features] [num_partitions]")
      System.exit(1)
    }

    val sparkMaster: String = args(0)
    val outputPath: String = args(1)
    val nexamples: Int = if (args.length > 2) args(2).toInt else 1000
    val nfeatures: Int = if (args.length > 3) args(3).toInt else 2
    val parts: Int = if (args.length > 4) args(4).toInt else 2

    val sc = new SparkContext(sparkMaster, "SVMGenerator")

    val globalRnd = new Random(94720)
    val trueWeights = Array.fill[Double](nfeatures)(globalRnd.nextGaussian())

    val data: RDD[LabeledPoint] = sc.parallelize(0 until nexamples, parts).map { idx =>
      val rnd = new Random(42 + idx)

      val x = Array.fill[Double](nfeatures) {
        rnd.nextDouble() * 2.0 - 1.0
      }
      val yD = blas.ddot(trueWeights.length, x, 1, trueWeights, 1) + rnd.nextGaussian() * 0.1
      val y = if (yD < 0) 0.0 else 1.0
      LabeledPoint(y, Vectors.dense(x))
    }

    data.saveAsTextFile(outputPath)

    sc.stop()
  }
}
