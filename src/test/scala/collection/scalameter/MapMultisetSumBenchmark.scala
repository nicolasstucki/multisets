package scala.collection.scalameter

import org.scalameter.api._

import scala.collection._

object MapMultisetSumBenchmark extends PerformanceTest.Quickbenchmark {

  val sizes = Gen.range("size")(4000, 24000, 4000)

  val bags = for {
    size <- sizes
  } yield {
    val b = immutable.VectorBag.newBuilder(immutable.BagBucketFactory.ofMultiplicities[Int])
    for (n <- 1 to size) {
      b.add(n, n)
    }
    b.result()
  }

  performance of "MapMultiset" in {
    measure method "reduce{reference}" in {
      using(bags) in {
        bag =>
          var acc = 0
          for (bkt <- bag.bucketsIterator) {
            acc += bkt.multiplicity * bkt.sentinel
          }
          acc
      }
    }
  }

  performance of "MapMultiset" in {
    measure method "reduce" in {
      using(bags) in {
        bag => bag.sum
      }
    }
  }


}
