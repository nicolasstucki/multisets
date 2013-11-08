package scala.collection.mutable

import scala.collection.{Iterator, GenTraversable, mutable}
import scala.collection

trait Bag[A]
  extends collection.Bag[A]
  with mutable.BagLike[A, mutable.Bag[A]] {


  def update(elem: A, count: Int): this.type = {
    val b = bucketFactory.newBuilder(elem)
    b.add(elem, count)
    updateBucket(b.result())
    this
  }

  def updateBucket(bucket: mutable.BagBucket[A]): this.type

  def +=(elem: A): this.type = this += (elem -> 1)

  def +=(elemCount: (A, Int)): this.type = elemCount match {
    case (elem, count) => update(elem, this.multiplicity(elem) + count)
  }

  def addBucket(bucket: collection.BagBucket[A]): this.type = {
    this.getBucket(bucket.sentinel) match {
      case Some(b) => b addBucket bucket
      case None => updateBucket((bucketFactory.newBuilder(bucket.sentinel) addBucket bucket).result())
    }
    this
  }

  def -=(elem: A): this.type = this -= (elem -> 1)

  def -=(elemCount: (A, Int)): this.type = {
    val (elem, count) = elemCount
    update(elem, Math.max(this.multiplicity(elem) - count, 0))
  }

}


object Bag {

  def empty[A](implicit bktFactory: mutable.BagBucketFactory[A]): mutable.Bag[A] = {
    mutable.DummyMapBag.empty(bktFactory)
  }

  def apply[A](elem: (A, Int))(implicit bktFactory: mutable.BagBucketFactory[A]): mutable.Bag[A] = {
    mutable.DummyMapBag(elem)(bktFactory)
  }

  def apply[A](elem1: (A, Int), elem2: (A, Int), elems: (A, Int)*)(implicit bktFactory: mutable.BagBucketFactory[A]): mutable.Bag[A] = {
    mutable.DummyMapBag(elem1, elem2, elems: _*)(bktFactory)
  }

  def apply[A](elems: GenTraversable[A])(implicit bktFactory: mutable.BagBucketFactory[A]): mutable.Bag[A] = {
    mutable.DummyMapBag(elems)(bktFactory)
  }

}