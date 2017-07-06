package scala.collection.mutable

import scala.collection._
import scala.collection.generic.CanBuildFrom


trait Bag[A]
  extends scala.collection.Bag[A]
  with mutable.BagLike[A, mutable.Bag[A]]
  with generic.GrowableBag[A] {


  def update(elem: A, count: Int): this.type = setMultiplicity(elem, count)

  def setMultiplicity(elem: A, count: Int): this.type = {
    val b = bagConfiguration.newBuilder(elem)
    b.add(elem, count)
    updateBucket(b.result())
    this
  }

  protected def updateBucket(bucket: mutable.BagBucket[A]): this.type


  def add(elem: A, count: Int): this.type = {
    this.getBucket(elem) match {
      case Some(b) => updateBucket(b add(elem, count))
      case None => updateBucket((bagConfiguration.newBuilder(elem) add(elem, count)).result())
    }
    this
  }

  def addBucket(bucket: scala.collection.BagBucket[A]): this.type = {
    this.getBucket(bucket.sentinel) match {
      case Some(b) => updateBucket(b addBucket bucket)
      case None => updateBucket((bagConfiguration.newBuilder(bucket.sentinel) addBucket bucket).result())
    }
    this
  }

  def -=(elem: A): this.type = this -= (elem -> 1)

  def -=(elemCount: (A, Int)): this.type = remove(elemCount._1, elemCount._2)

  def remove(elem: A, count: Int): this.type = {
    val amount = Math.min(this.multiplicity(elem), count)
    if (amount > 0)
      this.getBucket(elem) match {
        case Some(b) => updateBucket(b.remove(elem, amount))
        case None => updateBucket((bagConfiguration.newBuilder(elem) add(elem, count)).result())
      }
    this
  }

  def removeAll(elem: A): this.type = {
    this.getBucket(elem) match {
      case Some(b) => updateBucket(b.removeAll(elem))
      case None =>
    }
    this
  }
}


object Bag extends generic.MutableHashedBagFactory[mutable.Bag] {

  implicit def canBuildFrom[A](implicit bagConfiguration: mutable.HashedBagConfiguration[A]): CanBuildFrom[Coll, A, mutable.Bag[A]] = bagCanBuildFrom[A]

  def empty[A](implicit bagConfiguration: mutable.HashedBagConfiguration[A]): mutable.Bag[A] = mutable.HashBag.empty[A]
}