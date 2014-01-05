package bagapps.histogram


object Main {

  def main(args: Array[String]) {

    // Import HashBag and rename it to Histogram
    import scala.collection.mutable.{HashBag => Histogram}

    // Configuration for the bag using multiplicities of Int elements on default equivalence
    implicit val bagConfiguration = Histogram.configuration.ofMultiplicities[Int]

    // Create empty histogram
    val histogram = Histogram.empty[Int]

    val LIMIT = 50

    // Add random numbers to the histogram
    for (_ <- 1 to LIMIT * LIMIT) {
      histogram += (Math.random() * LIMIT).toInt
    }

    // Print the contents of the histogram
    println(
      s"""
        |Histogram of (Math.random() * LIMIT).toInt over ${LIMIT * LIMIT} executions
        |=================================================================
        | maximum occurrences: ${histogram.maxMultiplicity}
        | minimum occurrences: ${histogram.minMultiplicity}
        |=================================================================
        |${
        for (i <- 0 until LIMIT) yield {
          val occurrences = histogram.multiplicity(i)
          s"$i:".padTo(4, ' ') + "".padTo(occurrences, '|') + s"  $occurrences"
        }.mkString("\n")
      }
        |================================================================
        """.stripMargin)
  }

}
