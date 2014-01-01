package scala.collection.scalatest.immutable

import scala.collection.scalatest.IntBagTest
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