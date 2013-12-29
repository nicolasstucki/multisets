package scala.collection.scalatest.mutable

import scala.collection.scalatest.IntBagTest
import scala.collection.{immutable, mutable}

class IntMutableHashBagOnMultiplicitiesTest extends IntBagTest {

  implicit lazy val bagBucketFactory = mutable.BagBucketFactory.Hashed.ofMultiplicities[Int]

  override def emptyBag: mutable.Bag[Int] = mutable.HashBag.empty[Int]

}

class IntMutableHashBagOnBagBucketBagTest extends IntBagTest {

  implicit lazy val bagBucketFactory = mutable.BagBucketFactory.Hashed.ofBagBucketBag[Int]

  override def emptyBag: mutable.Bag[Int] = mutable.HashBag.empty[Int]

}

class IntMutableHashBagOnVectorBucketTest extends IntBagTest {

  implicit lazy val bagBucketFactory = mutable.BagBucketFactory.Hashed.ofVectors[Int]

  override def emptyBag: mutable.Bag[Int] = mutable.HashBag.empty[Int]

}

class IntMutableTreeBagOnBagBucketsBagWithMod3EquivTest extends IntBagTest {
  implicit lazy val bagBucketFactory = mutable.BagBucketFactory.Hashed.ofBagBucketBag[Int]

  implicit lazy val mod3 = new Ordering[Int] {
    def compare(x: Int, y: Int): Int = (x % 3) - (y % 3)
  }

  override def emptyBag = mutable.HashBag.empty[Int]
}

class IntMutableTreeBagOnVectorBucketsWithMod3EquivTest extends IntBagTest {
  implicit lazy val bagBucketFactory = mutable.BagBucketFactory.Hashed.ofVectors[Int]

  implicit lazy val mod3 = new Ordering[Int] {
    def compare(x: Int, y: Int): Int = (x % 3) - (y % 3)
  }

  override def emptyBag = mutable.HashBag.empty[Int]
}