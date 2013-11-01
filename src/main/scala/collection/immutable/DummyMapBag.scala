package scala.collection.immutable

import scala.collection.{mutable, GenTraversable, immutable}
import scala.collection


class DummyMapBag[A](multiplicityMap: immutable.Map[A, immutable.BagBucket[A]])(protected val bucketFactory: BagBucketFactory[A])
  extends immutable.Bag[A] {

  def empty: DummyMapBag[A] = DummyMapBag.empty(bucketFactory)

  def bucketsIterator: Iterator[BagBucket] = multiplicityMap.valuesIterator

  // Added elements
  def +(elem: A): DummyMapBag[A] = {
    val bkt = multiplicityMap.getOrElse(elem, bucketFactory.empty(elem)) + elem
    new DummyMapBag(multiplicityMap.updated(elem, bkt))(bucketFactory)
  }


  def getBucket(elem: A): Option[BagBucket] = multiplicityMap.get(elem)

  // Added Bucket
  def addedBucket(bucket: scala.collection.BagBucket[A]): immutable.DummyMapBag[A] = {
    val newMap: Map[A, immutable.BagBucket[A]] = multiplicityMap.get(bucket.sentinel) match {
      case Some(bucket2) => multiplicityMap.updated(bucket.sentinel, bucket2 addedBucket bucket)
      case None => multiplicityMap.updated(bucket.sentinel, bucketFactory.from(bucket))
    }
    new immutable.DummyMapBag[A](newMap)(bucketFactory)
  }


  // Removed elements
  def -(elem: A): DummyMapBag[A] = {
    val bkt = multiplicityMap.getOrElse(elem, bucketFactory.empty(elem)) - elem
    if (bkt.isEmpty) {
      new DummyMapBag(multiplicityMap - elem)(bucketFactory)
    } else {
      new DummyMapBag(multiplicityMap.updated(elem, bkt))(bucketFactory)
    }
  }

}


object DummyMapBag {

  def empty[A](implicit bktFactory: immutable.BagBucketFactory[A]): immutable.DummyMapBag[A] = {
    new immutable.DummyMapBag[A](immutable.Map.empty[A, BagBucket[A]])(bktFactory)
  }

  def apply[A](elemCount: (A, Int))(implicit bktFactory: immutable.BagBucketFactory[A]): immutable.DummyMapBag[A] = {
    val (elem, count) = elemCount
    new immutable.DummyMapBag[A](immutable.Map(elem -> bktFactory.from(elem, count)))(bktFactory)
  }

  def apply[A](elem1: (A, Int), elem2: (A, Int), elems: (A, Int)*)(implicit bktFactory: immutable.BagBucketFactory[A]): immutable.Bag[A] = {
    var bag = this(elem1) + elem2
    for (elem <- elems) {
      bag = bag + elem
    }
    bag
  }

  def apply[A](elems: GenTraversable[A])(implicit bktFactory: immutable.BagBucketFactory[A] = immutable.BagBucketFactory.ofMultiplicities[A]): immutable.DummyMapBag[A] = {
    new immutable.DummyMapBag[A](immutable.Map() ++ (elems map (elem => elem -> bktFactory.from(elem, elems.count(elem == _)))))(bktFactory)
  }
}