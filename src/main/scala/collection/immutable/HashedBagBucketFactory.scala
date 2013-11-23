package scala.collection.immutable

import scala.collection.immutable
import scala.util.hashing.Hashing


trait HashedBagBucketFactory[A]
  extends immutable.BagBucketFactory[A]
  with collection.HashedBagBucketFactory[A, immutable.BagBucket[A]]
  with Equiv[A]
  with Hashing[A]

object HashedBagBucketFactory {

  def ofMultiplicities[A](implicit equivClass: Equiv[A], hashing: Hashing[A]): immutable.HashedBagBucketFactory[A] = new immutable.HashedBagBucketFactory[A] {
    def empty(sentinel: A): BagBucket[A] = new immutable.MultiplicityBagBucket(sentinel, 0)

    def hash(x: A): Int = hashing.hash(x)

    def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)
  }

  def ofVectors[A](implicit equivClass: Equiv[A], hashing: Hashing[A]): immutable.HashedBagBucketFactory[A] = new immutable.HashedBagBucketFactory[A] {
    def empty(sentinel: A): BagBucket[A] = new immutable.VectorBagBucket(sentinel, immutable.Vector.empty[A])

    def hash(x: A): Int = hashing.hash(x)

    def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)
  }

}