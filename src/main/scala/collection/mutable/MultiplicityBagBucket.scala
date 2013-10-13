package scala.collection.mutable

import scala.collection.mutable
import scala.collection


class MultiplicityBagBucket[A](val sentinel: A, var multiplicity: Int)
  extends scala.collection.MultiplicityBagBucket[A]
  with mutable.BagBucket[A] {

  def update(elem: A, multiplicity: Int): Unit = if (elem == sentinel) this.multiplicity = multiplicity

  def +=(elem: A): this.type = {
    assert(elem == sentinel)
    multiplicity += 1
    this
  }

  def -=(elem: A): this.type = {
    assert(elem == sentinel)
    multiplicity = Math.max(0, multiplicity - 1)
    this
  }

  def +(elem: A): collection.BagBucket[A] = {
    assert(elem == sentinel)
    new mutable.MultiplicityBagBucket[A](sentinel, multiplicity + 1)
  }

  def -(elem: A): collection.BagBucket[A] = {
    assert(elem == sentinel)
    new mutable.MultiplicityBagBucket[A](sentinel, Math.max(0, multiplicity - 1))
  }
}


object MultiplicityBagBucketFactory {
  def of[A] = new mutable.MultiplicityBagBucketFactory[A]
}


class MultiplicityBagBucketFactory[A] extends scala.collection.mutable.BagBucketFactory[A] {

  def empty(sentinel: A): mutable.MultiplicityBagBucket[A] = new mutable.MultiplicityBagBucket(sentinel, 0)

  override def apply(elem: A): mutable.MultiplicityBagBucket[A] = new mutable.MultiplicityBagBucket(elem, 1)

  override def apply(elem: A, multi: Int): mutable.MultiplicityBagBucket[A] = new mutable.MultiplicityBagBucket(elem, multi)

}