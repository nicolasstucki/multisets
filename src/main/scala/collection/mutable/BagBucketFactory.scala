package scala.collection.mutable

import scala.collection.{immutable, mutable}
import scala.util.hashing.Hashing
import scala.collection.immutable.Vector

trait BagBucketFactory[A]
  extends collection.BagBucketFactory[A, mutable.BagBucket[A]] {

  def newBuilder(sentinel: A) = new mutable.GrowingBagBucketBuilder[A](empty(sentinel))
}

trait HashedBagBucketFactory[A]
  extends mutable.BagBucketFactory[A]
  with collection.HashedBagBucketFactory[A, mutable.BagBucket[A]]


trait SortedBagBucketFactory[A]
  extends mutable.BagBucketFactory[A]
  with collection.SortedBagBucketFactory[A, mutable.BagBucket[A]]


object BagBucketFactory {

  private class MultiplicityBagBucketFactory[A] extends mutable.BagBucketFactory[A] {
    def empty(sentinel: A): mutable.MultiplicityBagBucket[A] = new mutable.MultiplicityBagBucket(sentinel, 0)

    def equiv(x: A, y: A): Boolean = x == y
  }

  private abstract class BagBucketBagFactory[A](val equivClass: Equiv[A]) extends mutable.BagBucketFactory[A] {
    def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)
  }

  private class VectorBagBucketFactory[A](val equivClass: Equiv[A]) extends mutable.BagBucketFactory[A] {
    def empty(sentinel: A): mutable.VectorBagBucket[A] = new mutable.VectorBagBucket(sentinel, immutable.Vector.empty[A])

    def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)
  }

  object Hashed {

    private class MultiplicityBagBucketFactory[A]
      extends mutable.BagBucketFactory.MultiplicityBagBucketFactory[A]
      with mutable.HashedBagBucketFactory[A] {
      def hash(x: A): Int = x.hashCode()
    }

    private class BagBucketBagFactory[A](equivClass: Equiv[A], hashing: Hashing[A])
      extends mutable.BagBucketFactory.BagBucketBagFactory[A](equivClass)
      with mutable.HashedBagBucketFactory[A] {
      def empty(sentinel: A): mutable.BagBucketBag[A] = new mutable.BagBucketBag(sentinel, mutable.HashBag.empty[A](ofMultiplicities[A]))

      def hash(x: A): Int = hashing.hash(x)
    }

    private class VectorBagBucketFactory[A](equivClass: Equiv[A], hashing: Hashing[A])
      extends mutable.BagBucketFactory.VectorBagBucketFactory[A](equivClass)
      with mutable.HashedBagBucketFactory[A] {
      def hash(x: A): Int = hashing.hash(x)
    }


    def ofMultiplicities[A]: mutable.HashedBagBucketFactory[A] = new mutable.BagBucketFactory.Hashed.MultiplicityBagBucketFactory

    def ofBagBucketBag[A](implicit equivClass: Equiv[A], hashing: Hashing[A]): mutable.HashedBagBucketFactory[A] = new mutable.BagBucketFactory.Hashed.BagBucketBagFactory(equivClass, hashing)

    def ofVectors[A](implicit equivClass: Equiv[A], hashing: Hashing[A]): mutable.HashedBagBucketFactory[A] = new mutable.BagBucketFactory.Hashed.VectorBagBucketFactory(equivClass, hashing)

  }

  object Sorted {

    private class MultiplicityBagBucketFactory[A](val ordering: Ordering[A])
      extends mutable.BagBucketFactory.MultiplicityBagBucketFactory[A]
      with mutable.SortedBagBucketFactory[A]

    private class BagBucketBagFactory[A](val ordering: Ordering[A])
      extends mutable.BagBucketFactory.BagBucketBagFactory[A](ordering)
      with mutable.SortedBagBucketFactory[A] {
      def empty(sentinel: A): mutable.BagBucketBag[A] = ??? // new mutable.BagBucketBag(sentinel, mutable.TreeBag.empty[A](ofMultiplicities[A](ordering)))
    }


    private class VectorBagBucketFactory[A](val ordering: Ordering[A])
      extends mutable.BagBucketFactory.VectorBagBucketFactory[A](ordering)
      with mutable.SortedBagBucketFactory[A]


    def ofMultiplicities[A](implicit ordering: Ordering[A]): mutable.SortedBagBucketFactory[A] = new MultiplicityBagBucketFactory(ordering)

    def ofBagBucketBag[A](implicit ordering: Ordering[A]): mutable.SortedBagBucketFactory[A] = new BagBucketBagFactory(ordering)

    def ofVectors[A](implicit ordering: Ordering[A]): mutable.SortedBagBucketFactory[A] = new VectorBagBucketFactory(ordering)

  }

}


