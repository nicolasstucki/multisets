package scala.collection.mutable

import scala.collection.{immutable, mutable}
import scala.util.hashing.Hashing
import scala.collection.immutable.Vector

trait BagConfiguration[A]
  extends collection.BagConfiguration[A, mutable.BagBucket[A]] {

  def newBuilder(sentinel: A) = new mutable.GrowingBagBucketBuilder[A](empty(sentinel))
}


trait HashedBagConfiguration[A] extends mutable.BagConfiguration[A] with collection.HashedBagConfiguration[A, mutable.BagBucket[A]]

object HashedBagConfiguration {

  private class HashedMultiplicityBagConfiguration[A](protected val equivClass: Equiv[A] with Hashing[A]) extends mutable.HashedBagConfiguration[A] {
    def empty(sentinel: A): mutable.MultiplicityBagBucket[A] = new mutable.MultiplicityBagBucket(sentinel, 0)
  }

  private class HashedBagOfMultiplicitiesBagBucketConfiguration[A](protected val equivClass: Equiv[A] with Hashing[A]) extends mutable.HashedBagConfiguration[A] {
    def empty(sentinel: A): mutable.BagOfMultiplicitiesBagBucket[A] = new mutable.BagOfMultiplicitiesBagBucket(sentinel, mutable.HashBag.empty[A](ofMultiplicities[A]))
  }

  private class HashedVectorBagConfiguration[A](protected val equivClass: Equiv[A] with Hashing[A])
    extends mutable.HashedBagConfiguration[A] {
    def empty(sentinel: A): mutable.VectorBagBucket[A] = new mutable.VectorBagBucket(sentinel, immutable.Vector.empty[A])
  }


  def ofMultiplicities[A]: mutable.HashedBagConfiguration[A] = new HashedMultiplicityBagConfiguration(collection.HashedBagConfiguration.defaultHashedEquiv[A])

  def ofBagOfMultiplicities[A](equivClass: Equiv[A] with Hashing[A]): mutable.HashedBagConfiguration[A] = new HashedBagOfMultiplicitiesBagBucketConfiguration(equivClass)

  def keepAll[A]: mutable.HashedBagConfiguration[A] = new HashedVectorBagConfiguration(collection.HashedBagConfiguration.defaultHashedEquiv[A])

  def keepAll[A](equivClass: Equiv[A] with Hashing[A]): mutable.HashedBagConfiguration[A] = new HashedVectorBagConfiguration(equivClass)

}


trait SortedBagConfiguration[A] extends mutable.BagConfiguration[A] with collection.SortedBagConfiguration[A, mutable.BagBucket[A]]

object SortedBagConfiguration {

  private class SortedMultiplicityBagConfiguration[A](protected val equivClass: Ordering[A]) extends mutable.SortedBagConfiguration[A] {
    def empty(sentinel: A): mutable.MultiplicityBagBucket[A] = new mutable.MultiplicityBagBucket(sentinel, 0)

  }

  private class SortedBagOfMultiplicitiesBagBucketConfiguration[A](protected val equivClass: Ordering[A], innerOrdering: Ordering[A]) extends mutable.SortedBagConfiguration[A] {
    def empty(sentinel: A): mutable.BagOfMultiplicitiesBagBucket[A] = ??? // new mutable.BagBucketBag(sentinel, mutable.TreeBag.empty[A](ofMultiplicities[A](innerOrdering)))
  }


  private class SortedVectorBagConfiguration[A](protected val equivClass: Ordering[A]) extends mutable.SortedBagConfiguration[A] {
    def empty(sentinel: A): mutable.VectorBagBucket[A] = new mutable.VectorBagBucket(sentinel, immutable.Vector.empty[A])

  }


  def ofMultiplicities[A](implicit equivClass: Ordering[A]): mutable.SortedBagConfiguration[A] = new SortedMultiplicityBagConfiguration(equivClass)

  def ofBagOfMultiplicities[A](equivClass: Ordering[A])(implicit innerOrdering: Ordering[A]): mutable.SortedBagConfiguration[A] = new SortedBagOfMultiplicitiesBagBucketConfiguration(equivClass, innerOrdering)

  def keepAll[A](implicit equivClass: Ordering[A]): mutable.SortedBagConfiguration[A] = new SortedVectorBagConfiguration(equivClass)


}


