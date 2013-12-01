package scala.collection.scalatest

import scala.collection.immutable._

import org.scalatest._
import scala.collection
import scala.collection

abstract class IntBagTest extends FlatSpec with IntBagBehaviors {


  def emptyBag: collection.Bag[Int]

  def bagWithOne = emptyBag + 1

  def bagWithOneOneOne = emptyBag added (1 -> 3)

  def bagWithOneTwoThree = emptyBag + 1 + 2 + 3

  def bagWithOneOneTwoThreeThreeThree = emptyBag added (1 -> 2) added (2 -> 1) added (3 -> 3)

  def bags = Seq(bagWithOne, bagWithOneOneOne, bagWithOneTwoThree, bagWithOneOneTwoThreeThreeThree)


  "An empty Bag" should behave like emptyBagBehavior(emptyBag, bags)
  it should behave like intBagBehavior(emptyBag)


  "Bag {1}" should "have the same content as List(1)" in {
    assert(bagWithOne.iterator.toList.sorted == List(1))
  }
  it should behave like nonEmptyBagBehavior(bagWithOne, bags)
  it should behave like intBagBehavior(bagWithOne)


  "Bag {1,1,1}" should "have the same content as List(1,1,1)" in {
    assert(bagWithOneOneOne.iterator.toList.sorted == List(1, 1, 1))
  }
  it should behave like nonEmptyBagBehavior(bagWithOneOneOne, bags)
  it should behave like intBagBehavior(bagWithOneOneOne)


  "Bag {1,2,3}" should "have the same content as List(1,2,3)" in {
    assert(bagWithOneTwoThree.iterator.toList.sorted == List(1, 2, 3))
  }
  it should behave like nonEmptyBagBehavior(bagWithOneTwoThree, bags)
  it should behave like intBagBehavior(bagWithOneTwoThree)


  "Bag {1,1,2,3,3,3}" should "have the same content as List(1,1,2,3,3,3)" in {
    assert(bagWithOneOneTwoThreeThreeThree.iterator.toList.sorted == List(1, 1, 2, 3, 3, 3))
  }
  it should behave like nonEmptyBagBehavior(bagWithOneOneTwoThreeThreeThree, bags)
  it should behave like intBagBehavior(bagWithOneOneTwoThreeThreeThree)


}
