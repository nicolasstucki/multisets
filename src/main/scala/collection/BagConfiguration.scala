package scala.collection

import scala.util.hashing.Hashing


trait BagConfiguration[A, +BagBucket <: collection.BagBucket[A]] extends Equiv[A] {

  def empty(sentinel: A): BagBucket

  protected def equivClass: Equiv[A]

  def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)

  def bucketFrom(elem: A, count: Int): BagBucket = {
    val bb = newBuilder(elem)
    bb.add(elem, count)
    bb.result()
  }

  def bucketFrom(bucket: collection.BagBucket[A]): BagBucket = (newBuilder(bucket.sentinel) addBucket bucket).result()

  def bucketFrom(bucket: collection.BagBucket[A], otherBucket: collection.BagBucket[A]): BagBucket = (newBuilder(bucket.sentinel) addBucket bucket addBucket otherBucket).result()

  def newBuilder(sentinel: A): mutable.BagBucketBuilder[A, BagBucket]

}

trait HashedBagConfiguration[A, +BagBucket <: collection.BagBucket[A]] extends BagConfiguration[A, BagBucket] with Hashing[A] {
  protected def equivClass: Equiv[A] with Hashing[A]

  def hash(x: A): Int = equivClass.hash(x)
}

object HashedBagConfiguration {

  private[collection] def defaultHashedEquiv[A]: Equiv[A] with Hashing[A] = {
    new Equiv[A] with Hashing[A] {
      def hash(x: A): Int = x.hashCode()

      def equiv(x: A, y: A): Boolean = x == y
    }
  }

}

trait SortedBagConfiguration[A, +BagBucket <: collection.BagBucket[A]] extends BagConfiguration[A, BagBucket] with Ordering[A] {
  protected def equivClass: Ordering[A]

  def compare(x: A, y: A): Int = equivClass.compare(x, y)

  new Equiv[String] with Hashing[String] {
    def hash(x: String): Int = x.toLowerCase.hashCode

    def equiv(x: String, y: String): Boolean = (x compareToIgnoreCase y) == 0
  }

}
