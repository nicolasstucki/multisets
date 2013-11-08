package scala.collection.immutable

import scala.collection.{mutable, GenTraversable, immutable}
import scala.collection
import scala.collection.generic.ImmutableBagFactory


class DummyMapBag[A](multiplicityMap: immutable.Map[A, immutable.BagBucket[A]])(protected val bucketFactory: immutable.BagBucketFactory[A])
  extends immutable.Bag[A] {

  def empty: DummyMapBag[A] = DummyMapBag.empty(bucketFactory)

  def bucketsIterator: Iterator[BagBucket[A]] = multiplicityMap.valuesIterator


  def addedBucket(bucket: collection.BagBucket[A]): Bag[A] = {
    val b = bucketFactory.newBuilder(bucket.sentinel)
    b addBucket bucket
    multiplicityMap.get(bucket.sentinel) match {
      case Some(bucket2) => b addBucket bucket2
      case None =>
    }
    new DummyMapBag(multiplicityMap.updated(bucket.sentinel, b.result()))(bucketFactory)
  }

  // Removed elements
  def -(elem: A): DummyMapBag[A] = {
    val bkt = multiplicityMap.getOrElse(elem, bucketFactory.newBuilder(elem).result()) - elem
    if (bkt.isEmpty) {
      new DummyMapBag(multiplicityMap - elem)(bucketFactory)
    } else {
      new DummyMapBag(multiplicityMap.updated(elem, bkt))(bucketFactory)
    }
  }

}


object DummyMapBag extends ImmutableBagFactory[Bag] {

  def empty[A](implicit bktFactory: immutable.BagBucketFactory[A]): immutable.DummyMapBag[A] = {
    new immutable.DummyMapBag[A](immutable.Map.empty[A, BagBucket[A]])(bktFactory)
  }

}