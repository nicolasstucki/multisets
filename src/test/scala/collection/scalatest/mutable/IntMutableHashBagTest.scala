package scala.collection.scalatest.mutable

import scala.collection.scalatest.IntBagTest
import scala.collection.mutable

class IntMutableHashBagBagOnMultiplicitiesTest extends IntBagTest {

  implicit lazy val bagBucketFactory = mutable.HashedBagBucketFactory.ofMultiplicities[Int]

  override def emptyBag: mutable.Bag[Int] = mutable.HashBag.empty[Int]

}

class IntMutableHashBagOnVectorBucketTest extends IntBagTest {

  implicit lazy val bagBucketFactory = mutable.HashedBagBucketFactory.ofVectors[Int]

  override def emptyBag: mutable.Bag[Int] = mutable.HashBag.empty[Int]

}
