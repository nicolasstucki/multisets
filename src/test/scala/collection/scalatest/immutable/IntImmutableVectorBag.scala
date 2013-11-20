package scala.collection.scalatest.immutable

import scala.collection.scalatest.IntBagTest
import scala.collection.immutable


class IntImmutableVectorBagOnMultiplicitiesTest extends IntBagTest {
  implicit lazy val bagBucketFactory = immutable.BagBucketFactory.ofMultiplicities[Int]

  override def emptyBag = immutable.VectorBag.empty

}

class IntImmutableVectorBagBagOnVectorBucketTest extends IntBagTest {
  implicit lazy val bagBucketFactory = immutable.BagBucketFactory.ofVectors[Int]

  override def emptyBag = immutable.VectorBag.empty

}


class IntImmutableVectorBagBagOnVectorBucketsWithMod3EquivTest extends IntBagTest {
  implicit lazy val bagBucketFactory = immutable.BagBucketFactory.ofVectors[Int]

  implicit lazy val mod3Equiv = new Equiv[Int] {
    def equiv(x: Int, y: Int): Boolean = (x % 3) == (y % 3)
  }

  override def emptyBag = immutable.VectorBag.empty
}