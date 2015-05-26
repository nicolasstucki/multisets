package scala.collection.scalatest

import scala.collection.immutable._

import org.scalatest._
import scala.collection
import scala.util.hashing.Hashing

abstract class StringBagBucketTest extends FlatSpec with StringBagBucketBehaviours {

  object StrSize extends Ordering[String] with Hashing[String] {
    def hash(x: String): Int = x.size

    def compare(x: String, y: String): Int = x.size compare y.size
  }

  def emptyBagBucket: collection.BagBucket[String]

  def bagWithCat = emptyBagBucket + "Cat"

  def bagWithCatCatCat = emptyBagBucket + ("Cat" -> 3)

  def bagWithCatDogMouse = emptyBagBucket + "Cat" + "Dog" + "Mouse"

  def bagWithCatCatDogMouseMouseMouse = emptyBagBucket + ("Cat" -> 2) + ("Dog" -> 1) + ("Mouse" -> 3)

  def bags = Seq(bagWithCat, bagWithCatCatCat, bagWithCatDogMouse, bagWithCatCatDogMouseMouseMouse)


  "An empty BagBucket" should behave like emptyBagBucketBehaviour(emptyBagBucket, bags)
  it should behave like stringBagBucketBehaviour(emptyBagBucket)

  """BagBucket {"Cat"}""" should behave like nonEmptyBagBucketBehaviour(bagWithCat, bags)
  it should behave like stringBagBucketBehaviour(bagWithCat)


  """BagBucket {"Cat","Cat","Cat"}""" should behave like nonEmptyBagBucketBehaviour(bagWithCatCatCat, bags)
  it should behave like stringBagBucketBehaviour(bagWithCatCatCat)


  """BagBucket {"Cat","Dog","Mouse"}""" should behave like nonEmptyBagBucketBehaviour(bagWithCatDogMouse, bags)
  it should behave like stringBagBucketBehaviour(bagWithCatDogMouse)


  """BagBucket {"Cat","Cat","Dog","Mouse","Mouse","Mouse"}""" should behave like nonEmptyBagBucketBehaviour(bagWithCatCatDogMouseMouseMouse, bags)
  it should behave like stringBagBucketBehaviour(bagWithCatCatDogMouseMouseMouse)


}
