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
    def empty(sentinel: A): MultiplicityBagBucket[A] = new MultiplicityBagBucket(sentinel, 0)
  }

  class SeqBagBucketFactory[A] extends immutable.BagBucketFactory[A] {
    def empty(sentinel: A): immutable.SeqBagBucket[A] = new immutable.SeqBagBucket(sentinel, Seq.empty[A])
  }

}
