package scala.collection


import scala.collection.generic.{CanBuildFrom, Subtractable}
import scala.collection.mutable.{BagBuilder, Builder}

trait BagLike[A, +This <: BagLike[A, This] with Bag[A]]
  extends IterableLike[A, This]
  with GenBagLike[A, This]
  with Subtractable[A, This] {
  self =>


  def empty: This


  protected[this] def newBagBuilder: mutable.BagBuilder[A, This] = mutable.BagBuilder.newBuilder(empty)

  override protected[this] def newBuilder: mutable.Builder[A, This] = newBagBuilder

  def contains(elem: A): Boolean = repr.multiplicity(elem) > 0

  def mostCommon: Bag[A] = {
    if (isEmpty) return empty

    val maxM = maxMultiplicity
    val b = newBagBuilder
    for (bucket <- bucketsIterator if maxM == bucket.multiplicity) {
      b addBucket bucket
    }
    b.result()
  }

  override def leastCommon: Bag[A] = {
    if (isEmpty) return empty

    val minM = minMultiplicity
    val b = newBagBuilder
    for (bucket <- bucketsIterator if minM == bucket.multiplicity) {
      b addBucket bucket
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
  def +(elem: A): This = this addedBucket (bucketFactory.newBuilder(elem) += elem).result()


  def +(elemCount: (A, Int)): This = {
    val (elem, count) = elemCount
    val bb = bucketFactory.newBuilder(elem)
    bb.add(elem, count)
    this.addedBucket(bb.result())
  }

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

  def added(elem: A, count: Int): This = {
    val b = newBagBuilder
    for (bucket <- bucketsIterator) {
      b addBucket bucket
    }
    b.add(elem, count)
    b.result()
  }

  def addedBucket(bucket: collection.BagBucket[A]): This


  // Multiset operations
  override def union(that: GenBag[A]): This = this ++ that

  def diff(that: GenBag[A]): This = this -- that

  override def maxUnion(that: GenBag[A]): This = {
    newBuilder.result ++ (for (elem <- (this union that).distinctIterator) yield {
      for (_ <- (1 to Math.max(this.multiplicity(elem), that.multiplicity(elem))).iterator) yield {
        elem
      }
    }).flatten.toIterable
  }

  override def intersect(that: GenBag[A]): This = {
    val b = newBagBuilder
    for (bucket <- bucketsIterator if that.multiplicity(bucket.sentinel) > 0) {
      if (bucket.multiplicity <= that.multiplicity(bucket.sentinel)) {
        b addBucket bucket
      } else {
        b addBucket that.getBucket(bucket.sentinel).get
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

  def -*(elem: A): This = removedBucket(elem)

  def removedBucket(elem: A): This = {
    val b = newBagBuilder
    for (bucket <- bucketsIterator if bucket.sentinel != elem) {
      b addBucket bucket
    }
    b.result()
  }

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

  def mapBuckets[B, That](op: (BagBucket[A]) => B)(implicit bf: CanBuildFrom[This, B, That]): That = {
    def builder = {
      val b = bf(repr)
      b.sizeHint(this)
      b
    }
    val b = builder
    for (bucket <- bucketsIterator) b += op(bucket)
    b.result()
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
