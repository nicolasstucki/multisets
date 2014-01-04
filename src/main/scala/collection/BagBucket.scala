package scala.collection

import scala.collection


trait BagBucket[A]
  extends Iterable[A] {

  protected type BagBucket <: collection.BagBucket[A]

  def sentinel: A

  def multiplicity(elem: A): Int

  def maxMultiplicity: Int = distinctIterator.map(elem => multiplicity(elem)).max

  def minMultiplicity: Int = distinctIterator.map(elem => multiplicity(elem)).min

  def intersect(that: collection.BagBucket[A]): BagBucket

  def diff(that: collection.BagBucket[A]): BagBucket

  def subsetOf(that: collection.BagBucket[A]): Boolean = {
    this.distinctIterator.forall(elem => this.multiplicity(elem) <= that.multiplicity(elem))
  }

  def +(elem: A): BagBucket = added(elem, 1)

  def added(elem: A, count: Int): BagBucket

  def addedBucket(bucket: collection.BagBucket[A]): BagBucket

  def -(elem: A): BagBucket = removed(elem, 1)

  def removed(elem: A, count: Int): BagBucket

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


trait BagBucketBag[A] extends BagBucket[A] {

  def bag: collection.Bag[A]

  def multiplicity(elem: A): Int = bag.multiplicity(elem)

  def distinctIterator: Iterator[A] = bag.distinctIterator

  def iterator: Iterator[A] = bag.iterator

  override def size: Int = bag.size

  override def nonEmpty: Boolean = bag.nonEmpty

  override def count(p: (A) => Boolean): Int = bag.count(p)

  override def min[B >: A](implicit cmp: Ordering[B]): A = bag.min(cmp)

  override def max[B >: A](implicit cmp: Ordering[B]): A = bag.max(cmp)

  override def maxBy[B](f: (A) => B)(implicit cmp: Ordering[B]): A = bag.maxBy(f)(cmp)

  override def minBy[B](f: (A) => B)(implicit cmp: Ordering[B]): A = bag.minBy(f)(cmp)

  override def sum[B >: A](implicit num: Numeric[B]): B = bag.sum(num)

  override def product[B >: A](implicit num: Numeric[B]): B = bag.product(num)

  override def foldLeft[B](z: B)(op: (B, A) => B): B = bag.foldLeft(z)(op)

  override def reduceLeft[B >: A](op: (B, A) => B): B = bag.reduceLeft(op)

  override def forall(p: (A) => Boolean): Boolean = bag.forall(p)

  override def exists(p: (A) => Boolean): Boolean = bag.exists(p)

  override def find(p: (A) => Boolean): Option[A] = bag.find(p)

  override def isEmpty: Boolean = bag.isEmpty

  override def foldRight[B](z: B)(op: (A, B) => B): B = bag.foldRight(z)(op)

  override def reduceRight[B >: A](op: (A, B) => B): B = bag.reduceRight(op)

  override def maxMultiplicity: Int = bag.maxMultiplicity

  override def minMultiplicity: Int = bag.minMultiplicity

}


trait VectorBagBucket[A] extends BagBucket[A] {

  def vector: Vector[A]

  def multiplicity(elem: A): Int = vector.count(_ == elem)

  override def sum[B >: A](implicit num: Numeric[B]): B = distinctIterator.map(elem => num.times(elem, num.fromInt(multiplicity(elem)))).sum

  def iterator: Iterator[A] = vector.iterator

  def distinctIterator: Iterator[A] = vector.distinct.iterator
}
