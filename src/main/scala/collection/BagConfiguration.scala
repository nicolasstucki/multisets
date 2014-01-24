package scala.collection

import scala.util.hashing.Hashing


trait BagConfiguration[A, +BagBucket <: collection.BagBucket[A]] {

  /**
   *
   * @param sentinel
   * @return
   */
  def empty(sentinel: A): BagBucket

  /**
   *
   * @return
   */
  def equivClass: Equiv[A]

  /**
   *
   * @param x
   * @param y
   * @return
   */
  def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)

  /**
   *
   * @param elem
   * @param count
   * @return
   */
  def bucketFrom(elem: A, count: Int): BagBucket = {
    val bb = newBuilder(elem)
    bb.add(elem, count)
    bb.result()
  }

  /**
   *
   * @param bucket
   * @return
   */
  def bucketFrom(bucket: collection.BagBucket[A]): BagBucket = (newBuilder(bucket.sentinel) addBucket bucket).result()

  /**
   *
   * @param bucket
   * @param otherBucket
   * @return
   */
  def bucketFrom(bucket: collection.BagBucket[A], otherBucket: collection.BagBucket[A]): BagBucket =
    (newBuilder(bucket.sentinel) addBucket bucket addBucket otherBucket).result()

  /**
   *
   * @param sentinel
   * @return
   */
  def newBuilder(sentinel: A): mutable.BagBucketBuilder[A, BagBucket]

}

trait HashedBagConfiguration[A, +BagBucket <: collection.BagBucket[A]] extends BagConfiguration[A, BagBucket] {
  def equivClass: Equiv[A] with Hashing[A]
}

object HashedBagConfiguration {

  private[collection] def defaultHashedEquiv[A]: Equiv[A] with Hashing[A] = {
    new Equiv[A] with Hashing[A] {
      def hash(x: A): Int = x.hashCode()

      def equiv(x: A, y: A): Boolean = x == y
    }
  }

}

trait SortedBagConfiguration[A, +BagBucket <: collection.BagBucket[A]] extends BagConfiguration[A, BagBucket] {
  def equivClass: Ordering[A]
}
