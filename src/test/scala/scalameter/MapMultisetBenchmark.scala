package scala.scalameter

import org.scalameter.api._

import scala.collection.mutable.MapBag
import scala.collection.{mutable, IntBagBuckets}

object MapMultisetBenchmark extends PerformanceTest.Quickbenchmark {

  val sizes = Gen.range("size")(200000, 1000000, 100000)

  val bags = for {
    size <- sizes
  } yield {
    val bag = mutable.MapBag(IntBagBuckets.of[Int])
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
          for ((elem, count) <- bag.countsIterator) {
            acc = sum(mul(count, elem), acc)
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
