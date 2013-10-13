package scala.collection


import scala.collection.generic.Subtractable

trait BagLike[A, +This <: BagLike[A, This] with Bag[A]]
  extends IterableLike[A, This]
  with GenBagLike[A, This]
  with Subtractable[A, This] {
  self =>


  def empty: This

  override protected[this] def newBuilder: mutable.Builder[A, This] = new mutable.BagBuilder[A, This](empty)


  def contains(elem: A): Boolean = repr(elem).multiplicity > 0

  override def mostCommon(p: A => Boolean = _ => true): Bag[A] = {
    val maxM = maxMultiplicity(p)
    val b = newBuilder
    for (bkt <- bucketsIterator if maxM == bkt.multiplicity && p(bkt.sentinel); elem <- bkt) {
      b += elem
    }
    b.result()
  }

  override def leastCommon(p: A => Boolean = _ => true): Bag[A] = {
    val minM = minMultiplicity(p)
    var b = newBuilder
    for (bkt <- bucketsIterator if minM == bkt.multiplicity && p(bkt.sentinel); elem <- bkt) {
      b += elem
    }
    b.result()
  }

  def distinct: Bag[A] = {
    val b = newBuilder
    for (bkt <- bucketsIterator) {
      b += bkt.sentinel
    }
    b.result()
  }

  // Added elements
  def +(elem: A): This

  def +(elemMultiplicity: (A, Int)): This = this ++ Iterator.fill(elemMultiplicity._2)(elemMultiplicity._1)

  def ++(elems: GenTraversableOnce[A]): This = {
    val b = newBuilder
    for (bkt <- this.bucketsIterator; elem <- bkt) {
      b += elem
    }
    for (elem <- elems) {
      b += elem
    }
    b.result()
  }


  override def union(that: GenBag[A]): This = this ++ that

  def diff(that: GenBag[A]): This = this -- that

  override def maxUnion(that: GenBag[A]): This = {
    newBuilder.result ++ (for (elem <- (this union that).distinctIterator) yield {
      for (_ <- (1 to Math.max(this(elem).multiplicity, that(elem).multiplicity)).iterator) yield {
        elem
      }
    }).flatten.toIterable
  }

  override def intersect(that: GenBag[A]): This = {
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


  // Removed elements
  def -(elem: A): This

  def -(elemCount: (A, Int)): This = elemCount match {
    case (elem, count) if count > 0 => (this - elem) - (elem -> (count - 1))
    case _ => repr
  }

  def -*(elem: A): This = this - (elem -> repr(elem).multiplicity)


  def multiplicities: Map[A, Int] = new Multiplicities(repr)

  def toMultiplicities: Map[A, Int] = Map.empty ++ bucketsIterator.map(g => (g.sentinel, g.multiplicity))

  def zipWithMultiplicities: Iterable[(A, Int)] = multiplicitiesIterator.toIterable

  def setMultiplicity(elem: A, count: Int): This = this -* elem + (elem -> count)


  def fold[A1 >: A](z: A1)(op: (A1, A1) => A1, op2: (A1, Int) => A1) = (multiplicitiesIterator map ((tup: (A, Int)) => op2(tup._1, tup._2))).fold(z)(op)

  def reduce[A1 >: A](op: (A1, A1) => A1, op2: (A1, Int) => A1): A1 = (multiplicitiesIterator map ((tup: (A, Int)) => op2(tup._1, tup._2))).reduce(op)

}
