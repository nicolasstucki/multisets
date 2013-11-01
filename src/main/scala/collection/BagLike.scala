package scala.collection


import scala.collection.generic.{CanBuildFrom, Subtractable}
import scala.annotation.tailrec

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
    for (bucket <- bucketsIterator if maxM == bucket.multiplicity && p(bucket.sentinel); elem <- bucket) {
      b += elem
    }
    b.result()
  }

  override def leastCommon(p: A => Boolean = _ => true): Bag[A] = {
    val minM = minMultiplicity(p)
    var b = newBuilder
    for (bucket <- bucketsIterator if minM == bucket.multiplicity && p(bucket.sentinel); elem <- bucket) {
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
    for (bucket <- this.bucketsIterator; elem <- bucket) {
      b += elem
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
    for (bucket <- bucketsIterator if that(bucket.sentinel).multiplicity > 0) {
      if (that(bucket.sentinel).multiplicity > bucket.multiplicity) {
        for (elem <- bucket) b += elem
      } else {
        for (elem <- that(bucket.sentinel)) b += elem
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

  def mapBuckets[B, That](op: (BagBucket) => B)(implicit bf: CanBuildFrom[This, B, That]): That = {
    def builder = {
      val b = bf(repr)
      b.sizeHint(this)
      b
    }
    val b = builder
    for (bucket <- bucketsIterator) b += op(bucket)
    b.result
  }


  override def sum[B >: A](implicit num: Numeric[B]): B = bucketsIterator.map(_.sum(num)).foldLeft(num.zero)(num.plus)

  override def product[B >: A](implicit num: Numeric[B]): B = bucketsIterator.map(_.product(num)).foldLeft(num.one)(num.times)

  override def min[B >: A](implicit cmp: Ordering[B]): A = bucketsIterator.map(_.min(cmp)).min(cmp)

  override def max[B >: A](implicit cmp: Ordering[B]): A = bucketsIterator.map(_.max(cmp)).max(cmp)


  override def reduceLeft[B >: A](op: (B, A) => B): B = {
    if (isEmpty)
      throw new UnsupportedOperationException("empty.reduceLeft")

    var first = true
    var acc: B = 0.asInstanceOf[B]

    for (bucket <- bucketsIterator if !bucket.isEmpty) {
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
