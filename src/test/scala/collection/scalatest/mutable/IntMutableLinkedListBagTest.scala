package scala.collection.scalatest.mutable

import scala.collection.scalatest._

import scala.collection.mutable

class IntMutableLinkedListBagBagOnMultiplicitiesTest extends IntBagTest {

  implicit lazy val bagBucketFactory = mutable.BagBucketFactory.ofMultiplicities[Int]

  override def emptyBag: mutable.Bag[Int] = mutable.LinkedListBag.empty[Int]

}

class IntMutableLinkedListBagOnVectorBucketTest extends IntBagTest {

  implicit lazy val bagBucketFactory = mutable.BagBucketFactory.ofVectors[Int]

  override def emptyBag: mutable.Bag[Int] = mutable.LinkedListBag.empty[Int]


}
