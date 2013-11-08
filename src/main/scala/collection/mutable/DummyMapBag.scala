package scala.collection.mutable

import scala.collection.{mutable, GenTraversable}
import scala.collection

class DummyMapBag[A](multiplicityMap: mutable.Map[A, mutable.BagBucket[A]])(protected val bucketFactory: mutable.BagBucketFactory[A])
  extends mutable.Bag[A] {

  def empty: mutable.DummyMapBag[A] = new mutable.DummyMapBag(mutable.Map.empty[A, mutable.BagBucket[A]])(bucketFactory)

  def result(): mutable.DummyMapBag[A] = new mutable.DummyMapBag(multiplicityMap.clone())(bucketFactory)

  def clear(): Unit = multiplicityMap.clear()


  def add(elem: A, count: Int) = {
    multiplicityMap.get(elem) match {
      case Some(bucket) =>
        bucket.add(elem, count)
      case None =>
        val b = bucketFactory.newBuilder(elem)
        b.add(elem, count)
        multiplicityMap(elem) = b.result()
    }
    this
  }


  def addedBucket(bucket: collection.BagBucket[A]): mutable.Bag[A] = {
    val b = bucketFactory.newBuilder(bucket.sentinel)
    b addBucket bucket
    multiplicityMap.get(bucket.sentinel) match {
      case Some(bucket2) => b addBucket bucket2
      case None =>
    }
    new mutable.DummyMapBag[A](multiplicityMap.updated(bucket.sentinel, b.result()))(bucketFactory)
  }

  def updateBucket(bucket: mutable.BagBucket[A]) = {
    multiplicityMap.update(bucket.sentinel, bucket)
    this
  }


  // Removed elements
  def -(elem: A): mutable.Bag[A] = {
    val newBag = mutable.DummyMapBag(this.iterator.toIterable)(bucketFactory)
    newBag -= elem
    newBag
  }

  def bucketsIterator: Iterator[mutable.BagBucket[A]] = multiplicityMap.valuesIterator


}


object DummyMapBag {

  def empty[A](implicit bktFactory: mutable.BagBucketFactory[A]): mutable.DummyMapBag[A] = {
    new mutable.DummyMapBag[A](mutable.Map.empty[A, mutable.BagBucket[A]])(bktFactory)
  }

  def apply[A](elemCount: (A, Int))(implicit bktFactory: mutable.BagBucketFactory[A]): mutable.DummyMapBag[A] = {
    val (elem, count) = elemCount
    new mutable.DummyMapBag[A](mutable.Map(elemCount._1 -> {
      val b = bktFactory.newBuilder(elem)
      b.add(elem, count)
      b.result()
    }))(bktFactory)
  }

  def apply[A](elem1: (A, Int), elem2: (A, Int), elems: (A, Int)*)(implicit bktFactory: mutable.BagBucketFactory[A]): mutable.DummyMapBag[A] = {
    val bag = this(elem1) += elem2
    for (elem <- elems) {
      bag += elem
    }
    bag
  }

  def apply[A](elems: GenTraversable[A])(implicit bktFactory: mutable.BagBucketFactory[A]): mutable.Bag[A] = {
    new mutable.DummyMapBag[A](mutable.Map() ++ (elems map (elem => elem -> {
      val b = bktFactory.newBuilder(elem)
      b.add(elem, elems.count(elem == _))
      b.result()
    })))(bktFactory)
  }
}