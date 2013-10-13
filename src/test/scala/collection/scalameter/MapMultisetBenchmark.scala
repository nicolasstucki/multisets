package scala.collection.scalameter

import org.scalameter.api._

import scala.collection.mutable

object MapMultisetBenchmark extends PerformanceTest.Quickbenchmark {

  val sizes = Gen.range("size")(2000, 10000, 1000)

  val bags = for {
    size <- sizes
  } yield {
    val bag = mutable.DummyMapBag.empty(mutable.MultiplicityBagBucketFactory.of[Int])
    for (n <- 1 to size) {
      bag += (n -> n)
    }
    bag
  }

  val sum = (a: Int, b: Int) => a + b
  val mul = (a: Int, b: Int) => a * b

  performance of "MapMultiset" in {
    measure method "reduce{reference 0 overhead}" in {
      using(sizes) in {
        size =>
          var acc = 0
          for (n <- 1 to size) {
            acc = sum(mul(n, n), acc)
          }
          acc
      }
    }
  }

  performance of "MapMultiset" in {
    measure method "reduce{reference}" in {
      using(bags) in {
        bag =>
          var acc = 0
          for (bkt <- bag.bucketsIterator) {
            acc = sum(mul(bkt.multiplicity, bkt.sentinel), acc)
          }
          acc
      }
    }
  }

  performance of "MapMultiset" in {
    measure method "reduce" in {
      using(bags) in {
        bag => bag.reduce(sum, mul)
      }
    }
  }


}
