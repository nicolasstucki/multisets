package scala.collection.mutable

import scala.collection._
import scala.collection.generic.GrowableBag
import scala.collection
import scala.collection.immutable.Vector

trait BagBucket[A]
  extends scala.collection.BagBucket[A]
  with GrowableBag[A] {

  final protected override type BagBucket = mutable.BagBucket[A]

}


class MultiplicityBagBucket[A](val sentinel: A, var multiplicity: Int)
  extends scala.collection.MultiplicityBagBucket[A]
  with mutable.BagBucket[A] {


  def update(elem: A, multiplicity: Int): this.type = {
    if (elem == sentinel) this.multiplicity = multiplicity
    this
  }


  def clear(): Unit = multiplicity = 0

  override def +=(elem: A): this.type = {
    multiplicity += 1
    this
  }

  def -=(elem: A): this.type = {
    multiplicity = Math.max(0, multiplicity - 1)
    this
  }

  def added(elem: A, count: Int): BagBucket = {
    new mutable.MultiplicityBagBucket[A](sentinel, multiplicity + Math.max(count, 0))
  }


  def add(elem: A, count: Int): this.type = {
    this.multiplicity += Math.max(count, 0)
    this
  }

  def addBucket(bucket: collection.BagBucket[A]): this.type = {
    this.multiplicity += bucket.multiplicity(sentinel)
    this
  }

  def addedBucket(bucket: collection.BagBucket[A]): BagBucket = {
    new mutable.MultiplicityBagBucket[A](sentinel, this.multiplicity + bucket.multiplicity(sentinel))
  }

  override def -(elem: A): BagBucket = {
    new mutable.MultiplicityBagBucket[A](sentinel, Math.max(0, multiplicity - 1))
  }

  def intersect(that: collection.BagBucket[A]): BagBucket = new mutable.MultiplicityBagBucket(sentinel, Math.min(this.multiplicity, that.multiplicity(sentinel)))

  def diff(that: collection.BagBucket[A]): BagBucket = new mutable.MultiplicityBagBucket(sentinel, Math.max(this.multiplicity - that.multiplicity(sentinel), 0))

  def removed(elem: A, count: Int): BagBucket = new mutable.MultiplicityBagBucket(sentinel, Math.max(0, multiplicity - count))

}

class BagOfMultiplicitiesBagBucket[A](val sentinel: A, val bag: mutable.Bag[A])
  extends scala.collection.BagOfMultiplicitiesBagBucket[A]
  with mutable.BagBucket[A] {

  def clear(): Unit = bag.clear()

  def addBucket(bucket: collection.BagBucket[A]): this.type = {
    bag.addBucket(bucket)
    this
  }

  def add(elem: A, count: Int): this.type = {
    bag.add(elem, count)
    this
  }

  def removed(elem: A, count: Int): BagBucket = new mutable.BagOfMultiplicitiesBagBucket(sentinel, bag.removed(elem, count))

  override def -(elem: A): BagBucket = new mutable.BagOfMultiplicitiesBagBucket(sentinel, bag - elem)

  def addedBucket(bucket: collection.BagBucket[A]): BagBucket = new mutable.BagOfMultiplicitiesBagBucket(sentinel, bag addedBucket bucket)

  def added(elem: A, count: Int): BagBucket = new mutable.BagOfMultiplicitiesBagBucket(sentinel, bag.added(elem, count))

  def diff(that: collection.BagBucket[A]): BagBucket = that match {
    case bagBucketBag: collection.BagOfMultiplicitiesBagBucket[A] => new mutable.BagOfMultiplicitiesBagBucket(sentinel, bag diff bagBucketBag.bag)
    case _ => new mutable.BagOfMultiplicitiesBagBucket(sentinel, bag.diff(bag.empty ++ that))
  }

  def intersect(that: collection.BagBucket[A]): BagBucket = that match {
    case bagBucketBag: collection.BagOfMultiplicitiesBagBucket[A] => new mutable.BagOfMultiplicitiesBagBucket(sentinel, bag intersect bagBucketBag.bag)
    case _ => new mutable.BagOfMultiplicitiesBagBucket(sentinel, bag.intersect(bag.empty ++ that))
  }
}


class VectorBagBucket[A](val sentinel: A, initialVector: immutable.Vector[A])
  extends scala.collection.VectorBagBucket[A]
  with mutable.BagBucket[A] {

  var vec: Vector[A] = initialVector

  def vector: Vector[A] = vec

  def clear(): Unit = {
    vec = immutable.Vector.empty[A]
  }

  def result(): mutable.VectorBagBucket[A] = new mutable.VectorBagBucket[A](sentinel, vec)


  override def +=(elem: A) = {
    vec = vec :+ elem
    this
  }

  def -=(elem: A) = {
    vec = vec.init
    this
  }


  def added(elem: A, count: Int): mutable.VectorBagBucket[A] = {
    new mutable.VectorBagBucket[A](sentinel, vec ++ Iterator.fill(count)(elem))
  }


  def add(elem: A, count: Int) = {
    vec = vec ++ Iterator.fill(count)(elem)
    this
  }

  def addBucket(bucket: collection.BagBucket[A]) = {
    vec = vec ++ bucket
    this
  }

  def addedBucket(bucket: collection.BagBucket[A]): mutable.VectorBagBucket[A] = {
    new mutable.VectorBagBucket[A](sentinel, vec ++ bucket)
  }

  override def -(elem: A): mutable.VectorBagBucket[A] = {
    if (vec.isEmpty) this
    else new mutable.VectorBagBucket(sentinel, vec.tail)
  }

  def intersect(that: collection.BagBucket[A]): BagBucket = new mutable.VectorBagBucket[A](sentinel, this.toList.intersect(that.toSeq).toVector)

  def diff(that: collection.BagBucket[A]): BagBucket = new mutable.VectorBagBucket[A](sentinel, this.toList.diff(that.toSeq).toVector)

  def removed(elem: A, count: Int): BagBucket = {
    var c = count
    var v = Vector.empty[A]
    for (e <- iterator) {
      if (e == elem) {
        if (c > 0) {
          v = v :+ elem
          c -= 1
        }
      } else {
        v = v :+ elem
      }

    }
    new mutable.VectorBagBucket[A](sentinel, v)
  }
}


