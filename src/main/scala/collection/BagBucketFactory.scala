package scala.collection

import scala.util.hashing.Hashing


trait BagBucketFactory[A, +BagBucket <: collection.BagBucket[A]] extends Equiv[A] {

  def empty(sentinel: A): BagBucket

  def from(bucket: collection.BagBucket[A]): BagBucket = (newBuilder(bucket.sentinel) addBucket bucket).result()

  def from(bucket: collection.BagBucket[A], otherBucket: collection.BagBucket[A]): BagBucket = (newBuilder(bucket.sentinel) addBucket bucket addBucket otherBucket).result()

  def newBuilder(sentinel: A): mutable.BagBucketBuilder[A, BagBucket]

}

trait HashedBagBucketFactory[A, +BagBucket <: collection.BagBucket[A]] extends BagBucketFactory[A, BagBucket] with Hashing[A]

trait SortedBagBucketFactory[A, +BagBucket <: collection.BagBucket[A]] extends BagBucketFactory[A, BagBucket] with Ordering[A] {
  def ordering: Ordering[A]

  def compare(x: A, y: A): Int = ordering.compare(x, y)
}
