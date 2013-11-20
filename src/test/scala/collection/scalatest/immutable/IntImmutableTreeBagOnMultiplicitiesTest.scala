package scala.collection.scalatest.immutable

import scala.collection.scalatest.IntBagTest
import scala.collection.immutable

class IntImmutableTreeBagOnMultiplicitiesTest extends IntBagTest {
  implicit lazy val bagBucketFactory = immutable.SortedBagBucketFactory.ofMultiplicities[Int]

  override def emptyBag = immutable.TreeBag.empty[Int]

}

class IntImmutableTreeBagBagOnVectorBucketTest extends IntBagTest {
  implicit lazy val bagBucketFactory = immutable.SortedBagBucketFactory.ofVectors[Int]

  override def emptyBag = immutable.TreeBag.empty[Int]

}


class IntImmutableTreeBagBagOnVectorBucketsWithMod3EquivTest extends IntBagTest {
  implicit lazy val bagBucketFactory = immutable.SortedBagBucketFactory.ofVectors[Int]

  implicit lazy val mod3 = new Ordering[Int] {
    def compare(x: Int, y: Int): Int = (x % 3) - (y % 3)
  }

  override def emptyBag = immutable.TreeBag.empty[Int]
}