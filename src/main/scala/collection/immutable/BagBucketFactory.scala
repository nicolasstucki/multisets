package scala.collection.immutable

import scala.collection._

trait BagBucketFactory[A]
  extends scala.collection.BagBucketFactory[A, immutable.BagBucket[A]] {

  def newBuilder(sentinel: A) = new mutable.BagBucketBuilderImpl(empty(sentinel))

}

object BagBucketFactory {

  def ofMultiplicities[A] = new MultiplicityBagBucketFactory[A]

  def ofSeq[A] = new SeqBagBucketFactory[A]

  class MultiplicityBagBucketFactory[A] extends immutable.BagBucketFactory[A] {
    def empty(sentinel: A): immutable.MultiplicityBagBucket[A] = new immutable.MultiplicityBagBucket(sentinel, 0)

    def from(elem: A): immutable.MultiplicityBagBucket[A] = new MultiplicityBagBucket(elem, 1)
  }

  class SeqBagBucketFactory[A] extends immutable.BagBucketFactory[A] {
    def empty(sentinel: A): immutable.SeqBagBucket[A] = new immutable.SeqBagBucket(sentinel, immutable.Seq.empty[A])

    def from(elem: A): immutable.SeqBagBucket[A] = new immutable.SeqBagBucket(elem, immutable.Seq(elem))
  }

}
