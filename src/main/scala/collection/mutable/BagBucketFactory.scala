package scala.collection.mutable

import scala.collection.mutable

trait BagBucketFactory[A]
  extends collection.BagBucketFactory[A, mutable.BagBucket[A]] {

  def newBuilder(sentinel: A) = new mutable.GrowingBagBucketBuilder[A](empty(sentinel))
}


object BagBucketFactory {

  def ofMultiplicities[A] = new mutable.BagBucketFactory[A] {

    def empty(sentinel: A): mutable.MultiplicityBagBucket[A] = new mutable.MultiplicityBagBucket[A](sentinel, 0)

    def equiv(x: A, y: A): Boolean = x == y
  }

  def ofVectors[A](implicit equivClass: Equiv[A]) = new mutable.BagBucketFactory[A] {

    def empty(sentinel: A): mutable.BagBucket[A] = new mutable.VectorBagBucket[A](sentinel, Vector.empty[A])

    def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)
  }

}


