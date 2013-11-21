package scala.collection.mutable

import scala.collection.mutable
import scala.collection.immutable.Vector


trait SortedBagBucketFactory[A]
  extends mutable.BagBucketFactory[A]
  with collection.SortedBagBucketFactory[A, mutable.BagBucket[A]]
  with Ordering[A]

object SortedBagBucketFactory {

  def ofMultiplicities[A](implicit ordering: Ordering[A]): mutable.SortedBagBucketFactory[A] = new mutable.SortedBagBucketFactory[A] {
    def empty(sentinel: A): mutable.BagBucket[A] = new mutable.MultiplicityBagBucket(sentinel, 0)

    def compare(x: A, y: A): Int = ordering.compare(x, y)
  }

  def ofVectors[A](implicit ordering: Ordering[A]): mutable.SortedBagBucketFactory[A] = new mutable.SortedBagBucketFactory[A] {
    def empty(sentinel: A): mutable.BagBucket[A] = new mutable.VectorBagBucket(sentinel, Vector.empty[A])

    def compare(x: A, y: A): Int = ordering.compare(x, y)
  }

}