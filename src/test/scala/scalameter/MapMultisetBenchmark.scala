package scala

import org.scalameter.api._

import scala.collection.mutable.MapBag


object MapMultisetBenchmark extends PerformanceTest.Quickbenchmark {

  val sizes = Gen.range("size")(300, 1500, 300)

  val bags = for {
    size <- sizes
  } yield {
    val bag = MapBag[Int]()
    for (n <- 1 to size) {
      bag += (n -> n)
    }
    bag
  }

  performance of "MapMultiset" in {
    measure method "reduce" in {
      using(bags) in {
        r => r.reduce(_ + _)
      }
    }
  }

  performance of "MapMultiset" in {
    measure method "reduce2" in {
      using(bags) in {
        r => r.reduce(_ + _, _ * _)
      }
    }
  }
}
