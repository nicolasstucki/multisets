package scala.collection.immutable

import scala.collection._

trait BagBucketFactory[A]
  extends scala.collection.BagBucketFactory[A, immutable.BagBucket[A]] {

  def newBuilder(sentinel: A) = mutable.BagBucketBuilder(empty(sentinel))

}

object BagBucketFactory {

  def ofMultiplicities[A] = new MultiplicityBagBucketFactory[A]

  def ofVectors[A](implicit equivClass: Equiv[A]) = new VectorBagBucketFactory[A](equivClass)

  class MultiplicityBagBucketFactory[A] extends immutable.BagBucketFactory[A] {

    def empty(sentinel: A): immutable.MultiplicityBagBucket[A] = new immutable.MultiplicityBagBucket(sentinel, 0)

    def from(elem: A): immutable.MultiplicityBagBucket[A] = new MultiplicityBagBucket(elem, 1)

    def equiv(x: A, y: A): Boolean = x == y
  }

  class VectorBagBucketFactory[A](equivClass: Equiv[A]) extends immutable.BagBucketFactory[A] {
    def empty(sentinel: A): immutable.VectorBagBucket[A] = new immutable.VectorBagBucket(sentinel, immutable.Vector.empty[A])

    def from(elem: A): immutable.VectorBagBucket[A] = new immutable.VectorBagBucket(elem, immutable.Vector(elem))

    def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)
  }

}
