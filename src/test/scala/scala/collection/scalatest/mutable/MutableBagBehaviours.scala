package scala.collection.scalatest.mutable

import org.scalatest._

trait MutableBagBehaviours {
  this: FlatSpec =>

  def mutableBagBehaviour[A](bag: => scala.collection.mutable.Bag[A], newElem: A) {

    it should "support removing single elements with -=" in {
      val b = bag
      var expectedSize = b.size
      for (elem <- bag) {
        b -= elem
        expectedSize = expectedSize - 1
        assert(b.size == expectedSize)
      }
    }

    it should "support removing a fixed number of elements with -= and multiplicities" in {
      val b = bag
      var expectedSize = b.size
      for (elem <- bag.distinct) {
        b -= (elem -> bag.multiplicity(elem))
        expectedSize = expectedSize - bag.multiplicity(elem)
        assert(b.size == expectedSize)
        assert(!b.contains(elem))
      }
    }

    it should "support removing single elements with remove" in {
      val b = bag
      var expectedSize = b.size
      for (elem <- bag) {
        b.remove(elem, 1)
        expectedSize = expectedSize - 1
        assert(b.size == expectedSize)
      }
    }

    it should "support removing all elements with removeAll" in {
      val b = bag
      var expectedSize = b.size
      for (elem <- bag.distinct) {
        b.removeAll(elem)
        expectedSize = expectedSize - bag.multiplicity(elem)
        assert(b.size == expectedSize)
        assert(!b.contains(elem))
      }
    }

    it should "be unchanged when removing elements that do not exist" in {
      val b = bag
      val elem = b.head
      b.removeAll(elem)
      val expectedSize = b.size
      b -= elem
      assert(b.size == expectedSize)
      b -= (elem, 1)
      assert(b.size == expectedSize)
      b.remove(elem, 1)
      assert(b.size == expectedSize)
      b.removeAll(elem)
      assert(b.size == expectedSize)
    }

    it should "support adding single elements with add" in {
      val b = bag
      b.add(newElem, 1)
      val expectedSize = bag.size + 1
      assert(b.contains(newElem))
      assert(b.size == expectedSize)
      assert(b.multiplicity(newElem) == 1)
    }

    it should "support adding multiple elements with add" in {
      val b = bag
      b.add(newElem, 3)
      val expectedSize = bag.size + 3
      assert(b.contains(newElem))
      assert(b.size == expectedSize)
      assert(b.multiplicity(newElem) == 3)
    }

    it should "support adding single elements with +=" in {
      val b = bag
      b += newElem
      val expectedSize = bag.size + 1
      assert(b.contains(newElem))
      assert(b.size == expectedSize)
      assert(b.multiplicity(newElem) == 1)
    }

    it should "support adding multiple elements with +=" in {
      val b = bag
      b.+=((newElem, 3))
      val expectedSize = bag.size + 3
      assert(b.contains(newElem))
      assert(b.size == expectedSize)
      assert(b.multiplicity(newElem) == 3)
    }

    it should "support changing the multiplicity with setMultiplicity" in {
      val b = bag
      val elem = b.head
      b.setMultiplicity(elem, 3)
      assert(b.multiplicity(elem) == 3)
      b.setMultiplicity(elem, 5)
      assert(b.multiplicity(elem) == 5)
    }

    it should "support changing the multiplicity with update" in {
      val b = bag
      val elem = b.head
      b.update(elem, 3)
      assert(b.multiplicity(elem) == 3)
      b.update(elem, 5)
      assert(b.multiplicity(elem) == 5)
    }
  }
}
