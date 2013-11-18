package scala.collection.scalatest.mutable

import scala.collection.scalatest._

import scala.collection.mutable

class IntMutableLinkedListBagBagOnMultiplicitiesTest extends IntBagTest {
  override def emptyBag = mutable.LinkedListBag.empty(mutable.BagBucketFactory.ofMultiplicities[Int])
}

class IntMutableLinkedListBagOnVectorBucketTest extends IntBagTest {
  override def emptyBag = mutable.LinkedListBag.empty(mutable.BagBucketFactory.ofVectors[Int])
}
