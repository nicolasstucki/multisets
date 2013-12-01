package bagapps.histogram


import scala.collection.immutable.{TreeBag => Histogram}

object Main extends BagPredef {

  def main(args: Array[String]) {

    val LIMIT = 50

    val histogram = {
      val histogramBuilder = Histogram.newBuilder[Int]

      (1 to LIMIT * LIMIT).foreach(_ => histogramBuilder += (Math.random() * LIMIT).toInt)

      histogramBuilder.result()
    }

    println(
      s"""
        |Histogram of (Math.random() * LIMIT).toInt over $LIMIT executions
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
