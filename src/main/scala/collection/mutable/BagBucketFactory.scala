package scala.collection.mutable

import scala.collection.mutable

trait BagBucketFactory[A]
  extends collection.BagBucketFactory[A, mutable.BagBucket[A]] {

  def newBuilder(sentinel: A) = new mutable.GrowingBagBucketBuilder[A](empty(sentinel))
}


object BagBucketFactory {

  def ofMultiplicities[A] = new MultiplicityBagBucketFactory[A]

  def ofVectors[A](implicit equivClass: Equiv[A]) = new VectorBagBucketFactory[A](equivClass)



  class MultiplicityBagBucketFactory[A] extends scala.collection.mutable.BagBucketFactory[A] {

    def empty(sentinel: A): mutable.MultiplicityBagBucket[A] = new mutable.MultiplicityBagBucket[A](sentinel, 0)

    def from(elem: A): mutable.MultiplicityBagBucket[A] = new mutable.MultiplicityBagBucket[A](elem, 1)

    def equiv(x: A, y: A): Boolean = x == y

  }


  class VectorBagBucketFactory[A](equivClass: Equiv[A]) extends scala.collection.mutable.BagBucketFactory[A] {

    def empty(sentinel: A): mutable.BagBucket[A] = new mutable.VectorBagBucket[A](sentinel, Vector.empty[A])

    def from(elem: A): mutable.BagBucket[A] = new mutable.VectorBagBucket[A](elem, Vector(elem))

    def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)
  }

}


