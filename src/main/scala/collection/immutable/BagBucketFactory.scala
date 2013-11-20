package scala.collection.immutable

import scala.collection._
import scala.collection

trait BagBucketFactory[A]
  extends collection.BagBucketFactory[A, immutable.BagBucket[A]] {


  override def from(bucket: collection.BagBucket[A]): BagBucket[A] = bucket match {
    case immutableBucket: immutable.BagBucket[A] => immutableBucket
    case _ => super.from(bucket)
  }

  def newBuilder(sentinel: A) = mutable.BagBucketBuilder(empty(sentinel))

}


object BagBucketFactory {

  def ofMultiplicities[A]: immutable.BagBucketFactory[A] = new immutable.BagBucketFactory[A] {
    def empty(sentinel: A): immutable.MultiplicityBagBucket[A] = new immutable.MultiplicityBagBucket(sentinel, 0)

    def equiv(x: A, y: A): Boolean = x == y
  }

  def ofVectors[A](implicit equivClass: Equiv[A]): immutable.BagBucketFactory[A] = new immutable.BagBucketFactory[A] {
    def empty(sentinel: A): immutable.VectorBagBucket[A] = new immutable.VectorBagBucket(sentinel, immutable.Vector.empty[A])

    def equiv(x: A, y: A): Boolean = equivClass.equiv(x, y)
  }

}
