package scala.collection

trait GenBagLike[A, +Repr]
  extends GenIterableLike[A, Repr]
  with (A => collection.BagBucket[A])
  with Equals {

  protected type BagBucket <: collection.BagBucket[A]
  protected type BagBucketFactory <: collection.BagBucketFactory[A, BagBucket]

  protected def bucketFactory: BagBucketFactory

  def apply(elem: A): collection.BagBucket[A] = getBucket(elem) match {
    case Some(bucket) => bucket
    case None => bucketFactory.newBuilder(elem).result()
  }


  def bucketsIterator: Iterator[BagBucket]

  def iterator: Iterator[A] = bucketsIterator.flatMap(_.iterator)

  def multiplicitiesIterator: Iterator[(A, Int)] = bucketsIterator.map(g => g.sentinel -> g.multiplicity)

  def distinctIterator: Iterator[A] = bucketsIterator.map(_.sentinel)

  def distinctSize: Int = bucketsIterator.size


  def contains(elem: A): Boolean

  def +(elem: A): Repr

  def -(elem: A): Repr

  def mostCommon(p: A => Boolean = _ => true): Bag[A]

  def leastCommon(p: A => Boolean = _ => true): Bag[A]


  def getBucket(elem: A): Option[BagBucket] = bucketsIterator.find(_.sentinel == elem)


  def maxMultiplicity(p: A => Boolean = _ => true): Int = (0 :: (distinctIterator filter p map (this(_).multiplicity)).toList).max

  def minMultiplicity(p: A => Boolean = _ => true): Int = (Int.MaxValue :: (distinctIterator filter p map (this(_).multiplicity)).toList).max


  def intersect(that: GenBag[A]): Repr

  def &(that: GenBag[A]): Repr = this intersect that

  def union(that: GenBag[A]): Repr

  def |(that: GenBag[A]): Repr = this union that

  def maxUnion(that: GenBag[A]): Repr

  def diff(that: GenBag[A]): Repr

  def &~(that: GenBag[A]): Repr = this diff that

  def subsetOf(that: GenBag[A]): Boolean = bucketsIterator.forall(bkt => bkt.multiplicity <= that(bkt.sentinel).multiplicity)

  def properSubsetOf(that: GenBag[A]): Boolean = this.size < that.size && bucketsIterator.forall(bkt => bkt.multiplicity <= that(bkt.sentinel).multiplicity)


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
