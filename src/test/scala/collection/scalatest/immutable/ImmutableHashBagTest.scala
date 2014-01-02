package scala.collection.scalatest.immutable

import scala.collection.scalatest._
import scala.collection.immutable

class IntImmutableHashBagOnMultiplicitiesTest extends IntBagTest {
  implicit lazy val bagConfiguration = immutable.BagConfiguration.Hashed.ofMultiplicities[Int]

  override def emptyBag = immutable.HashBag.empty[Int]
}

class IntImmutableHashBagOnBagBucketBagTest extends IntBagTest {
  implicit lazy val bagConfiguration = immutable.BagConfiguration.Hashed.ofBagBucketBag[Int]

  override def emptyBag = immutable.HashBag.empty[Int]
}

class IntImmutableHashBagOnVectorBucketTest extends IntBagTest {
  implicit lazy val bagConfiguration = immutable.BagConfiguration.Hashed.ofVectors[Int]

  override def emptyBag = immutable.HashBag.empty[Int]
}

class IntImmutableHasBagOnBagBucketsBagWithMod3EquivTest extends IntBagTest {
  implicit lazy val bagConfiguration = immutable.BagConfiguration.Hashed.ofBagBucketBag[Int]

  implicit lazy val mod3 = new Ordering[Int] {
    def compare(x: Int, y: Int): Int = (x % 3) - (y % 3)
  }

  override def emptyBag = immutable.HashBag.empty[Int]
}

class IntImmutableHashBagOnVectorBucketsWithMod3EquivTest extends IntBagTest {
  implicit lazy val bagConfiguration = immutable.BagConfiguration.Hashed.ofVectors[Int]

  implicit lazy val mod3 = new Ordering[Int] {
    def compare(x: Int, y: Int): Int = (x % 3) - (y % 3)
  }

  override def emptyBag = immutable.HashBag.empty[Int]
}


class StringImmutableHashBagOnMultiplicitiesTest extends StringBagTest {
  implicit lazy val bagConfiguration = immutable.BagConfiguration.Hashed.ofMultiplicities[String]

  override def emptyBag = immutable.HashBag.empty[String]
}

class StringImmutableHashBagOnBagBucketBagTest extends StringBagTest {
  implicit lazy val bagConfiguration = immutable.BagConfiguration.Hashed.ofBagBucketBag[String]

  override def emptyBag = immutable.HashBag.empty[String]
}

class StringImmutableHashBagOnVectorBucketTest extends StringBagTest {
  implicit lazy val bagConfiguration = immutable.BagConfiguration.Hashed.ofVectors[String]

  override def emptyBag = immutable.HashBag.empty[String]
}

class StringImmutableHasBagOnBagBucketsBagWithStrSizeEquivTest extends StringBagTest {
  implicit lazy val bagConfiguration = immutable.BagConfiguration.Hashed.ofBagBucketBag[String]

  implicit lazy val strSizeOrd = new Ordering[String] {
    def compare(x: String, y: String): Int = x.size compare y.size
  }

  override def emptyBag = immutable.HashBag.empty[String]
}

class StringImmutableHashBagOnVectorBucketsWithStrSizeEquivTest extends StringBagTest {
  implicit lazy val bagConfiguration = immutable.BagConfiguration.Hashed.ofVectors[String]

  implicit lazy val strSizeOrd = new Ordering[String] {
    def compare(x: String, y: String): Int = x.size compare y.size
  }

  override def emptyBag = immutable.HashBag.empty[String]
}