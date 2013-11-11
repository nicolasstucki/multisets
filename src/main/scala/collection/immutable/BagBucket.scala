package scala.collection.immutable

import scala.collection.immutable
import scala.collection
import scala.language.higherKinds

trait BagBucket[A] extends scala.collection.BagBucket[A] {

  protected override type BagBucket[X] = immutable.BagBucket[X]


}


class MultiplicityBagBucket[A](val sentinel: A, val multiplicity: Int)
  extends scala.collection.MultiplicityBagBucket[A]
  with immutable.BagBucket[A] {

  def +(elem: A): MultiplicityBagBucket[A] = {
    new immutable.MultiplicityBagBucket(sentinel, multiplicity + 1)
  }


  def added(elem: A, count: Int) = {
    if (count > 0)
      new immutable.MultiplicityBagBucket(sentinel, multiplicity + count)
    else
      this
  }

  def addedBucket(bucket: collection.BagBucket[A]): immutable.BagBucket[A] = {
    new immutable.MultiplicityBagBucket[A](sentinel, this.multiplicity + bucket.multiplicity)
  }

  def -(elem: A): MultiplicityBagBucket[A] = {
    new MultiplicityBagBucket(sentinel, Math.max(0, multiplicity - 1))
  }


}


class SeqBagBucket[A](val sentinel: A, val sequence: Seq[A])
  extends scala.collection.SeqBagBucket[A]
  with immutable.BagBucket[A] {


  def +(elem: A): SeqBagBucket[A] = {
    new immutable.SeqBagBucket(sentinel, sequence :+ elem)
  }


  def added(elem: A, count: Int) = {
    if (count > 0)
      new immutable.SeqBagBucket[A](elem, sequence ++ Iterator.fill(count)(elem))
    else
      this
  }

  def addedBucket(bucket: collection.BagBucket[A]): immutable.BagBucket[A] = {
    new immutable.SeqBagBucket[A](sentinel, this.sequence ++ bucket)
  }

  def -(elem: A): SeqBagBucket[A] = {
    if (sequence.isEmpty)
      new SeqBagBucket(sentinel, Seq.empty[A])
    else
      new SeqBagBucket(sentinel, sequence.tail)
  }
}



