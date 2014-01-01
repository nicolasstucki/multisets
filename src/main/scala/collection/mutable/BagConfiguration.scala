package scala.collection.mutable

import scala.collection.{immutable, mutable}
import scala.util.hashing.Hashing
import scala.collection.immutable.Vector

trait BagConfiguration[A]
  extends collection.BagConfiguration[A, mutable.BagBucket[A]] {

  def newBuilder(sentinel: A) = new mutable.GrowingBagBucketBuilder[A](empty(sentinel))
}

trait HashedBagConfiguration[A]
  extends mutable.BagConfiguration[A]
  with collection.HashedBagConfiguration[A, mutable.BagBucket[A]]


trait SortedBagConfiguration[A]
  extends mutable.BagConfiguration[A]
  with collection.SortedBagConfiguration[A, mutable.BagBucket[A]]


object BagConfiguration {

  private class MultiplicityBagConfiguration[A] extends mutable.BagConfiguration[A] {
    def empty(sentinel: A): mutable.MultiplicityBagBucket[A] = new mutable.MultiplicityBagBucket(sentinel, 0)

    def equiv(x: A, y: A): Boolean = x == y
  }

  private abstract class BagBucketBagConfiguration[A](val equivClass: Equiv[A]) extends mutable.BagConfiguration[A] {
    def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)
  }

  private class VectorBagConfiguration[A](val equivClass: Equiv[A]) extends mutable.BagConfiguration[A] {
    def empty(sentinel: A): mutable.VectorBagBucket[A] = new mutable.VectorBagBucket(sentinel, immutable.Vector.empty[A])

    def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)
  }

  object Hashed {

    private class MultiplicityBagConfiguration[A]
      extends mutable.BagConfiguration.MultiplicityBagConfiguration[A]
      with mutable.HashedBagConfiguration[A] {
      def hash(x: A): Int = x.hashCode()
    }

    private class BagBucketBagConfiguration[A](equivClass: Equiv[A], hashing: Hashing[A])
      extends mutable.BagConfiguration.BagBucketBagConfiguration[A](equivClass)
      with mutable.HashedBagConfiguration[A] {
      def empty(sentinel: A): mutable.BagBucketBag[A] = new mutable.BagBucketBag(sentinel, mutable.HashBag.empty[A](ofMultiplicities[A]))

      def hash(x: A): Int = hashing.hash(x)
    }

    private class VectorBagConfiguration[A](equivClass: Equiv[A], hashing: Hashing[A])
      extends mutable.BagConfiguration.VectorBagConfiguration[A](equivClass)
      with mutable.HashedBagConfiguration[A] {
      def hash(x: A): Int = hashing.hash(x)
    }


    def ofMultiplicities[A]: mutable.HashedBagConfiguration[A] = new mutable.BagConfiguration.Hashed.MultiplicityBagConfiguration

    def ofBagBucketBag[A](implicit equivClass: Equiv[A], hashing: Hashing[A]): mutable.HashedBagConfiguration[A] = new mutable.BagConfiguration.Hashed.BagBucketBagConfiguration(equivClass, hashing)

    def ofVectors[A](implicit equivClass: Equiv[A], hashing: Hashing[A]): mutable.HashedBagConfiguration[A] = new mutable.BagConfiguration.Hashed.VectorBagConfiguration(equivClass, hashing)

  }

  object Sorted {

    private class MultiplicityBagConfiguration[A](val ordering: Ordering[A])
      extends mutable.BagConfiguration.MultiplicityBagConfiguration[A]
      with mutable.SortedBagConfiguration[A]

    private class BagBucketBagConfiguration[A](val ordering: Ordering[A])
      extends mutable.BagConfiguration.BagBucketBagConfiguration[A](ordering)
      with mutable.SortedBagConfiguration[A] {
      def empty(sentinel: A): mutable.BagBucketBag[A] = ??? // new mutable.BagBucketBag(sentinel, mutable.TreeBag.empty[A](ofMultiplicities[A](ordering)))
    }


    private class VectorBagConfiguration[A](val ordering: Ordering[A])
      extends mutable.BagConfiguration.VectorBagConfiguration[A](ordering)
      with mutable.SortedBagConfiguration[A]


    def ofMultiplicities[A](implicit ordering: Ordering[A]): mutable.SortedBagConfiguration[A] = new MultiplicityBagConfiguration(ordering)

    def ofBagBucketBag[A](implicit ordering: Ordering[A]): mutable.SortedBagConfiguration[A] = new BagBucketBagConfiguration(ordering)

    def ofVectors[A](implicit ordering: Ordering[A]): mutable.SortedBagConfiguration[A] = new VectorBagConfiguration(ordering)

  }

}


