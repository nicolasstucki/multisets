package scala.collection.scalatest

import org.scalatest._
import java.lang.UnsupportedOperationException

trait BagBucketBehaviours {
  this: FlatSpec =>

  def emptyBagBucketBehaviour[A](bagBucket: => collection.BagBucket[A], bagBuckets: => Seq[collection.BagBucket[A]]) {
    it should "be empty" in {
      assert(bagBucket.isEmpty, s"bagBucket = $bagBucket")
    }

    it should "not be non-empty" in {
      assert(!bagBucket.nonEmpty, s"bagBucket = $bagBucket")
    }

    it should "have size 0" in {
      assertResult(0, s"bagBucket = $bagBucket") {
        bagBucket.size
      }
    }

    it should "throw UnsupportedOperationException if for [minMultiplicity] and [maxMultiplicity]" in {
      intercept[UnsupportedOperationException] {
        bagBucket.minMultiplicity
      }
      intercept[UnsupportedOperationException] {
        bagBucket.maxMultiplicity
      }
    }

    it should behave like bagBucketBehaviour(bagBucket)

    it should behave like multisetBehaviour(bagBucket, bagBuckets)

  }

  def nonEmptyBagBucketBehaviour[A](bagBucket: => collection.BagBucket[A], bagBuckets: => Seq[collection.BagBucket[A]]) {
    it should "not be empty" in {
      assert(!bagBucket.isEmpty, s"bagBucket = $bagBucket")
    }

    it should "be non-empty" in {
      assert(bagBucket.nonEmpty, s"bagBucket = $bagBucket")
    }

    it should "have size greater than 0" in {
      assert(bagBucket.size > 0, s"bagBucket = $bagBucket, bagBucket.size = ${bagBucket.size}")
    }

    it should "have [minMultiplicity] grater than 0" in {
      assert(bagBucket.minMultiplicity > 0, s"bagBucket = $bagBucket, bagBucket.minMultiplicity = ${bagBucket.minMultiplicity}")
    }

    it should "have [maxMultiplicity] grater than 0" in {
      assert(bagBucket.maxMultiplicity > 0, s"bagBucket = $bagBucket, bagBucket.maxMultiplicity = ${bagBucket.maxMultiplicity}")
    }

    it should "have [minMultiplicity] lesser or equals [maxMultiplicity]" in {
      assert(bagBucket.minMultiplicity <= bagBucket.maxMultiplicity, s"bagBucket = $bagBucket, bagBucket.minMultiplicity = ${bagBucket.minMultiplicity}, bagBucket.maxMultiplicity = ${bagBucket.maxMultiplicity}")
    }

    it should behave like bagBucketBehaviour(bagBucket)

    it should behave like multisetBehaviour(bagBucket, bagBuckets)
  }

  private def multisetBehaviour[A](bagBucket: => collection.BagBucket[A], bagBuckets: => Seq[collection.BagBucket[A]]) {

//    it should "implement [union] as a multiset union" in {
//      for (bagBucket2 <- bagBuckets) {
//        val union = bagBucket union bagBucket2
//        val clue = s"bagBucket = $bagBucket, bagBucket2 = $bagBucket2, union = $union"
//        assert(union.size == bagBucket.size + bagBucket2.size, clue)
//        assert(bagBucket subsetOf union, clue)
//        assert(bagBucket2 subsetOf union, clue)
//        union.distinctIterator.foreach {
//          elem =>
//            assertResult(bagBucket.multiplicity(elem) + bagBucket2.multiplicity(elem), s"$clue, elem = $elem") {
//              union.multiplicity(elem)
//            }
//        }
//      }
//    }

//    it should "implement [maxUnion] as a multiset max union (generalized set union)" in {
//      for (bagBucket2 <- bagBuckets) {
//        val maxUnion = bagBucket maxUnion bagBucket2
//        val clue = s"bagBucket = $bagBucket, bagBucket2 = $bagBucket2, maxUnion = $maxUnion"
//        assert(maxUnion.size <= bagBucket.size + bagBucket2.size, clue)
//        assert(maxUnion.size >= bagBucket.size, clue)
//        assert(maxUnion.size >= bagBucket2.size, clue)
//        assert(bagBucket subsetOf maxUnion, clue)
//        assert(bagBucket2 subsetOf maxUnion, clue)
//        maxUnion.distinctIterator.foreach {
//          elem =>
//            assertResult(Math.max(bagBucket.multiplicity(elem), bagBucket2.multiplicity(elem)), s"$clue, elem = $elem") {
//              maxUnion.multiplicity(elem)
//            }
//        }
//      }
//    }

//    it should "implement [intersect] as a multiset intersection" in {
//      for (bagBucket2 <- bagBuckets) {
//        val intersect = bagBucket intersect bagBucket2
//        val clue = s"bagBucket = $bagBucket, bagBucket2 = $bagBucket2, intersect = $intersect"
//        assert(intersect.size <= bagBucket.size, clue)
//        assert(intersect.size <= bagBucket2.size, clue)
//        assert(intersect subsetOf bagBucket, clue)
//        assert(intersect subsetOf bagBucket2, clue)
//        intersect.distinctIterator.foreach {
//          elem =>
//            assertResult(Math.min(bagBucket.multiplicity(elem), bagBucket2.multiplicity(elem)), s"$clue, elem = $elem") {
//              intersect.multiplicity(elem)
//            }
//        }
//      }
//    }

//    it should "implement [diff] as a multiset difference" in {
//      for (bagBucket2 <- bagBuckets) {
//        val diff = bagBucket diff bagBucket2
//        val clue = s"bagBucket = $bagBucket, bagBucket2 = $bagBucket2, diff = $diff"
//        assert(diff.size <= bagBucket.size, clue)
//        assert(diff subsetOf bagBucket, clue)
//        diff.distinctIterator.foreach {
//          elem =>
//            assertResult(Math.max(bagBucket.multiplicity(elem) - bagBucket2.multiplicity(elem), 0), s"$clue, elem = $elem") {
//              diff.multiplicity(elem)
//            }
//        }
//      }
//    }
  }

  private def bagBucketBehaviour[A](bagBucket: => collection.BagBucket[A]) {

    it should "have non negative size" in {
      assert(bagBucket.size >= 0, s"bagBucket = $bagBucket")
    }

    it should "have only positive multiplicities (multiplicity>=0)" in {
      assert(bagBucket.forall(bagBucket.multiplicity(_) >= 0))
    }
  }

}
