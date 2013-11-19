package scala.collection.scalatest.mutable

import scala.collection.scalatest._

import scala.collection.mutable

class IntMutableLinkedListBagBagOnMultiplicitiesTest extends IntBagTest {
  implicit val bagBucketFactory = mutable.BagBucketFactory.ofMultiplicities[Int]

  override def emptyBag = mutable.LinkedListBag.empty
}

class IntMutableLinkedListBagOnVectorBucketTest extends IntBagTest {

  implicit val bagBucketFactory = mutable.BagBucketFactory.ofVectors[Int]

  override def emptyBag = {

    println("emptyBag: "+ mutable.BagBucketFactory.ofVectors[Int])
    mutable.LinkedListBag.empty

  }
}
