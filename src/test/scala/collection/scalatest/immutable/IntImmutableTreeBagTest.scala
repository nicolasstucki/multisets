package scala.collection.scalatest.immutable

import scala.collection.scalatest._
import scala.collection.immutable

class IntImmutableTreeBagOnMultiplicitiesTest extends IntBagTest {
  implicit lazy val bagBucketFactory = immutable.BagConfiguration.Sorted.ofMultiplicities[Int]

  override def emptyBag = immutable.TreeBag.empty[Int]
}

class IntImmutableTreeBagOnBagBucketBagTest extends IntBagTest {
  implicit lazy val bagBucketFactory = immutable.BagConfiguration.Sorted.ofBagBucketBag[Int]

  override def emptyBag = immutable.TreeBag.empty[Int]
}

class IntImmutableTreeBagOnVectorBucketTest extends IntBagTest {
  implicit lazy val bagBucketFactory = immutable.BagConfiguration.Sorted.ofVectors[Int]

  override def emptyBag = immutable.TreeBag.empty[Int]
}

class IntImmutableTreeBagOnBagBucketsBagWithMod3EquivTest extends IntBagTest {
  implicit lazy val bagBucketFactory = immutable.BagConfiguration.Sorted.ofBagBucketBag[Int]

  implicit lazy val mod3 = new Ordering[Int] {
    def compare(x: Int, y: Int): Int = (x % 3) - (y % 3)
  }

  override def emptyBag = immutable.TreeBag.empty[Int]
}

class IntImmutableTreeBagOnVectorBucketsWithMod3EquivTest extends IntBagTest {
  implicit lazy val bagBucketFactory = immutable.BagConfiguration.Sorted.ofVectors[Int]

  implicit lazy val mod3 = new Ordering[Int] {
    def compare(x: Int, y: Int): Int = (x % 3) - (y % 3)
  }

  override def emptyBag = immutable.TreeBag.empty[Int]
}


class StringImmutableTreeBagOnMultiplicitiesTest extends StringBagTest {
  implicit lazy val bagBucketFactory = immutable.BagConfiguration.Sorted.ofMultiplicities[String]

  override def emptyBag = immutable.TreeBag.empty[String]
}

class StringImmutableTreeBagOnBagBucketBagTest extends StringBagTest {
  implicit lazy val bagBucketFactory = immutable.BagConfiguration.Sorted.ofBagBucketBag[String]

  override def emptyBag = immutable.TreeBag.empty[String]
}

class StringImmutableTreeBagOnVectorBucketTest extends StringBagTest {
  implicit lazy val bagBucketFactory = immutable.BagConfiguration.Sorted.ofVectors[String]

  override def emptyBag = immutable.TreeBag.empty[String]
}

class StringImmutableTreeBagOnBagBucketsBagWithStrSizeEquivTest extends StringBagTest {
  implicit lazy val bagBucketFactory = immutable.BagConfiguration.Sorted.ofBagBucketBag[String]

  implicit lazy val strSizeOrd = new Ordering[String] {
    def compare(x: String, y: String): Int = x.size compare y.size
  }

  override def emptyBag = immutable.TreeBag.empty[String]
}

class StringImmutableTreeBagOnVectorBucketsWithStrSizeEquivTest extends StringBagTest {
  implicit lazy val bagBucketFactory = immutable.BagConfiguration.Sorted.ofVectors[String]

  implicit lazy val strSizeOrd = new Ordering[String] {
    def compare(x: String, y: String): Int = x.size compare y.size
  }

  override def emptyBag = immutable.TreeBag.empty[String]
}