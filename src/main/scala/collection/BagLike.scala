package scala.collection


import scala.collection.generic.{CanBuildFrom, Subtractable}
import scala.collection.mutable.{BagBuilder, Builder}
import scala.annotation.tailrec

trait BagLike[A, +This <: BagLike[A, This] with Bag[A]]
  extends IterableLike[A, This]
  with GenBagLike[A, This]
  with (A => This)
  with Subtractable[A, This] {
  self =>

  def empty: This

  override protected[this] def newBuilder: mutable.BagBuilder[A, This] = mutable.BagBuilder(empty)


  def apply(elem: A): This = getBucket(elem) match {
    case Some(bucket) => (newBuilder addBucket bucket).result()
    case None => empty
  }


  def contains(elem: A): Boolean = repr.multiplicity(elem) > 0

  def mostCommon: Bag[A] = {
    if (isEmpty) return empty

    val maxM = maxMultiplicity
    val b = newBuilder
    for (bucket <- bucketsIterator if maxM == bucket.size) {
      b addBucket bucket
    }
    b.result()
  }

  override def leastCommon: Bag[A] = {
    if (isEmpty) return empty

    val minM = minMultiplicity
    val b = newBuilder
    for (bucket <- bucketsIterator if minM == bucket.size) {
      b addBucket bucket
    }
    b.result()
  }

  def distinct: Bag[A] = {
    val b = newBuilder
    for (bucket <- bucketsIterator) {
      b += bucket.sentinel
    }
    b.result()
  }

  // Added elements
  def +(elem: A): This = this added(elem, 1)


  def added(elem: A, count: Int): This = {
    val bb = bucketFactory.newBuilder(elem)
    bb.add(elem, count)
    this.addedBucket(bb.result())
  }

  def added(elemCount: (A, Int)): This = added(elemCount._1, elemCount._2)

  def ++(elems: GenTraversableOnce[A]): This = {
    val b = newBuilder
    for (bucket <- this.bucketsIterator) {
      b addBucket bucket
    }
    for (elem <- elems) {
      b += elem
    }
    b.result()
  }


  // Added Bucket


  def addedBucket(bucket: collection.BagBucket[A]): This

  // Multiset operations
  override def union(that: GenBag[A]): This = this ++ that

  def diff(that: GenBag[A]): This = {
    val b = newBuilder
    for (bucket <- bucketsIterator) {
      that.getBucket(bucket.sentinel) match {
        case Some(bucket2) =>
          val diffBucket = bucket diff bucket2
          if (diffBucket.nonEmpty)
            b addBucket diffBucket
        case None =>
          b addBucket bucket
      }
    }
    b.result()
  }

  override def maxUnion(that: GenBag[A]): This = {
    newBuilder.result ++ (for (elem <- (this union that).distinctIterator) yield {
      for (_ <- (1 to Math.max(this.multiplicity(elem), that.multiplicity(elem))).iterator) yield {
        elem
      }
    }).flatten.toIterable
  }

  override def intersect(that: GenBag[A]): This = {
    val b = newBuilder
    for (bucket <- bucketsIterator) {
      that.getBucket(bucket.sentinel) match {
        case Some(bucket2) =>
          val intersectionBucket = bucket intersect bucket2
          if (intersectionBucket.nonEmpty)
            b addBucket intersectionBucket
        case None =>
      }
    }
    b.result()
  }

  // Removed elements
  def -(elem: A): This = removed(elem, 1)

  def -(elemCount: (A, Int)): This = removed(elemCount._1, elemCount._2)

  def removed(elem: A, count: Int): This = {
    val b = newBuilder

    for (bucket <- bucketsIterator) {
      if (bucketFactory.equiv(bucket.sentinel, elem)) {
        val bucket2 = bucket.removed(elem, count)
        if (bucket2.nonEmpty)
          b addBucket bucket2

      } else {
        b addBucket bucket
      }
    }

    b.result()
  }

  def -*(elem: A): This = removedBucket(elem)

  def removedBucket(elem: A): This = {
    val b = newBuilder
    for (bucket <- bucketsIterator if !bucketFactory.equiv(bucket.sentinel, elem)) {
      b addBucket bucket
    }
    b.result()
  }

  def multiplicities: Map[A, Int] = new Multiplicities(repr)

  def setMultiplicity(elem: A, count: Int): This = (this -* elem).added(elem, count)

  override def forall(p: (A) => Boolean): Boolean = bucketsIterator.forall(_.forall(p))

  override def exists(p: (A) => Boolean): Boolean = bucketsIterator.exists(_.exists(p))


  override def find(p: (A) => Boolean): Option[A] = {
    val it = bucketsIterator
    while (it.hasNext) {
      it.next().find(p) match {
        case value@Some(_) => return value
        case None =>
      }
    }
    None
  }


  override def foldLeft[B](z: B)(op: (B, A) => B): B = {
    var result = z
    this.bucketsIterator foreach (bucket => result = bucket.foldLeft[B](result)(op))
    result
  }


  override def map[B, That](f: (A) => B)(implicit bf: CanBuildFrom[This, B, That]): That = super.map(f)(bf)


  override def sum[B >: A](implicit num: Numeric[B]): B = bucketsIterator.map(_.sum(num)).foldLeft(num.zero)(num.plus)

  override def product[B >: A](implicit num: Numeric[B]): B = bucketsIterator.map(_.product(num)).foldLeft(num.one)(num.times)

  override def min[B >: A](implicit cmp: Ordering[B]): A = bucketsIterator.map(_.min(cmp)).min(cmp)

  override def max[B >: A](implicit cmp: Ordering[B]): A = bucketsIterator.map(_.max(cmp)).max(cmp)


  override def reduceLeft[B >: A](op: (B, A) => B): B = {
    if (isEmpty)
      throw new UnsupportedOperationException("empty.reduceLeft")

    var first = true
    var acc: B = 0.asInstanceOf[B]

    for (bucket <- bucketsIterator if bucket.nonEmpty) {
      if (first) {
        acc = bucket.reduceLeft(op)
        first = false
      }
      else acc = bucket.foldLeft(acc)(op)
    }
    acc
  }

  override def count(p: (A) => Boolean): Int = {
    var cnt = 0
    for (bucket <- bucketsIterator)
      cnt += bucket.count(p)
    cnt
  }
}
