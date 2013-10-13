package scala.collection.immutable

import scala.collection.immutable


class MultiplicityBagBucket[A](val sentinel: A, val multiplicity: Int)
  extends scala.collection.MultiplicityBagBucket[A]
  with BagBucket[A] {

  def +(elem: A): MultiplicityBagBucket[A] = {
    assert(elem == sentinel)
    new MultiplicityBagBucket(sentinel, multiplicity + 1)
  }

  def -(elem: A): MultiplicityBagBucket[A] = {
    assert(elem == sentinel)
    new MultiplicityBagBucket(sentinel, Math.max(0, multiplicity - 1))
  }

}


object MultiplicityBagBucketFactory {
  def of[A] = new MultiplicityBagBucketFactory[A]
}

class MultiplicityBagBucketFactory[A] extends immutable.BagBucketFactory[A] {

  def empty(sentinel: A): MultiplicityBagBucket[A] = new MultiplicityBagBucket(sentinel, 0)

  override def apply(elem: A): MultiplicityBagBucket[A] = new MultiplicityBagBucket(elem, 1)

  override def apply(elem: A, multiplicity: Int): MultiplicityBagBucket[A] = new MultiplicityBagBucket(elem, multiplicity)

}