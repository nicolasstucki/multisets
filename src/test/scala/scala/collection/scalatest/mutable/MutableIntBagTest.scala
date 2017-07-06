package scala.collection.scalatest.mutable

import scala.collection.scalatest.IntBagTest

/**
  * @author Tom Warnke
  */
trait MutableIntBagTest extends IntBagTest with MutableBagBehaviours {

  override def emptyBag: collection.mutable.Bag[Int]

  override def bagWithOneOneTwoThreeThreeThree = emptyBag + (1 -> 2) + (2 -> 1) + (3 -> 3)

  it should behave like mutableBagBehaviour[Int](bagWithOneOneTwoThreeThreeThree)
}
