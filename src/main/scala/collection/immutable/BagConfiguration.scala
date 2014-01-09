package scala.collection.immutable

import scala.collection._
import scala.collection
import scala.util.hashing.Hashing

trait BagConfiguration[A]
  extends collection.BagConfiguration[A, immutable.BagBucket[A]] {

  override def bucketFrom(bucket: collection.BagBucket[A]): BagBucket[A] = bucket match {
    case immutableBucket: immutable.BagBucket[A] => immutableBucket
    case _ => super.bucketFrom(bucket)
  }

  def newBuilder(sentinel: A) = mutable.BagBucketBuilder(empty(sentinel))

}

trait HashedBagConfiguration[A] extends immutable.BagConfiguration[A] with collection.HashedBagConfiguration[A, immutable.BagBucket[A]]

object HashedBagConfiguration {

  private class HashedMultiplicityBagConfiguration[A](protected val equivClass: Equiv[A] with Hashing[A]) extends immutable.HashedBagConfiguration[A] {
    def empty(sentinel: A): immutable.MultiplicityBagBucket[A] = new immutable.MultiplicityBagBucket(sentinel, 0)
  }

  private class HashedBagOfMultiplicitiesBagBucketConfiguration[A](protected val equivClass: Equiv[A] with Hashing[A]) extends immutable.HashedBagConfiguration[A] {
    def empty(sentinel: A): immutable.BagOfMultiplicitiesBagBucket[A] = new immutable.BagOfMultiplicitiesBagBucket(sentinel, immutable.HashBag.empty[A](compact[A]))
  }

  private class HashedVectorBagConfiguration[A](protected val equivClass: Equiv[A] with Hashing[A]) extends immutable.HashedBagConfiguration[A] {
    def empty(sentinel: A): immutable.VectorBagBucket[A] = new immutable.VectorBagBucket(sentinel, immutable.Vector.empty[A])
  }

  def compact[A]: immutable.HashedBagConfiguration[A] = new immutable.HashedBagConfiguration.HashedMultiplicityBagConfiguration(collection.HashedBagConfiguration.defaultHashedEquiv[A])

  def compactWithEquiv[A](equivClass: Equiv[A] with Hashing[A]): immutable.HashedBagConfiguration[A] = new HashedBagOfMultiplicitiesBagBucketConfiguration(equivClass)

  def keepAll[A]: immutable.HashedBagConfiguration[A] = new HashedVectorBagConfiguration(collection.HashedBagConfiguration.defaultHashedEquiv[A])

  def keepAll[A](equivClass: Equiv[A] with Hashing[A]): immutable.HashedBagConfiguration[A] = new HashedVectorBagConfiguration(equivClass)
}


trait SortedBagConfiguration[A] extends immutable.BagConfiguration[A] with collection.SortedBagConfiguration[A, immutable.BagBucket[A]]

object SortedBagConfiguration {

  private class SortedMultiplicityBagConfiguration[A](protected val equivClass: Ordering[A]) extends SortedBagConfiguration[A] {
    def empty(sentinel: A): BagBucket[A] = new immutable.MultiplicityBagBucket(sentinel, 0)
  }

  private class SortedBagOfMultiplicitiesBagBucketConfiguration[A](protected val equivClass: Ordering[A], innerOrdering: Ordering[A]) extends SortedBagConfiguration[A] {
    def empty(sentinel: A): immutable.BagOfMultiplicitiesBagBucket[A] = new immutable.BagOfMultiplicitiesBagBucket(sentinel, immutable.TreeBag.empty[A](compact[A](innerOrdering)))
  }

  private class SortedVectorBagConfiguration[A](protected val equivClass: Ordering[A]) extends SortedBagConfiguration[A] {
    def empty(sentinel: A): BagBucket[A] = new immutable.VectorBagBucket(sentinel, immutable.Vector.empty[A])
  }


  def compact[A](implicit equivClass: Ordering[A]): immutable.SortedBagConfiguration[A] = new SortedMultiplicityBagConfiguration(equivClass)

  def compactWithEquiv[A](equivClass: Ordering[A])(implicit innerOrdering: Ordering[A]): immutable.SortedBagConfiguration[A] = new SortedBagOfMultiplicitiesBagBucketConfiguration(equivClass, innerOrdering)

  def keepAll[A](implicit equivClass: Ordering[A]): immutable.SortedBagConfiguration[A] = new SortedVectorBagConfiguration(equivClass)

}






