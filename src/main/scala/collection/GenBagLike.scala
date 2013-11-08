package scala.collection

trait GenBagLike[A, +Repr]
  extends GenIterableLike[A, Repr]
  with (A => Int)
  with Equals {

  protected type BagBucket <: collection.BagBucket[A]
  protected type BagBucketFactory <: collection.BagBucketFactory[A, BagBucket]

  protected def bucketFactory: BagBucketFactory

  def apply(elem: A): Int = multiplicity(elem)


  def bucketsIterator: Iterator[BagBucket]

  def iterator: Iterator[A] = bucketsIterator.flatMap(_.iterator)

  def multiplicitiesIterator: Iterator[(A, Int)] = bucketsIterator.map(g => g.sentinel -> g.multiplicity)

  def distinctIterator: Iterator[A] = bucketsIterator.map(_.sentinel)

  def multiplicity(elem: A): Int = getBucket(elem) match {
    case Some(bucket) => bucket.multiplicity
    case None => 0
  }

  def contains(elem: A): Boolean

  def +(elem: A): Repr

  def -(elem: A): Repr

  def mostCommon: Bag[A]

  def leastCommon: Bag[A]


  def getBucket(elem: A): Option[BagBucket] = bucketsIterator.find(_.sentinel == elem)


  def maxMultiplicity: Int = bucketsIterator.map(_.multiplicity).max

  def minMultiplicity: Int = bucketsIterator.map(_.multiplicity).min

  def intersect(that: GenBag[A]): Repr

  def &(that: GenBag[A]): Repr = this intersect that

  def union(that: GenBag[A]): Repr

  def |(that: GenBag[A]): Repr = this union that

  def maxUnion(that: GenBag[A]): Repr

  def diff(that: GenBag[A]): Repr

  def &~(that: GenBag[A]): Repr = this diff that

  def subsetOf(that: GenBag[A]): Boolean = bucketsIterator.forall(bkt => bkt.multiplicity <= multiplicity(bkt.sentinel))

  def toMap: immutable.Map[A, Int] = Map.empty ++ multiplicitiesIterator

  override def equals(that: Any): Boolean = that match {
    case that: GenBag[_] =>
      (this eq that) ||
        (that canEqual this) &&
          (this.size == that.size) &&
          (try this subsetOf that.asInstanceOf[GenBag[A]]
          catch {
            case ex: ClassCastException => false
          })
    case _ =>
      false
  }

  override def hashCode() = scala.util.hashing.MurmurHash3.mapHash(this.toMap)
}
