package scala.collection.scalatest.immutable

import scala.collection.scalatest.IntBagTest
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