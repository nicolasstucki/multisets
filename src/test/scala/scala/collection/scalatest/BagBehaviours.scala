package scala.collection.scalatest

import org.scalatest._

trait BagBehaviours {
  this: FlatSpec =>

  def emptyBagBehaviour[A: Ordering](bag: scala.collection.Bag[A], bags: Seq[scala.collection.Bag[A]], noneAlreadyPresentInBag: Seq[A]) {
    it should "be empty" in {
      assert(bag.isEmpty, s"bag = $bag")
    }

    it should "not be non-empty" in {
      assert(!bag.nonEmpty, s"bag = $bag")
    }

    it should "have size 0" in {
      assertResult(0, s"bag = $bag") {
        bag.size
      }
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
      assert(bag.mostCommon.isEmpty, s"bag = $bag")
      assert(bag.leastCommon.isEmpty, s"bag = $bag")
    }

    it should behave like bagBehaviour(bag, noneAlreadyPresentInBag)

    it should behave like multisetBehaviour(bag, bags)

  }

  def nonEmptyBagBehaviour[A: Ordering](bag: scala.collection.Bag[A], bags: Seq[scala.collection.Bag[A]], noneAlreadyPresentInBag: Seq[A]) {
    it should "not be empty" in {
      assert(!bag.isEmpty, s"bag = $bag")
    }

    it should "be non-empty" in {
      assert(bag.nonEmpty, s"bag = $bag")
    }

    it should "have size greater than 0" in {
      assert(bag.size > 0, s"bag = $bag, bag.size = ${bag.size}")
    }

    it should "have [minMultiplicity] grater than 0" in {
      assert(bag.minMultiplicity > 0, s"bag = $bag, bag.minMultiplicity = ${bag.minMultiplicity}")
    }

    it should "have [maxMultiplicity] grater than 0" in {
      assert(bag.maxMultiplicity > 0, s"bag = $bag, bag.maxMultiplicity = ${bag.maxMultiplicity}")
    }

    it should "have [minMultiplicity] lesser or equals [maxMultiplicity]" in {
      assert(bag.minMultiplicity <= bag.maxMultiplicity, s"bag = $bag, bag.minMultiplicity = ${bag.minMultiplicity}, bag.maxMultiplicity = ${bag.maxMultiplicity}")
    }

    it should "have [mostCommon] with constant sizes" in {
      val b = bag.mostCommon
      assert(bag.isEmpty === b.isEmpty, s"bag = $bag, b = $b")
      if (b.nonEmpty) {
        val m = b(b.head).size
        for (elem <- b.distinctIterator) {
          assertResult(m, s"bag = $bag, b = $b, elem = $elem") {
            b(elem).size
          }
        }

      }
    }

    it should "have [leastCommon] with constant sizes" in {
      val b = bag.leastCommon
      assert(bag.isEmpty === b.isEmpty, s"bag = $bag, b = $b")
      if (b.nonEmpty) {
        val m = b(b.head).size
        for (elem <- b.distinctIterator) {
          assertResult(m, s"bag = $bag, b = $b, elem = $elem") {
            b(elem).size
          }
        }
      }
    }

    it should "split into parts which when recombined yield the original" in {
      def validateSplitAt(indexOfSplit: Int): Unit = {
        {
          val (firstPart, secondPart) = bag.splitAt(indexOfSplit)
          assertResult(bag, s"bag = $bag, indexOfSplit = $indexOfSplit, firstPart = $firstPart, secondPart = $secondPart") {
            firstPart union secondPart
          }
        }

        {
          val firstPart = bag.take(indexOfSplit)
          val secondPart = bag.drop(indexOfSplit)
          assertResult(bag, s"bag = $bag, indexOfSplit = $indexOfSplit, firstPart = $firstPart, secondPart = $secondPart") {
            firstPart union secondPart
          }
        }
      }

      0 to bag.size foreach (validateSplitAt(_))
    }

    it should "roundtrip to itself when an element that is already contained is removed and then added back in again" in {
      def validateRoundtrip(elementAlreadyPresentInBag: A) = {
        assertResult(bag, s"bag = $bag, element = $elementAlreadyPresentInBag") {
          bag - elementAlreadyPresentInBag + elementAlreadyPresentInBag
        }

        assertResult(bag, s"bag = $bag, element = $elementAlreadyPresentInBag") {
          val multiplicity = bag.multiplicity(elementAlreadyPresentInBag)
          bag - (elementAlreadyPresentInBag -> multiplicity) + (elementAlreadyPresentInBag -> multiplicity)
        }

        assertResult(bag, s"bag = $bag, element = $elementAlreadyPresentInBag") {
          bag.removed(elementAlreadyPresentInBag, 1).added(elementAlreadyPresentInBag, 1)
        }

        assertResult(bag, s"bag = $bag, element = $elementAlreadyPresentInBag") {
          val multiplicity = bag.multiplicity(elementAlreadyPresentInBag)
          bag.removedAll(elementAlreadyPresentInBag).added(elementAlreadyPresentInBag, multiplicity)
        }
      }

      for (elementAlreadyPresentInBag <- bag) {
        validateRoundtrip(elementAlreadyPresentInBag)
      }
    }

    it should "yield a changed value when an element that is already contained is removed" in {
      def validateChange(elementAlreadyPresentInBag: A) = {
        assert(bag - elementAlreadyPresentInBag != bag)

        assert(bag - (elementAlreadyPresentInBag -> bag.multiplicity(elementAlreadyPresentInBag)) != bag)

        assert(bag.removed(elementAlreadyPresentInBag, 1) != bag)

        assert(bag.removedAll(elementAlreadyPresentInBag) != bag)
      }

      for (elementAlreadyPresentInBag <- bag) {
        validateChange(elementAlreadyPresentInBag)
      }
    }

    it should behave like bagBehaviour(bag, noneAlreadyPresentInBag)

    it should behave like multisetBehaviour(bag, bags)
  }

