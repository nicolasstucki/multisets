package scala.collection.immutable

import scala.collection.immutable

class SeqBagBucket[A](val sentinel: A, val sequence: Seq[A])
  extends scala.collection.SeqBagBucket[A]
  with BagBucket[A] {


  def +(elem: A): SeqBagBucket[A] = {
    assert(elem == sentinel)
    new SeqBagBucket(sentinel, sequence :+ elem)
  }

  def -(elem: A): SeqBagBucket[A] = {
    assert(elem == sentinel)
    new SeqBagBucket(sentinel, sequence.tail)
  }
}


object SeqBagBucketFactory {
  def of[A] = new SeqBagBucketFactory[A]
}

class SeqBagBucketFactory[A] extends immutable.BagBucketFactory[A] {

  def empty(sentinel: A): SeqBagBucket[A] = new SeqBagBucket(sentinel, Seq.empty[A])

  override def apply(elem: A, multiplicity: Int): SeqBagBucket[A] = new SeqBagBucket(elem, Seq.fill(multiplicity)(elem))

}
