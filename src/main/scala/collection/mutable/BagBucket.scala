package scala.collection.mutable

import scala.collection._
import scala.collection.generic.GrowableBag

trait BagBucket[A]
  extends scala.collection.BagBucket[A]
  with GrowableBag[A] {

  protected override type BagBucket[X] = mutable.BagBucket[X]

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

  def +(elem: A): BagBucket[A] = {
    new mutable.MultiplicityBagBucket[A](sentinel, multiplicity + 1)
  }


  def added(elem: A, count: Int): BagBucket[A] = {
    new mutable.MultiplicityBagBucket[A](sentinel, multiplicity + Math.max(count, 0))
  }


  def add(elem: A, count: Int): this.type = {
    this.multiplicity += Math.max(count, 0)
    this
  }

  def addBucket(bucket: collection.BagBucket[A]): this.type = {
    this.multiplicity += bucket.multiplicity
    this
  }

  def addedBucket(bucket: collection.BagBucket[A]): BagBucket[A] = {
    new mutable.MultiplicityBagBucket[A](sentinel, this.multiplicity + bucket.multiplicity)
  }

  def -(elem: A): BagBucket[A] = {
    new mutable.MultiplicityBagBucket[A](sentinel, Math.max(0, multiplicity - 1))
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


  def added(elem: A, count: Int) = {
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

  def +(elem: A) = {
    new mutable.VectorBagBucket(sentinel, vec :+ elem)
  }


  def addedBucket(bucket: collection.BagBucket[A]) = {
    new mutable.VectorBagBucket[A](sentinel, vec ++ bucket)
  }

  def -(elem: A) = {
    if (vec.isEmpty) this
    else new mutable.VectorBagBucket(sentinel, vec.tail)
  }
}


