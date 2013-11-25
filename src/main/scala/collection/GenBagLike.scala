package scala.collection

import scala.language.higherKinds

trait GenBagLike[A, +Repr]
  extends GenIterableLike[A, Repr]
  with Equals {


  protected type BagBucket[X] <: collection.BagBucket[X]
  protected type BagBucketFactory[X] <: collection.BagBucketFactory[X, BagBucket[X]]

  protected def bucketFactory: BagBucketFactory[A]



  def bucketsIterator: Iterator[BagBucket[A]]

  def iterator: Iterator[A] = bucketsIterator.flatMap(_.iterator)

  def distinctIterator: Iterator[A] = bucketsIterator.flatMap(_.distinctIterator)

  def multiplicity(elem: A): Int = getBucket(elem) match {
    case Some(bucket) => bucket.multiplicity(elem)
    case None => 0
  }

  def contains(elem: A): Boolean

  def +(elem: A): Repr

  def -(elem: A): Repr

  def mostCommon: Bag[A]

  def leastCommon: Bag[A]


  def getBucket(elem: A): Option[BagBucket[A]]


  def maxMultiplicity: Int = bucketsIterator.map(_.maxMultiplicity).max

  def minMultiplicity: Int = bucketsIterator.map(_.minMultiplicity).min

  def intersect(that: GenBag[A]): Repr

  def &(that: GenBag[A]): Repr = this intersect that

  def union(that: GenBag[A]): Repr

  def |(that: GenBag[A]): Repr = this union that

  def maxUnion(that: GenBag[A]): Repr

  def diff(that: GenBag[A]): Repr

  def &~(that: GenBag[A]): Repr = this diff that

  def subsetOf(that: GenBag[A]): Boolean = {
    bucketsIterator.forall(
      bucket => that.getBucket(bucket.sentinel) match {
        case Some(bucket2) => bucket subsetOf bucket2
        case None => false
      })
  }


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

  override def hashCode() = scala.util.hashing.MurmurHash3.seqHash(this.toList)
}

