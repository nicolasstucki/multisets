package scala.collection

import scala.util.hashing.Hashing


trait BagConfiguration[A, +BagBucket <: collection.BagBucket[A]] extends Equiv[A] {

  def empty(sentinel: A): BagBucket

  def bucketFrom(elem: A, count: Int): BagBucket = {
    val bb = newBuilder(elem)
    bb.add(elem, count)
    bb.result()
  }

  def bucketFrom(bucket: collection.BagBucket[A]): BagBucket = (newBuilder(bucket.sentinel) addBucket bucket).result()

  def bucketFrom(bucket: collection.BagBucket[A], otherBucket: collection.BagBucket[A]): BagBucket = (newBuilder(bucket.sentinel) addBucket bucket addBucket otherBucket).result()

  def newBuilder(sentinel: A): mutable.BagBucketBuilder[A, BagBucket]

}

trait HashedBagConfiguration[A, +BagBucket <: collection.BagBucket[A]] extends BagConfiguration[A, BagBucket] with Hashing[A]

trait SortedBagConfiguration[A, +BagBucket <: collection.BagBucket[A]] extends BagConfiguration[A, BagBucket] with Ordering[A] {
  def ordering: Ordering[A]

  def compare(x: A, y: A): Int = ordering.compare(x, y)
}
