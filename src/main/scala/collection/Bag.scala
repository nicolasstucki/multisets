package scala
package collection


trait Bag[A, G <: BagBucket[A]] extends (A => G)
with Iterable[A]
with BagLike[A, G, Bag[A, G]] {

  implicit protected def m: BagBucketFactory[A, G]

  def apply(elem: A): G = getBucket(elem)

  def getBucket(elem: A): G = bucketsIterator.find(_.sentinel == elem) match {
    case Some(g) => g
    case None => m.empty(elem)
  }

  // Iterators

  def iterator: Iterator[A] = bucketsIterator.flatMap(_.iterator)

  protected def multiplicitiesIterator: Iterator[(A, Int)] = bucketsIterator.map(g => g.sentinel -> g.multiplicity)

  protected def distinctIterator: Iterator[A] = bucketsIterator.map(_.sentinel)

  protected def distinctSize: Int = bucketsIterator.size

  // Counts and Multiplicities

  def multiplicities: Map[A, Int] = new Multiplicities(this)

  def toMultiplicities: Map[A, Int] = Map.empty ++ bucketsIterator.map(g => (g.sentinel, g.multiplicity))


  def maxMultiplicity(p: A => Boolean = _ => true): Int = (0 :: (distinctIterator filter p map (this(_).multiplicity)).toList).max

  def minMultiplicity(p: A => Boolean = _ => true): Int = (Int.MaxValue :: (distinctIterator filter p map (this(_).multiplicity)).toList).max


  def mostCommon(p: A => Boolean = _ => true): Bag[A, G] = {
    val maxM = maxMultiplicity(p)
    val b = newBuilder
    for (bkt <- bucketsIterator if maxM == bkt.multiplicity && p(bkt.sentinel); elem <- bkt) {
      b += elem
    }
    b.result()
  }

  def leastCommon(p: A => Boolean = _ => true): Bag[A, G] = {
    val minM = minMultiplicity(p)
    var b = newBuilder
    for (bkt <- bucketsIterator if minM == bkt.multiplicity && p(bkt.sentinel); elem <- bkt) {
      b += elem
    }
    b.result()
  }


  // Multiset operations
  def union(that: Bag[A, G]): Bag[A, G] = this ++ that

  def maxUnion(that: Bag[A, G]): Bag[A, G] = {
    newBuilder.result ++ (for (elem <- (this union that).distinctIterator) yield {
      for (_ <- (1 to Math.max(this(elem).multiplicity, that(elem).multiplicity)).iterator) yield {
        elem
      }
    }).flatten.toIterable
  }

  def intersect(that: Bag[A, G]): Bag[A, G] = {
    val b = newBuilder
    for (bkt <- bucketsIterator if that(bkt.sentinel).multiplicity > 0) {
      if (that(bkt.sentinel).multiplicity > bkt.multiplicity) {
        for (elem <- bkt) b += elem
      } else {
        for (elem <- that(bkt.sentinel)) b += elem
      }
    }
    b.result()
  }


  def diff(that: Bag[A, G]): Bag[A, G] = ??? // Bag.from(this.toList diff that.toList)


  def subsetOf(that: Bag[A, G]): Boolean = distinctIterator forall (elem => this(elem).multiplicity <= that(elem).multiplicity)

  def properSubsetOf(that: Bag[A, G]): Boolean = (this != that) && (this subsetOf that)


  def distinct: Bag[A, G] = {
    val b = newBuilder
    for (bkt <- bucketsIterator) {
      b += bkt.sentinel
    }
    b.result()
  }


  // Reducing
  def fold[A1 >: A](z: A1)(op: (A1, A1) => A1, op2: (A1, Int) => A1) = (multiplicitiesIterator map ((tup: (A, Int)) => op2(tup._1, tup._2))).fold(z)(op)

  def reduce[A1 >: A](op: (A1, A1) => A1, op2: (A1, Int) => A1): A1 = (multiplicitiesIterator map ((tup: (A, Int)) => op2(tup._1, tup._2))).reduce(op)

  // Mapping


  // Filtering

  def zipWithMultiplicities: Iterable[(A, Int)] = multiplicitiesIterator.toIterable

  override def stringPrefix: String = "Bag"

}


object Bag {

  //  def empty[A, Bkt <: BagBucket[A]](implicit m: BagBucketFactory[A, Bkt]): Bag[A, Bkt] = immutable.Bag.empty
  //
  //  def apply[A, Bkt <: BagBucket[A]](elem: (A, Int))(implicit m: BagBucketFactory[A, Bkt]): Bag[A, Bkt] = immutable.Bag(elem)
  //
  //  def apply[A, Bkt <: BagBucket[A]](elem1: (A, Int), elem2: (A, Int), elems: (A, Int)*)(implicit m: BagBucketFactory[A, Bkt]): immutable.Bag[A, Bkt] = immutable.Bag(elem1, elem2, elems: _*)
  //
  //  def from[A, Bkt <: BagBucket[A]](elems: Iterable[A])(implicit m: BagBucketFactory[A, Bkt]): Bag[A, Bkt] = immutable.Bag.from(elems)

}


