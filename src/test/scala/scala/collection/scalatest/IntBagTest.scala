package scala.collection.scalatest

import scala.collection.immutable._

import org.scalatest._
import scala.util.hashing.Hashing

abstract class IntBagTest extends FlatSpec with IntBagBehaviours {

  object Mod3 extends Ordering[Int] with Hashing[Int] {
    // NOTE: this is based on a signed remainder operation, *not* the modulo operation.
    // So 'notAlreadyPresentInBag' should be non-negative to ensure that its remainder
    // can collide with an element already present in the bag - this is to exercise
    // the 'compactWithEquiv' configuration cases.
    def hash(x: Int): Int = (x % 3)

    def compare(x: Int, y: Int): Int = (x % 3) - (y % 3)
  }

  def emptyBag: collection.Bag[Int]

  def bagWithOne = emptyBag + 1

  def bagWithOneOneOne = emptyBag + (1 -> 3)

  def bagWithOneTwoThree = emptyBag + 1 + 2 + 3

  def bagWithOneOneTwoThreeThreeThree = emptyBag + (1 -> 2) + (2 -> 1) + (3 -> 3)

  def bags = Seq(bagWithOne, bagWithOneOneOne, bagWithOneTwoThree, bagWithOneOneTwoThreeThreeThree)

  val notAlreadyPresentInAnyBag = 73753


  "An empty Bag" should behave like emptyBagBehaviour(emptyBag, bags, notAlreadyPresentInAnyBag)
  it should behave like intBagBehaviour(emptyBag)


  "Bag {1}" should "have the same content as List(1)" in {
    assert(bagWithOne.iterator.toList.sorted == List(1))
  }
  it should behave like nonEmptyBagBehaviour(bagWithOne, bags, notAlreadyPresentInAnyBag)
  it should behave like intBagBehaviour(bagWithOne)


  "Bag {1,1,1}" should "have the same content as List(1,1,1)" in {
    assert(bagWithOneOneOne.iterator.toList.sorted == List(1, 1, 1))
  }
  it should behave like nonEmptyBagBehaviour(bagWithOneOneOne, bags, notAlreadyPresentInAnyBag)
  it should behave like intBagBehaviour(bagWithOneOneOne)


  "Bag {1,2,3}" should "have the same content as List(1,2,3)" in {
    assert(bagWithOneTwoThree.iterator.toList.sorted == List(1, 2, 3))
  }
  it should behave like nonEmptyBagBehaviour(bagWithOneTwoThree, bags, notAlreadyPresentInAnyBag)
  it should behave like intBagBehaviour(bagWithOneTwoThree)


  "Bag {1,1,2,3,3,3}" should "have the same content as List(1,1,2,3,3,3)" in {
    assert(bagWithOneOneTwoThreeThreeThree.iterator.toList.sorted == List(1, 1, 2, 3, 3, 3))
  }
  it should behave like nonEmptyBagBehaviour(bagWithOneOneTwoThreeThreeThree, bags, notAlreadyPresentInAnyBag)
  it should behave like intBagBehaviour(bagWithOneOneTwoThreeThreeThree)

}
