package scala.collection.scalatest.immutable

import scala.collection.scalatest._

import scala.collection.immutable

class IntImmutableDummyMapBagOnMultiplicitiesTest extends IntBagTest {
  override def emptyBag = immutable.DummyMapBag.empty(immutable.BagBucketFactory.ofMultiplicities[Int])
}

class IntImmutableDummyMapBagOnSeqTest extends IntBagTest {
  override def emptyBag = immutable.DummyMapBag.empty(immutable.BagBucketFactory.ofSeq[Int])
}
