package bagapps.histogram


import scala.collection.mutable.{HashBag => Histogram}
import scala.collection.BagPredef._

object Main {

  def main(args: Array[String]) {

    val LIMIT = 50

    // Create empty histogram
    val histogram = Histogram.empty[Int]

    // Add every element to the histogram
    (1 to LIMIT * LIMIT).foreach(_ => histogram += (Math.random() * LIMIT).toInt)


    // Print the contents of the histogram
    println(
      s"""
        |Histogram of (Math.random() * LIMIT).toInt over ${LIMIT * LIMIT} executions
        |=================================================================
        | maximum occurrences: ${histogram.maxMultiplicity}
        | minimum occurrences: ${histogram.minMultiplicity}
        |=================================================================
        |${
        (0 until LIMIT).map {
          i =>
            val occurrences = histogram.multiplicity(i)
            s"$i:".padTo(4, ' ') + "".padTo(occurrences, '|') + s"  $occurrences"
        }.mkString("\n")
      }
        |================================================================
        """.stripMargin)


  }

}
