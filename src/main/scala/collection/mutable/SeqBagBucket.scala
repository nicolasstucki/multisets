package scala.collection.mutable

import scala.collection.mutable
import scala.collection.immutable


class SeqBagBucket[A](val sentinel: A, var sequence: immutable.Seq[A])
  extends scala.collection.SeqBagBucket[A]
  with mutable.BagBucket[A] {


  def +=(elem: A): this.type = {
    assert(elem == sentinel)
    sequence = sequence :+ elem
    this
  }

  def -=(elem: A): this.type = {
    assert(elem == sentinel)
    sequence = sequence.tail
    this
  }

  def +(elem: A): collection.BagBucket[A] = {
    assert(elem == sentinel)
    new mutable.SeqBagBucket(sentinel, sequence :+ elem)
  }

  def -(elem: A): collection.BagBucket[A] = {
    assert(elem == sentinel)
    if (isEmpty)
      mutable.SeqBagBucketFactory.of[A].empty(sentinel)
    else
      new mutable.SeqBagBucket(sentinel, sequence.tail)
  }
}


object SeqBagBucketFactory {
  def of[A] = new mutable.SeqBagBucketFactory[A]
}

class SeqBagBucketFactory[A] extends scala.collection.mutable.BagBucketFactory[A] {

  def empty(sentinel: A): mutable.SeqBagBucket[A] = new mutable.SeqBagBucket(sentinel, immutable.Seq.empty[A])

  override def apply(elem: A): mutable.SeqBagBucket[A] = new mutable.SeqBagBucket(elem, immutable.Seq(elem))

  override def apply(elem: A, multiplicity: Int): mutable.SeqBagBucket[A] = new mutable.SeqBagBucket(elem, immutable.Seq.fill(multiplicity)(elem))

}
