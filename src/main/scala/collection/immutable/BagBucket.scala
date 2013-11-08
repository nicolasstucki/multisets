package scala.collection.immutable

import scala.collection.{BagBucketException, immutable}
import scala.collection
import scala.language.higherKinds

trait BagBucket[A] extends scala.collection.BagBucket[A] {

  protected override type BagBucket[X] = immutable.BagBucket[X]


}


class MultiplicityBagBucket[A](val sentinel: A, val multiplicity: Int)
  extends scala.collection.MultiplicityBagBucket[A]
  with immutable.BagBucket[A] {

  def +(elem: A): MultiplicityBagBucket[A] = {
    sentinelCheck(elem)
    new immutable.MultiplicityBagBucket(sentinel, multiplicity + 1)
  }


  def added(elem: A, count: Int) = {
    sentinelCheck(elem)

    if (count > 0)
      new immutable.MultiplicityBagBucket(sentinel, multiplicity + count)
    else
      this
  }

  def addedBucket(bucket: collection.BagBucket[A]): immutable.BagBucket[A] = {
    sentinelCheck(bucket.sentinel)
    new immutable.MultiplicityBagBucket[A](sentinel, this.multiplicity + bucket.multiplicity)
  }

  def -(elem: A): MultiplicityBagBucket[A] = {
    sentinelCheck(elem)
    new MultiplicityBagBucket(sentinel, Math.max(0, multiplicity - 1))
  }


}


class SeqBagBucket[A](val sentinel: A, val sequence: Seq[A])
  extends scala.collection.SeqBagBucket[A]
  with immutable.BagBucket[A] {


  def +(elem: A): SeqBagBucket[A] = {
    sentinelCheck(elem)
    new immutable.SeqBagBucket(sentinel, sequence :+ elem)
  }


  def added(elem: A, count: Int) = {
    sentinelCheck(elem)
    if (count > 0)
      new immutable.SeqBagBucket[A](elem, sequence ++ Iterator.fill(count)(elem))
    else
      this
  }

  def addedBucket(bucket: collection.BagBucket[A]): immutable.BagBucket[A] = {
    sentinelCheck(bucket.sentinel)
    new immutable.SeqBagBucket[A](sentinel, this.sequence ++ bucket)
  }

  def -(elem: A): SeqBagBucket[A] = {
    sentinelCheck(elem)
    if (sequence.isEmpty)
      new SeqBagBucket(sentinel, Seq.empty[A])
    else
      new SeqBagBucket(sentinel, sequence.tail)
  }
}



