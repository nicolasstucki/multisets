package scala.collection.mutable

import scala.collection.mutable
import scala.collection.immutable.Vector
import scala.util.hashing.Hashing


trait HashedBagBucketFactory[A]
  extends mutable.BagBucketFactory[A]
  with collection.HashedBagBucketFactory[A, mutable.BagBucket[A]]
  with Equiv[A]
  with Hashing[A]

object HashedBagBucketFactory {

  def ofMultiplicities[A](implicit equivClass: Equiv[A], hashing: Hashing[A]): mutable.HashedBagBucketFactory[A] = new mutable.HashedBagBucketFactory[A] {
    def empty(sentinel: A): mutable.BagBucket[A] = new mutable.MultiplicityBagBucket(sentinel, 0)

    def hash(x: A): Int = hashing.hash(x)

    def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)
  }

  def ofVectors[A](implicit equivClass: Equiv[A], hashing: Hashing[A]): mutable.HashedBagBucketFactory[A] = new mutable.HashedBagBucketFactory[A] {
    def empty(sentinel: A): mutable.BagBucket[A] = new mutable.VectorBagBucket(sentinel, Vector.empty[A])

    def hash(x: A): Int = hashing.hash(x)

    def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)
  }

}