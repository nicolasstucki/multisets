package scala.collection.mutable

import scala.collection._


trait Bag[A]
  extends collection.Bag[A]
  with mutable.BagLike[A, mutable.Bag[A]]
  with generic.GrowableBag[A] {


  def update(elem: A, count: Int): this.type = {
    val b = bucketFactory.newBuilder(elem)
    b.add(elem, count)
    updateBucket(b.result())
    this
  }

  def updateBucket(bucket: mutable.BagBucket[A]): this.type


  def add(elem: A, count: Int): this.type = {
    this.getBucket(elem) match {
      case Some(b) => b add(elem, count)
      case None => updateBucket((bucketFactory.newBuilder(elem) add(elem, count)).result())
    }
    this
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


object Bag extends generic.MutableBagFactory[mutable.Bag] {

  def empty[A](implicit bucketFactory: mutable.Bag.BagBucketFactory[A], equivClass: Equiv[A]): mutable.Bag[A] = mutable.LinkedListBag.empty(bucketFactory, equivClass)
}