  private def multisetBehaviour[A](bag: scala.collection.Bag[A], bags: Seq[scala.collection.Bag[A]]) {

    it should "implement [union] as a multiset union" in {
      for (bag2 <- bags) {
        val union = bag union bag2
        val clue = s"bag = $bag, bag2 = $bag2, union = $union"
        assert(union.size == bag.size + bag2.size, clue)
        assert(bag subsetOf union, clue)
        assert(bag2 subsetOf union, clue)
        union.distinctIterator.foreach {
          elem =>
            assertResult(bag.multiplicity(elem) + bag2.multiplicity(elem), s"$clue, elem = $elem") {
              union.multiplicity(elem)
            }
        }
      }
    }

    it should "implement [maxUnion] as a multiset max union (generalized set union)" in {
      for (bag2 <- bags) {
        val maxUnion = bag maxUnion bag2
        val clue = s"bag = $bag, bag2 = $bag2, maxUnion = $maxUnion"
        assert(maxUnion.size <= bag.size + bag2.size, clue)
        assert(maxUnion.size >= bag.size, clue)
        assert(maxUnion.size >= bag2.size, clue)
        assert(bag subsetOf maxUnion, clue)
        assert(bag2 subsetOf maxUnion, clue)
        maxUnion.distinctIterator.foreach {
          elem =>
            assertResult(Math.max(bag.multiplicity(elem), bag2.multiplicity(elem)), s"$clue, elem = $elem") {
              maxUnion.multiplicity(elem)
            }
        }
      }
    }

    it should "implement [intersect] as a multiset intersection" in {
      for (bag2 <- bags) {
        val intersect = bag intersect bag2
        val clue = s"bag = $bag, bag2 = $bag2, intersect = $intersect"
        assert(intersect.size <= bag.size, clue)
        assert(intersect.size <= bag2.size, clue)
        assert(intersect subsetOf bag, clue)
        assert(intersect subsetOf bag2, clue)
        intersect.distinctIterator.foreach {
          elem =>
            assertResult(Math.min(bag.multiplicity(elem), bag2.multiplicity(elem)), s"$clue, elem = $elem") {
              intersect.multiplicity(elem)
            }
        }
      }
    }

    it should "implement [diff] as a multiset difference" in {
      for (bag2 <- bags) {
        val diff = bag diff bag2
        val clue = s"bag = $bag, bag2 = $bag2, diff = $diff"
        assert(diff.size <= bag.size, clue)
        assert(diff subsetOf bag, clue)
        diff.distinctIterator.foreach {
          elem =>
            assertResult(Math.max(bag.multiplicity(elem) - bag2.multiplicity(elem), 0), s"$clue, elem = $elem") {
              diff.multiplicity(elem)
            }
        }
      }
    }
  }

  private def bagBehaviour[A: Ordering](bag: scala.collection.Bag[A], noneAlreadyPresentInBag: Seq[A]) {

    it should "have non negative size" in {
      assert(bag.size >= 0, s"bag = $bag")
    }

    it should "have only positive multiplicities (multiplicity>0)" in {
      assert(bag.forall(bag.multiplicity(_) > 0))
    }

    val distinct = bag.distinct

    it should "implement [distinct]: all multiplicities must be one" in {
      for (elem <- distinct) {
        assertResult(1) {
          distinct.multiplicity(elem)
        }
      }
    }

    it should "implement [distinct]: all distinct element must be present" in {
      assertResult(bag.toSet.toList.sorted) {
        distinct.toList.sorted
      }
    }

    it should "roundtrip to itself when an element is added and then removed" in {
      def validateRoundtrip(element: A) = {
        assertResult(bag, s"bag = $bag, element = $element") {
          bag + element - element
        }

        assertResult(bag, s"bag = $bag, element = $element") {
          bag + (element -> 2) - (element -> 2)
        }

        assertResult(bag, s"bag = $bag, element = $element"){
          bag.added(element, 1).removed(element, 1)
        }
      }

      for (notAlreadyPresentInBag <- noneAlreadyPresentInBag) {
        validateRoundtrip(notAlreadyPresentInBag)

        assertResult(bag, s"bag = $bag, notAlreadyPresentInBag = $notAlreadyPresentInBag") {
          bag.added(notAlreadyPresentInBag, 10).removedAll(notAlreadyPresentInBag)
        }
      }

      for (elementAlreadyPresentInBag <- bag) {
        validateRoundtrip(elementAlreadyPresentInBag)
      }
    }

    it should "treat the removal of an element that is not already contained as a no-operation" in {
      for (notAlreadyPresentInBag <- noneAlreadyPresentInBag) {
        assertResult(bag, s"bag = $bag, notAlreadyPresentInBag = $notAlreadyPresentInBag") {
          bag - notAlreadyPresentInBag
        }
      }
    }
  }
}
