package scala.collection

import scala.language.higherKinds


object BagBucket {

  def unapply[A](bkt: BagBucket[A]): Option[(A, Int)] = Some((bkt.sentinel, bkt.multiplicity))

}


trait BagBucket[A]
  extends Iterable[A] {

  assert(multiplicity >= 0)

  protected type BagBucket[X] <: collection.BagBucket[X]

  def sentinel: A

  def multiplicity: Int

  def +(elem: A): BagBucket[A]

  def added(elem: A, count: Int): BagBucket[A]

  def addedBucket(bucket: collection.BagBucket[A]): BagBucket[A]

  def -(elem: A): BagBucket[A]

  protected def sentinelCheck(other: A) {
    if (sentinel != other)
      throw new BagBucketException("Bag Bucket sentinel miss match.")
  }


}


trait MultiplicityBagBucket[A] extends BagBucket[A] {

  override def isEmpty: Boolean = multiplicity == 0

  override def iterator: Iterator[A] = Iterator.fill(multiplicity)(sentinel)

  override def sum[B >: A](implicit num: Numeric[B]): B = num.times(sentinel, num.fromInt(multiplicity))

  override def min[B >: A](implicit cmp: Ordering[B]): A = {
    if (isEmpty)
      throw new UnsupportedOperationException("empty.minBy")

    sentinel
  }

  override def max[B >: A](implicit cmp: Ordering[B]): A = {
    if (isEmpty)
      throw new UnsupportedOperationException("empty.minBy")

    sentinel
  }

  override def minBy[B](f: (A) => B)(implicit cmp: Ordering[B]): A = {
    if (isEmpty)
      throw new UnsupportedOperationException("empty.minBy")

    sentinel
  }

  override def maxBy[B](f: (A) => B)(implicit cmp: Ordering[B]): A = {
    if (isEmpty)
      throw new UnsupportedOperationException("empty.minBy")

    sentinel
  }

  override def count(p: (A) => Boolean): Int = if (p(sentinel)) multiplicity else 0

  override def size: Int = multiplicity

  override def find(p: (A) => Boolean): Option[A] = if (p(sentinel)) Some(sentinel) else None

}


trait SeqBagBucket[A] extends BagBucket[A] {

  assert(sequence.hasDefiniteSize)

  def sequence: Seq[A]

  def multiplicity: Int = sequence.size

  def iterator: Iterator[A] = sequence.iterator
}


class BagBucketException(msg: String) extends Exception(msg)
