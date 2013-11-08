package scala.collection.scalatest.mutable

import scala.collection.scalatest._

import scala.collection.mutable

class IntMutableDummyMapBagOnMultiplicitiesTest extends IntBagTest {
  override def emptyBag = mutable.DummyMapBag.empty(mutable.BagBucketFactory.ofMultiplicities[Int])
}

class IntMutableDummyMapBagOnSeqTest extends IntBagTest {
  override def emptyBag = mutable.DummyMapBag.empty(mutable.BagBucketFactory.ofSeq[Int])
}
