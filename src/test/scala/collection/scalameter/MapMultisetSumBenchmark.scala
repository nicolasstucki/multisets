package scala.collection.scalameter

import org.scalameter.api._

import scala.collection.mutable

object MapMultisetSumBenchmark extends PerformanceTest.Quickbenchmark {

  val sizes = Gen.range("size")(4000, 24000, 4000)

  val bags = for {
    size <- sizes
  } yield {
    val bag = mutable.DummyMapBag.empty(mutable.BagBucketFactory.ofMultiplicities[Int])
    for (n <- 1 to size) {
      bag += (n -> n)
    }
    bag
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
