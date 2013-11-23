package scala.collection

import scala.language.higherKinds
import scala.collection


trait BagBucket[A]
  extends Iterable[A] {

  protected type BagBucket[X] <: collection.BagBucket[X]

  def sentinel: A

  def multiplicity(elem: A): Int

  def maxMultiplicity: Int = distinctIterator.map(elem => multiplicity(elem)).max

  def minMultiplicity: Int = distinctIterator.map(elem => multiplicity(elem)).min

  def intersect(that: collection.BagBucket[A]): BagBucket[A]

  def diff(that: collection.BagBucket[A]): BagBucket[A]

  def subsetOf(that: collection.BagBucket[A]): Boolean = {
    this.distinctIterator.forall(elem => this.multiplicity(elem) <= that.multiplicity(elem))
  }

  def +(elem: A): BagBucket[A] = added(elem, 1)

  def added(elem: A, count: Int): BagBucket[A]

  def addedBucket(bucket: collection.BagBucket[A]): BagBucket[A]

  def -(elem: A): BagBucket[A]

  def removed(elem: A, count: Int): BagBucket[A]

  def distinctIterator: Iterator[A]
}


trait MultiplicityBagBucket[A] extends BagBucket[A] {

  def multiplicity: Int

  def multiplicity(elem: A): Int = if (elem == sentinel) multiplicity else 0



  override def maxMultiplicity: Int = multiplicity

  override def minMultiplicity: Int = multiplicity

  override def subsetOf(that: collection.BagBucket[A]): Boolean = multiplicity <= that.multiplicity(sentinel)


  override def exists(p: (A) => Boolean): Boolean = p(sentinel)

  override def forall(p: (A) => Boolean): Boolean = p(sentinel)

  override def isEmpty: Boolean = multiplicity == 0

  override def iterator: Iterator[A] = Iterator.fill(multiplicity)(sentinel)

  override def sum[B >: A](implicit num: Numeric[B]): B = num.times(sentinel, num.fromInt(multiplicity))

  override def min[B >: A](implicit cmp: Ordering[B]): A = {
    if (isEmpty)
      throw new UnsupportedOperationException("emptyBag.minBy")

    sentinel
  }

  override def max[B >: A](implicit cmp: Ordering[B]): A = {
    if (isEmpty)
      throw new UnsupportedOperationException("emptyBag.minBy")

    sentinel
  }

  override def minBy[B](f: (A) => B)(implicit cmp: Ordering[B]): A = {
    if (isEmpty)
      throw new UnsupportedOperationException("emptyBag.minBy")

    sentinel
  }

  override def maxBy[B](f: (A) => B)(implicit cmp: Ordering[B]): A = {
    if (isEmpty)
      throw new UnsupportedOperationException("emptyBag.minBy")

    sentinel
  }

  override def count(p: (A) => Boolean): Int = if (p(sentinel)) multiplicity else 0

  override def size: Int = multiplicity

  override def find(p: (A) => Boolean): Option[A] = if (p(sentinel)) Some(sentinel) else None

  def distinctIterator: Iterator[A] = Iterator(sentinel)
}


trait VectorBagBucket[A] extends BagBucket[A] {

  def vector: Vector[A]

  def multiplicity(elem: A): Int = vector.count(_ == elem)

  override def sum[B >: A](implicit num: Numeric[B]): B = distinctIterator.map(elem => num.times(elem, num.fromInt(multiplicity(elem)))).sum

  def iterator: Iterator[A] = vector.iterator

  def distinctIterator: Iterator[A] = vector.distinct.iterator
}
