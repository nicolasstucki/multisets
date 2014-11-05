package scala.collection.scalatest

import scala.collection.immutable._

import org.scalatest._
import scala.collection
import scala.collection
import scala.util.hashing.Hashing

abstract class IntBagBucketTest extends FlatSpec with IntBagBucketBehaviours {

  def emptyBagBucket: collection.BagBucket[Int]

  def bagWithOne = emptyBagBucket + 1

  def bagWithOneOneOne = emptyBagBucket + (1 -> 3)

  def bagWithOneTwoThree = emptyBagBucket + 1 + 2 + 3

  def bagWithOneOneTwoThreeThreeThree = emptyBagBucket + (1 -> 2) + (2 -> 1) + (3 -> 3)

  def bags = Seq(bagWithOne, bagWithOneOneOne, bagWithOneTwoThree, bagWithOneOneTwoThreeThreeThree)


  "An empty BagBucket" should behave like emptyBagBucketBehaviour(emptyBagBucket, bags)
  it should behave like intBagBucketBehaviour(emptyBagBucket)


  "BagBucket {1}" should "have the same content as List(1)" in {
    assert(bagWithOne.iterator.toList.sorted == List(1))
  }
  it should behave like nonEmptyBagBucketBehaviour(bagWithOne, bags)
  it should behave like intBagBucketBehaviour(bagWithOne)


  "BagBucket {1,1,1}" should "have the same content as List(1,1,1)" in {
    assert(bagWithOneOneOne.iterator.toList.sorted == List(1, 1, 1))
  }
  it should behave like nonEmptyBagBucketBehaviour(bagWithOneOneOne, bags)
  it should behave like intBagBucketBehaviour(bagWithOneOneOne)


  "BagBucket {1,2,3}" should behave like nonEmptyBagBucketBehaviour(bagWithOneTwoThree, bags)
  it should behave like intBagBucketBehaviour(bagWithOneTwoThree)


  "BagBucket {1,1,2,3,3,3}" should behave like nonEmptyBagBucketBehaviour(bagWithOneOneTwoThreeThreeThree, bags)
  it should behave like intBagBucketBehaviour(bagWithOneOneTwoThreeThreeThree)

}
