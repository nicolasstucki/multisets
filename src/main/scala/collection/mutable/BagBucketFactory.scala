package scala.collection.mutable

import scala.collection.{immutable, mutable}
import scala.util.hashing.Hashing
import scala.collection.immutable.Vector

trait BagBucketConfiguration[A]
  extends collection.BagBucketConfiguration[A, mutable.BagBucket[A]] {

  def newBuilder(sentinel: A) = new mutable.GrowingBagBucketBuilder[A](empty(sentinel))
}

trait HashedBagBucketConfiguration[A]
  extends mutable.BagBucketConfiguration[A]
  with collection.HashedBagBucketConfiguration[A, mutable.BagBucket[A]]


trait SortedBagBucketConfiguration[A]
  extends mutable.BagBucketConfiguration[A]
  with collection.SortedBagBucketConfiguration[A, mutable.BagBucket[A]]


object BagBucketConfiguration {

  private class MultiplicityBagBucketConfiguration[A] extends mutable.BagBucketConfiguration[A] {
    def empty(sentinel: A): mutable.MultiplicityBagBucket[A] = new mutable.MultiplicityBagBucket(sentinel, 0)

    def equiv(x: A, y: A): Boolean = x == y
  }

  private abstract class BagBucketBagConfiguration[A](val equivClass: Equiv[A]) extends mutable.BagBucketConfiguration[A] {
    def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)
  }

  private class VectorBagBucketConfiguration[A](val equivClass: Equiv[A]) extends mutable.BagBucketConfiguration[A] {
    def empty(sentinel: A): mutable.VectorBagBucket[A] = new mutable.VectorBagBucket(sentinel, immutable.Vector.empty[A])

    def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)
  }

  object Hashed {

    private class MultiplicityBagBucketConfiguration[A]
      extends mutable.BagBucketConfiguration.MultiplicityBagBucketConfiguration[A]
      with mutable.HashedBagBucketConfiguration[A] {
      def hash(x: A): Int = x.hashCode()
    }

    private class BagBucketBagConfiguration[A](equivClass: Equiv[A], hashing: Hashing[A])
      extends mutable.BagBucketConfiguration.BagBucketBagConfiguration[A](equivClass)
      with mutable.HashedBagBucketConfiguration[A] {
      def empty(sentinel: A): mutable.BagBucketBag[A] = new mutable.BagBucketBag(sentinel, mutable.HashBag.empty[A](ofMultiplicities[A]))

      def hash(x: A): Int = hashing.hash(x)
    }

    private class VectorBagBucketConfiguration[A](equivClass: Equiv[A], hashing: Hashing[A])
      extends mutable.BagBucketConfiguration.VectorBagBucketConfiguration[A](equivClass)
      with mutable.HashedBagBucketConfiguration[A] {
      def hash(x: A): Int = hashing.hash(x)
    }


    def ofMultiplicities[A]: mutable.HashedBagBucketConfiguration[A] = new mutable.BagBucketConfiguration.Hashed.MultiplicityBagBucketConfiguration

    def ofBagBucketBag[A](implicit equivClass: Equiv[A], hashing: Hashing[A]): mutable.HashedBagBucketConfiguration[A] = new mutable.BagBucketConfiguration.Hashed.BagBucketBagConfiguration(equivClass, hashing)

    def ofVectors[A](implicit equivClass: Equiv[A], hashing: Hashing[A]): mutable.HashedBagBucketConfiguration[A] = new mutable.BagBucketConfiguration.Hashed.VectorBagBucketConfiguration(equivClass, hashing)

  }

  object Sorted {

    private class MultiplicityBagBucketConfiguration[A](val ordering: Ordering[A])
      extends mutable.BagBucketConfiguration.MultiplicityBagBucketConfiguration[A]
      with mutable.SortedBagBucketConfiguration[A]

    private class BagBucketBagConfiguration[A](val ordering: Ordering[A])
      extends mutable.BagBucketConfiguration.BagBucketBagConfiguration[A](ordering)
      with mutable.SortedBagBucketConfiguration[A] {
      def empty(sentinel: A): mutable.BagBucketBag[A] = ??? // new mutable.BagBucketBag(sentinel, mutable.TreeBag.empty[A](ofMultiplicities[A](ordering)))
    }


    private class VectorBagBucketConfiguration[A](val ordering: Ordering[A])
      extends mutable.BagBucketConfiguration.VectorBagBucketConfiguration[A](ordering)
      with mutable.SortedBagBucketConfiguration[A]


    def ofMultiplicities[A](implicit ordering: Ordering[A]): mutable.SortedBagBucketConfiguration[A] = new MultiplicityBagBucketConfiguration(ordering)

    def ofBagBucketBag[A](implicit ordering: Ordering[A]): mutable.SortedBagBucketConfiguration[A] = new BagBucketBagConfiguration(ordering)

    def ofVectors[A](implicit ordering: Ordering[A]): mutable.SortedBagBucketConfiguration[A] = new VectorBagBucketConfiguration(ordering)

  }

}


