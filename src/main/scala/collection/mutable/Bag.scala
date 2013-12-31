package scala.collection.mutable

import scala.collection._
import scala.collection.generic.CanBuildFrom


trait Bag[A]
  extends collection.Bag[A]
  with mutable.BagLike[A, mutable.Bag[A]]
  with generic.GrowableBag[A] {


  def update(elem: A, count: Int): this.type = {
    val b = bagConfiguration.newBuilder(elem)
    b.add(elem, count)
    updateBucket(b.result())
    this
  }

  def updateBucket(bucket: mutable.BagBucket[A]): this.type


  def add(elem: A, count: Int): this.type = {
    this.getBucket(elem) match {
      case Some(b) => b add(elem, count)
      case None => updateBucket((bagConfiguration.newBuilder(elem) add(elem, count)).result())
    }
    this
  }

  def addBucket(bucket: collection.BagBucket[A]): this.type = {
    this.getBucket(bucket.sentinel) match {
      case Some(b) => b addBucket bucket
      case None => updateBucket((bagConfiguration.newBuilder(bucket.sentinel) addBucket bucket).result())
    }
    this
  }

  def -=(elem: A): this.type = this -= (elem -> 1)

  def -=(elemCount: (A, Int)): this.type = {
    val (elem, count) = elemCount
    update(elem, Math.max(this.multiplicity(elem) - count, 0))
  }

}


object Bag extends generic.MutableHashedBagFactory[mutable.Bag] {

  implicit def canBuildFrom[A](implicit bagConfiguration: mutable.HashedBagConfiguration[A]): CanBuildFrom[Coll, A, mutable.Bag[A]] = bagCanBuildFrom[A]

  def empty[A](implicit bagConfiguration: mutable.HashedBagConfiguration[A]): mutable.Bag[A] = mutable.HashBag.empty[A]
}