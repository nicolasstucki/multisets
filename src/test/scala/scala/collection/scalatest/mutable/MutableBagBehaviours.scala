package scala.collection.scalatest.mutable

import org.scalatest._

trait MutableBagBehaviours {
  this: FlatSpec =>

  def mutableBagBehaviour[A](bag: => scala.collection.mutable.Bag[A]) {

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
  }
}
