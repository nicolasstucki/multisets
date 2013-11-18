package scala.collection.scalatest.immutable

import scala.collection.scalatest.IntBagTest
import scala.collection.immutable


class IntImmutableVectorBagOnMultiplicitiesTest extends IntBagTest {
  override def emptyBag = immutable.VectorBag.empty(immutable.BagBucketFactory.ofMultiplicities[Int])
}

class IntImmutableVectorBagBagOnVectorBucketTest extends IntBagTest {
  override def emptyBag = immutable.VectorBag.empty(immutable.BagBucketFactory.ofVectors[Int])
}


class IntImmutableVectorBagBagOnVectorBucketsWithMod3EquivTest extends IntBagTest {

  object Mod3Equiv extends Equiv[Int] {
    def equiv(x: Int, y: Int): Boolean = (x % 3) == (y % 3)
  }

  override def emptyBag = immutable.VectorBag.empty(immutable.BagBucketFactory.ofVectors[Int](Mod3Equiv))
}