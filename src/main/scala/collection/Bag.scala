package scala
package collection

import scala._


trait Bag[A, G <: BagBucket[A, G]] extends (A => Int)
with Iterable[A]
with BagLike[A, G, Bag[A, G]] {

  implicit protected def m: BagBuckets[A, G]

  def apply(elem: A): Int = multiplicity(elem)


  // Iterators

  def iterator: Iterator[A] = groupIterator.flatMap(_.iterator)

  def countsIterator: Iterator[(A, Int)] = groupIterator.map(g => g.sentinel -> g.multiplicity)

  def distinctIterator: Iterator[A] = groupIterator.map(_.sentinel)

  def distinctSize: Int = groupIterator.size

  // Counts and Multiplicities

  def multiplicity(elem: A): Int = groupIterator.find(_.sentinel == elem) match {
    case Some(group) => group.multiplicity
    case None => 0
  }

  def maxMultiplicity: Int = groupIterator.map(_.multiplicity).max

  def minMultiplicity: Int = groupIterator.map(_.multiplicity).min

  def maxCount(p: A => Boolean): Int = (0 :: (distinctIterator filter p map multiplicity).toList).max

  def minCount(p: A => Boolean): Int = (Int.MaxValue :: (distinctIterator filter p map multiplicity).toList).max

  // def mostCommon and leastCommon
  //  def mostCommon: Multiset[A] = mostCommon(_ => true)

  def mostCommon(p: A => Boolean = _ => true): Bag[A, G] = Bag[A, G]((this filter p) filter (elem => (this multiplicity elem) == maxCount(p)))

  def leastCommon: Bag[A, G] = leastCommon(_ => true)

  def leastCommon(p: A => Boolean): Bag[A, G] = Bag[A, G]((this filter p) filter (elem => (this multiplicity elem) == minCount(p)))


  // Multiset operations
  def union(that: Bag[A, G]): Bag[A, G] = Bag[A, G](this.toList ++ that.toList)

  def maxUnion(that: Bag[A, G]): Bag[A, G] = {
    Bag[A, G]((for (elem <- (this union that).distinctIterator) yield {
      for (_ <- (1 to Math.max(this(elem), that(elem))).iterator) yield {
        elem
      }
    }).flatten.toIterable)
  }

  def intersect(that: Bag[A, G]): Bag[A, G] = Bag[A, G](this.toList intersect that.toList)

  def diff(that: Bag[A, G]): Bag[A, G] = Bag[A, G](this.toList diff that.toList)


  def subsetOf(that: Bag[A, G]): Boolean = distinctIterator forall (elem => (this multiplicity elem) <= (that multiplicity elem))

  def properSubsetOf(that: Bag[A, G]): Boolean = (this != that) && (this subsetOf that)


  def distinct: Bag[A, G] = Bag[A, G](distinctIterator.toList)


  // Reducing
  def fold[A1 >: A](z: A1)(op: (A1, A1) => A1, op2: (A1, Int) => A1) = (countsIterator map ((tup: (A, Int)) => op2(tup._1, tup._2))).fold(z)(op)

  def reduce[A1 >: A](op: (A1, A1) ⇒ A1, op2: (A1, Int) => A1): A1 = (countsIterator map ((tup: (A, Int)) => op2(tup._1, tup._2))).reduce(op)

  // Mapping


  // Filtering

  def zipWithMultiplicities: Iterable[(A, Int)] = countsIterator.toIterable

}


object Bag {

  def empty[A, G <: BagBucket[A, G]](implicit m: BagBuckets[A, G]): Bag[A, G] = mutable.Bag(m)

  def apply[A, G <: BagBucket[A, G]](implicit m: BagBuckets[A, G]): Bag[A, G] = empty(m)

  def apply[A, G <: BagBucket[A, G]](elem: (A, Int))(implicit m: BagBuckets[A, G]): Bag[A, G] = mutable.Bag(elem)(m)

  def apply[A, G <: BagBucket[A, G]](elem1: (A, Int), elem2: (A, Int), elems: (A, Int)*)(implicit m: BagBuckets[A, G]): Bag[A, G] = mutable.Bag(elem1, elem2, elems: _*)(m)

  def apply[A, G <: BagBucket[A, G]](elems: Iterable[A])(implicit m: BagBuckets[A, G]): Bag[A, G] = mutable.Bag(elems)(m)


}


