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

trait HashedBagConfiguration[A]
  extends immutable.BagConfiguration[A]
  with collection.HashedBagConfiguration[A, immutable.BagBucket[A]]

trait SortedBagConfiguration[A]
  extends immutable.BagConfiguration[A]
  with collection.SortedBagConfiguration[A, immutable.BagBucket[A]]


object BagConfiguration {

  private class MultiplicityBagConfiguration[A]
    extends immutable.BagConfiguration[A] {
    def empty(sentinel: A): immutable.MultiplicityBagBucket[A] = new immutable.MultiplicityBagBucket(sentinel, 0)

    def equiv(x: A, y: A): Boolean = x == y
  }

  private abstract class BagBucketBagConfiguration[A](val equivClass: Equiv[A]) extends immutable.BagConfiguration[A] {
    def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)
  }

  private class VectorBagConfiguration[A](val equivClass: Equiv[A]) extends immutable.BagConfiguration[A] {
    def empty(sentinel: A): immutable.VectorBagBucket[A] = new immutable.VectorBagBucket(sentinel, immutable.Vector.empty[A])

    def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)
  }


  object Hashed {

    private class MultiplicityBagConfiguration[A]
      extends immutable.BagConfiguration.MultiplicityBagConfiguration[A]
      with immutable.HashedBagConfiguration[A] {
      def hash(x: A): Int = x.hashCode()
    }

    private class BagBucketBagConfiguration[A](equivClass: Equiv[A], hashing: Hashing[A])
      extends BagConfiguration.BagBucketBagConfiguration[A](equivClass)
      with immutable.HashedBagConfiguration[A] {

      def empty(sentinel: A): immutable.BagBucketBag[A] = new immutable.BagBucketBag(sentinel, immutable.HashBag.empty[A](ofMultiplicities[A]))

      def hash(x: A): Int = hashing.hash(x)
    }

    private class VectorBagConfiguration[A](equivClass: Equiv[A], hashing: Hashing[A])
      extends BagConfiguration.VectorBagConfiguration[A](equivClass)
      with immutable.HashedBagConfiguration[A] {
      def hash(x: A): Int = hashing.hash(x)
    }


    def ofMultiplicities[A]: immutable.HashedBagConfiguration[A] = new BagConfiguration.Hashed.MultiplicityBagConfiguration

    def ofBagBucketBag[A](implicit equivClass: Equiv[A], hashing: Hashing[A]): immutable.HashedBagConfiguration[A] = new BagConfiguration.Hashed.BagBucketBagConfiguration(equivClass, hashing)

    def ofVectors[A](implicit equivClass: Equiv[A], hashing: Hashing[A]): immutable.HashedBagConfiguration[A] = new BagConfiguration.Hashed.VectorBagConfiguration(equivClass, hashing)
  }


  object Sorted {

    private class MultiplicityBagConfiguration[A](val ordering: Ordering[A])
      extends BagConfiguration.MultiplicityBagConfiguration[A]
      with SortedBagConfiguration[A]

    private class BagBucketBagConfiguration[A](val ordering: Ordering[A])
      extends BagConfiguration.BagBucketBagConfiguration[A](ordering)
      with SortedBagConfiguration[A] {
      def empty(sentinel: A): immutable.BagBucketBag[A] = new immutable.BagBucketBag(sentinel, immutable.TreeBag.empty[A](ofMultiplicities[A](ordering)))
    }


    private class VectorBagConfiguration[A](val ordering: Ordering[A])
      extends BagConfiguration.VectorBagConfiguration[A](ordering)
      with SortedBagConfiguration[A]


    def ofMultiplicities[A](implicit ordering: Ordering[A]): immutable.SortedBagConfiguration[A] = new MultiplicityBagConfiguration(ordering)

    def ofBagBucketBag[A](implicit ordering: Ordering[A]): immutable.SortedBagConfiguration[A] = new BagBucketBagConfiguration(ordering)

    def ofVectors[A](implicit ordering: Ordering[A]): immutable.SortedBagConfiguration[A] = new VectorBagConfiguration(ordering)

  }

}






