package scala.collection.immutable

import scala.collection.immutable


trait SortedBagBucketFactory[A]
  extends immutable.BagBucketFactory[A]
  with collection.SortedBagBucketFactory[A, immutable.BagBucket[A]]
  with Ordering[A]

object SortedBagBucketFactory {

  def ofMultiplicities[A](implicit ordering: Ordering[A]): immutable.SortedBagBucketFactory[A] = new immutable.SortedBagBucketFactory[A] {
    def empty(sentinel: A): BagBucket[A] = new immutable.MultiplicityBagBucket(sentinel, 0)

    def compare(x: A, y: A): Int = ordering.compare(x, y)
  }

  def ofVectors[A](implicit ordering: Ordering[A]): immutable.SortedBagBucketFactory[A] = new immutable.SortedBagBucketFactory[A] {
    def empty(sentinel: A): BagBucket[A] = new immutable.VectorBagBucket(sentinel, immutable.Vector.empty[A])

    def compare(x: A, y: A): Int = ordering.compare(x, y)
  }

}