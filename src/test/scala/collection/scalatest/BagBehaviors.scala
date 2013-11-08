package scala.collection.scalatest

import org.scalatest._
import java.lang.UnsupportedOperationException

trait BagBehaviors {
  this: FlatSpec =>

  def emptyBagBehavior[A](bag: => collection.Bag[A], bags: => Seq[collection.Bag[A]]) {
    it should "be empty" in {
      assert(bag.isEmpty)
    }

    it should "not be non-empty" in {
      assert(!bag.nonEmpty)
    }

    it should "have size 0" in {
      assert(bag.size == 0)
    }

    it should "throw UnsupportedOperationException if for [minMultiplicity] and [maxMultiplicity]" in {
      intercept[UnsupportedOperationException] {
        bag.minMultiplicity
      }
      intercept[UnsupportedOperationException] {
        bag.maxMultiplicity
      }
    }

    it should "return an empty bag with [mostCommon] and [leastCommon]" in {
      assert(bag.mostCommon.isEmpty)
      assert(bag.leastCommon.isEmpty)
    }

    it should behave like bagBehavior(bag)

    it should behave like multisetBehavior(bag, bags)

  }

  def nonEmptyBagBehavior[A](bag: => collection.Bag[A], bags: => Seq[collection.Bag[A]]) {
    it should "not be empty" in {
      assert(!bag.isEmpty)
    }

    it should "be non-empty" in {
      assert(bag.nonEmpty)
    }

    it should "have size greater than 0" in {
      assert(bag.size > 0)
    }

    it should "have [minMultiplicity] grater than 0" in {
      assert(bag.minMultiplicity > 0)
    }

    it should "have [maxMultiplicity] grater than 0" in {
      assert(bag.minMultiplicity > 0)
    }

    it should "have [minMultiplicity] lesser or equals [maxMultiplicity]" in {
      assert(bag.minMultiplicity <= bag.maxMultiplicity)
    }

    it should "have [mostCommon] has constant multiplicity" in {
      val m = bag.maxMultiplicity
      assert(bag.mostCommon.bucketsIterator.forall(_.multiplicity == m))
    }

    it should "have [leastCommon] has constant multiplicity" in {
      val m = bag.minMultiplicity
      assert(bag.leastCommon.bucketsIterator.forall(_.multiplicity == m))
    }

    it should behave like bagBehavior(bag)

    it should behave like multisetBehavior(bag, bags)
  }

  private def multisetBehavior[A](bag: => collection.Bag[A], bags: => Seq[collection.Bag[A]]) {

    it should "implement [union] as a multiset union" in {
      for (bag2 <- bags) {
        val union = bag union bag2
        assert(union.size == bag.size + bag2.size)
        assert(bag subsetOf union)
        assert(bag2 subsetOf union)
        union.bucketsIterator.foreach {
          bucket =>
            val elem = bucket.sentinel
            assert(bag.multiplicity(elem) + bag2.multiplicity(elem) === bucket.multiplicity)
        }
      }
    }

    it should "implement [maxUnion] as a multiset max union (generalized set union)" in {
      for (bag2 <- bags) {
        val maxUnion = bag maxUnion bag2
        assert(maxUnion.size <= bag.size + bag2.size)
        assert(maxUnion.size >= bag.size)
        assert(maxUnion.size >= bag2.size)
        assert(bag subsetOf maxUnion)
        assert(bag2 subsetOf maxUnion)
        maxUnion.bucketsIterator.foreach {
          bucket =>
            val elem = bucket.sentinel
            assert(Math.max(bag.multiplicity(elem), bag2.multiplicity(elem)) === bucket.multiplicity)
        }
      }
    }

    it should "implement [intersect] as a multiset intersection" in {
      for (bag2 <- bags) {
        val intersect = bag intersect bag2
        assert(intersect.size <= bag.size)
        assert(intersect.size <= bag2.size)
        assert(intersect subsetOf bag)
        assert(intersect subsetOf bag2)
        intersect.bucketsIterator.foreach {
          bucket =>
            val elem = bucket.sentinel
            assert(Math.min(bag.multiplicity(elem), bag2.multiplicity(elem)) === bucket.multiplicity)
        }
      }
    }

    it should "implement [diff] as a multiset difference" in {
      for (bag2 <- bags) {
        val diff = bag diff bag2
        assert(diff.size <= bag.size)
        assert(diff subsetOf bag)
        diff.bucketsIterator.foreach {
          bucket =>
            val elem = bucket.sentinel
            assert(Math.max(bag.multiplicity(elem) - bag2.multiplicity(elem), 0) === bucket.multiplicity)
        }
      }
    }

  }

  private def bagBehavior[A](bag: => collection.Bag[A]) {

    it should "have non negative size" in {
      assert(bag.size >= 0)
    }

    it should "have only positive multiplicities (multiplicity>0)" in {
      assert(bag.forall(bag.multiplicity(_) > 0))
    }
  }

}
