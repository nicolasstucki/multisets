package scala.collection.scalatest.mutable

import scala.collection.scalatest._
import scala.collection.mutable
import scala.collection.scalatest.IntBagTest

class IntMutableHashBagOnMultiplicitiesTest extends IntBagTest {

  implicit lazy val bagBucketFactory = mutable.HashBag.configuration.ofMultiplicities[Int]

  override def emptyBag: mutable.Bag[Int] = mutable.HashBag.empty[Int]

}

class IntMutableHashBagOnBagBucketBagTest extends IntBagTest {

  implicit lazy val bagBucketFactory = mutable.HashBag.configuration.ofBagBucketBag[Int]

  override def emptyBag: mutable.Bag[Int] = mutable.HashBag.empty[Int]

}

class IntMutableHashBagOnVectorBucketTest extends IntBagTest {

  implicit lazy val bagBucketFactory = mutable.HashBag.configuration.ofVectors[Int]

  override def emptyBag: mutable.Bag[Int] = mutable.HashBag.empty[Int]

}

class IntMutableTreeBagOnBagBucketsBagWithMod3EquivTest extends IntBagTest {
  implicit lazy val bagBucketFactory = mutable.HashBag.configuration.ofBagBucketBag[Int]

  implicit lazy val mod3 = new Ordering[Int] {
    def compare(x: Int, y: Int): Int = (x % 3) - (y % 3)
  }

  override def emptyBag = mutable.HashBag.empty[Int]
}

class IntMutableTreeBagOnVectorBucketsWithMod3EquivTest extends IntBagTest {
  implicit lazy val bagBucketFactory = mutable.BagConfiguration.Hashed.ofVectors[Int]

  implicit lazy val mod3 = new Ordering[Int] {
    def compare(x: Int, y: Int): Int = (x % 3) - (y % 3)
  }

  override def emptyBag = mutable.HashBag.empty[Int]
}


class StringMutableHashBagOnMultiplicitiesTest extends StringBagTest {

  implicit lazy val bagBucketFactory = mutable.HashBag.configuration.ofMultiplicities[String]

  override def emptyBag: mutable.Bag[String] = mutable.HashBag.empty[String]

}

class StringMutableHashBagOnBagBucketBagTest extends StringBagTest {

  implicit lazy val bagBucketFactory = mutable.HashBag.configuration.ofBagBucketBag[String]

  override def emptyBag: mutable.Bag[String] = mutable.HashBag.empty[String]

}

class StringMutableHashBagOnVectorBucketTest extends StringBagTest {

  implicit lazy val bagBucketFactory = mutable.HashBag.configuration.ofVectors[String]

  override def emptyBag: mutable.Bag[String] = mutable.HashBag.empty[String]

}

class StringMutableTreeBagOnBagBucketsBagWithStrSizeEquivTest extends StringBagTest {
  implicit lazy val bagBucketFactory = mutable.HashBag.configuration.ofBagBucketBag[String]

  implicit lazy val strSizeOrd = new Ordering[String] {
    def compare(x: String, y: String): Int = x.size compare y.size
  }

  override def emptyBag = mutable.HashBag.empty[String]
}

class StringMutableTreeBagOnVectorBucketsWithStrSizeEquivTest extends StringBagTest {
  implicit lazy val bagBucketFactory = mutable.BagConfiguration.Hashed.ofVectors[String]

  implicit lazy val strSizeOrd = new Ordering[String] {
    def compare(x: String, y: String): Int = x.size compare y.size
  }

  override def emptyBag = mutable.HashBag.empty[String]
}