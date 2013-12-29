package scala.collection.immutable

import scala.collection._
import scala.collection
import scala.util.hashing.Hashing

trait BagBucketConfiguration[A]
  extends collection.BagBucketConfiguration[A, immutable.BagBucket[A]] {

  override def from(bucket: collection.BagBucket[A]): BagBucket[A] = bucket match {
    case immutableBucket: immutable.BagBucket[A] => immutableBucket
    case _ => super.from(bucket)
  }

  def newBuilder(sentinel: A) = mutable.BagBucketBuilder(empty(sentinel))

}

trait HashedBagBucketConfiguration[A]
  extends immutable.BagBucketConfiguration[A]
  with collection.HashedBagBucketConfiguration[A, immutable.BagBucket[A]]

trait SortedBagBucketConfiguration[A]
  extends immutable.BagBucketConfiguration[A]
  with collection.SortedBagBucketConfiguration[A, immutable.BagBucket[A]]


object BagBucketConfiguration {

  private class MultiplicityBagBucketConfiguration[A]
    extends immutable.BagBucketConfiguration[A] {
    def empty(sentinel: A): immutable.MultiplicityBagBucket[A] = new immutable.MultiplicityBagBucket(sentinel, 0)

    def equiv(x: A, y: A): Boolean = x == y
  }

  private abstract class BagBucketBagConfiguration[A](val equivClass: Equiv[A]) extends immutable.BagBucketConfiguration[A] {
    def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)
  }

  private class VectorBagBucketConfiguration[A](val equivClass: Equiv[A]) extends immutable.BagBucketConfiguration[A] {
    def empty(sentinel: A): immutable.VectorBagBucket[A] = new immutable.VectorBagBucket(sentinel, immutable.Vector.empty[A])

    def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)
  }


  object Hashed {

    private class MultiplicityBagBucketConfiguration[A]
      extends immutable.BagBucketConfiguration.MultiplicityBagBucketConfiguration[A]
      with immutable.HashedBagBucketConfiguration[A] {
      def hash(x: A): Int = x.hashCode()
    }

    private class BagBucketBagConfiguration[A](equivClass: Equiv[A], hashing: Hashing[A])
      extends BagBucketConfiguration.BagBucketBagConfiguration[A](equivClass)
      with immutable.HashedBagBucketConfiguration[A] {

      def empty(sentinel: A): immutable.BagBucketBag[A] = ??? // new immutable.BagBucketBag(sentinel, mutable.HashBag.empty[A](ofMultiplicities[A]))

      def hash(x: A): Int = hashing.hash(x)
    }

    private class VectorBagBucketConfiguration[A](equivClass: Equiv[A], hashing: Hashing[A])
      extends BagBucketConfiguration.VectorBagBucketConfiguration[A](equivClass)
      with immutable.HashedBagBucketConfiguration[A] {
      def hash(x: A): Int = hashing.hash(x)
    }


    def ofMultiplicities[A]: immutable.HashedBagBucketConfiguration[A] = new BagBucketConfiguration.Hashed.MultiplicityBagBucketConfiguration

    def ofBagBucketBag[A](implicit equivClass: Equiv[A], hashing: Hashing[A]): immutable.HashedBagBucketConfiguration[A] = new BagBucketConfiguration.Hashed.BagBucketBagConfiguration(equivClass, hashing)

    def ofVectors[A](implicit equivClass: Equiv[A], hashing: Hashing[A]): immutable.HashedBagBucketConfiguration[A] = new BagBucketConfiguration.Hashed.VectorBagBucketConfiguration(equivClass, hashing)
  }


  object Sorted {

    private class MultiplicityBagBucketConfiguration[A](val ordering: Ordering[A])
      extends BagBucketConfiguration.MultiplicityBagBucketConfiguration[A]
      with SortedBagBucketConfiguration[A]

    private class BagBucketBagConfiguration[A](val ordering: Ordering[A])
      extends BagBucketConfiguration.BagBucketBagConfiguration[A](ordering)
      with SortedBagBucketConfiguration[A] {
      def empty(sentinel: A): immutable.BagBucketBag[A] = new immutable.BagBucketBag(sentinel, immutable.TreeBag.empty[A](ofMultiplicities[A](ordering)))
    }


    private class VectorBagBucketConfiguration[A](val ordering: Ordering[A])
      extends BagBucketConfiguration.VectorBagBucketConfiguration[A](ordering)
      with SortedBagBucketConfiguration[A]


    def ofMultiplicities[A](implicit ordering: Ordering[A]): immutable.SortedBagBucketConfiguration[A] = new MultiplicityBagBucketConfiguration(ordering)

    def ofBagBucketBag[A](implicit ordering: Ordering[A]): immutable.SortedBagBucketConfiguration[A] = new BagBucketBagConfiguration(ordering)

    def ofVectors[A](implicit ordering: Ordering[A]): immutable.SortedBagBucketConfiguration[A] = new VectorBagBucketConfiguration(ordering)

  }

}






