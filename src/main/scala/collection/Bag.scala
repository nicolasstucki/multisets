package scala
package collection

import scala._


trait Bag[A] extends (A => Int)
with Iterable[A]
with BagLike[A, Bag[A]] {


  def apply(elem: A): Int = multiplicity(elem)


  // Iterators
  def iterator2: Iterator[Iterator[A]]

  def iterator: Iterator[A] = iterator2.flatten

  def countsIterator: Iterator[(A, Int)] = for (it <- iterator2 if !it.isEmpty) yield (it.next(), it.size + 1)

  def distinctIterator: Iterator[A] = for (it <- iterator2 if !it.isEmpty) yield it.next()

  def distinctSize: Int = iterator2.size

  // Counts and Multiplicities

  def maxMultiplicity: Int = maxCount(_ => true)

  def minMultiplicity: Int = minCount(_ => true)

  def maxCount(p: A => Boolean): Int = (0 :: (distinctIterator filter p map multiplicity).toList).max

  def minCount(p: A => Boolean): Int = (Int.MaxValue :: (distinctIterator filter p map multiplicity).toList).max

  // def mostCommon and leastCommon
  //  def mostCommon: Multiset[A] = mostCommon(_ => true)

  def mostCommon(p: A => Boolean = _ => true): Bag[A] = Bag((this filter p) filter (elem => (this multiplicity elem) == maxCount(p)))

  def leastCommon: Bag[A] = leastCommon(_ => true)

  def leastCommon(p: A => Boolean): Bag[A] = Bag((this filter p) filter (elem => (this multiplicity elem) == minCount(p)))


  // Multiset operations
  def union(that: Bag[A]): Bag[A] = Bag(this.toList ++ that.toList)

  def maxUnion(that: Bag[A]): Bag[A] = {
    Bag((for (elem <- (this union that).distinctIterator) yield {
      for (_ <- (1 to Math.max(this(elem), that(elem))).iterator) yield {
        elem
      }
    }).flatten.toIterable)
  }

  def intersect(that: Bag[A]): Bag[A] = Bag(this.toList intersect that.toList)

  def diff(that: Bag[A]): Bag[A] = Bag(this.toList diff that.toList)


  def subsetOf(that: Bag[A]): Boolean = distinctIterator forall (elem => (this multiplicity elem) <= (that multiplicity elem))

  def properSubsetOf(that: Bag[A]): Boolean = (this != that) && (this subsetOf that)


  def distinct: Bag[A] = Bag(distinctIterator.toList)


  // Reducing
  def fold[A1 >: A](z: A1)(op: (A1, A1) => A1, op2: (A1, Int) => A1) = (countsIterator map ((tup: (A, Int)) => op2(tup._1, tup._2))).fold(z)(op)

  def reduce[A1 >: A](op: (A1, A1) ⇒ A1, op2: (A1, Int) => A1): A1 = (countsIterator map ((tup: (A, Int)) => op2(tup._1, tup._2))).reduce(op)

  // Mapping


  // Filtering

  def zipWithMultiplicities: Iterable[(A, Int)] = countsIterator.toIterable

}


object Bag {

  def empty[A]: Bag[A] = immutable.Bag()

  def apply[T](): Bag[T] = immutable.Bag()

  def apply[T](elem: (T, Int)): Bag[T] = immutable.Bag(elem)

  def apply[T](elem1: (T, Int), elem2: (T, Int), elems: (T, Int)*): Bag[T] = immutable.Bag(elem1, elem2, elems: _*)

  def apply[T](elems: Iterable[T]): Bag[T] = immutable.Bag(elems)


}


