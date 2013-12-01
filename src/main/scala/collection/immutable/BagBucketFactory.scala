package scala.collection.immutable

import scala.collection._
import scala.collection
import scala.util.hashing.Hashing

trait BagBucketFactory[A]
  extends collection.BagBucketFactory[A, immutable.BagBucket[A]] {

  override def from(bucket: collection.BagBucket[A]): BagBucket[A] = bucket match {
    case immutableBucket: immutable.BagBucket[A] => immutableBucket
    case _ => super.from(bucket)
  }

  def newBuilder(sentinel: A) = mutable.BagBucketBuilder(empty(sentinel))

}

trait HashedBagBucketFactory[A]
  extends immutable.BagBucketFactory[A]
  with collection.HashedBagBucketFactory[A, immutable.BagBucket[A]]

trait SortedBagBucketFactory[A]
  extends immutable.BagBucketFactory[A]
  with collection.SortedBagBucketFactory[A, immutable.BagBucket[A]]


object BagBucketFactory {

  private class MultiplicityBagBucketFactory[A]
    extends immutable.BagBucketFactory[A] {
    def empty(sentinel: A): immutable.MultiplicityBagBucket[A] = new immutable.MultiplicityBagBucket(sentinel, 0)

    def equiv(x: A, y: A): Boolean = x == y
  }

  private abstract class BagBucketBagFactory[A](val equivClass: Equiv[A]) extends immutable.BagBucketFactory[A] {
    def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)
  }

  private class VectorBagBucketFactory[A](val equivClass: Equiv[A]) extends immutable.BagBucketFactory[A] {
    def empty(sentinel: A): immutable.VectorBagBucket[A] = new immutable.VectorBagBucket(sentinel, immutable.Vector.empty[A])

    def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)
  }


  object Hashed {

    private class MultiplicityBagBucketFactory[A]
      extends immutable.BagBucketFactory.MultiplicityBagBucketFactory[A]
      with immutable.HashedBagBucketFactory[A] {
      def hash(x: A): Int = x.hashCode()
    }

    private class BagBucketBagFactory[A](equivClass: Equiv[A], hashing: Hashing[A])
      extends BagBucketFactory.BagBucketBagFactory[A](equivClass)
      with immutable.HashedBagBucketFactory[A] {

      def empty(sentinel: A): immutable.BagBucketBag[A] = ??? // new immutable.BagBucketBag(sentinel, mutable.HashBag.empty[A](ofMultiplicities[A]))

      def hash(x: A): Int = hashing.hash(x)
    }

    private class VectorBagBucketFactory[A](equivClass: Equiv[A], hashing: Hashing[A])
      extends BagBucketFactory.VectorBagBucketFactory[A](equivClass)
      with immutable.HashedBagBucketFactory[A] {
      def hash(x: A): Int = hashing.hash(x)
    }


    def ofMultiplicities[A]: immutable.HashedBagBucketFactory[A] = new BagBucketFactory.Hashed.MultiplicityBagBucketFactory

    def ofBagBucketBag[A](implicit equivClass: Equiv[A], hashing: Hashing[A]): immutable.HashedBagBucketFactory[A] = new BagBucketFactory.Hashed.BagBucketBagFactory(equivClass, hashing)

    def ofVectors[A](implicit equivClass: Equiv[A], hashing: Hashing[A]): immutable.HashedBagBucketFactory[A] = new BagBucketFactory.Hashed.VectorBagBucketFactory(equivClass, hashing)
  }


  object Sorted {

    private class MultiplicityBagBucketFactory[A](val ordering: Ordering[A])
      extends BagBucketFactory.MultiplicityBagBucketFactory[A]
      with SortedBagBucketFactory[A]

    private class BagBucketBagFactory[A](val ordering: Ordering[A])
      extends BagBucketFactory.BagBucketBagFactory[A](ordering)
      with SortedBagBucketFactory[A] {
      def empty(sentinel: A): immutable.BagBucketBag[A] = new immutable.BagBucketBag(sentinel, immutable.TreeBag.empty[A](ofMultiplicities[A](ordering)))
    }


    private class VectorBagBucketFactory[A](val ordering: Ordering[A])
      extends BagBucketFactory.VectorBagBucketFactory[A](ordering)
      with SortedBagBucketFactory[A]


    def ofMultiplicities[A](implicit ordering: Ordering[A]): immutable.SortedBagBucketFactory[A] = new MultiplicityBagBucketFactory(ordering)

    def ofBagBucketBag[A](implicit ordering: Ordering[A]): immutable.SortedBagBucketFactory[A] = new BagBucketBagFactory(ordering)

    def ofVectors[A](implicit ordering: Ordering[A]): immutable.SortedBagBucketFactory[A] = new VectorBagBucketFactory(ordering)

  }

}






