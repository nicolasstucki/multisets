package scala.collection

import scala.language.higherKinds

trait GenBagLike[A, +Repr]
  extends GenIterableLike[A, Repr]
  with Equals {

  protected type BagBucket <: collection.BagBucket[A]

  /**
   *
   * @return
   */
  protected def bagConfiguration: collection.BagConfiguration[A, BagBucket]

  /**
   * Iterator over buckets
   * @return
   */
  def bucketsIterator: Iterator[BagBucket]

  def iterator: Iterator[A] = bucketsIterator.flatMap(_.iterator)

  /**
   * Iterator over distinct elements
   * @return
   */
  def distinctIterator: Iterator[A] = bucketsIterator.flatMap(_.distinctIterator)

  def multiplicity(elem: A): Int = getBucket(elem) match {
    case Some(bucket) => bucket.multiplicity(elem)
    case None => 0
  }

  def multiplicities: Iterator[(A, Int)] = {
    for (bucket <- bucketsIterator; elem <- bucket.distinctIterator) yield
      (elem, bucket.multiplicity(elem))
  }

  /** Tests if some element is contained in this bag.
    *
    * @param elem the element to test for membership.
    * @return     `true` if `elem` is contained in this bag, `false` otherwise.
    */
  def contains(elem: A): Boolean

  /**
   *
   * @param elem
   * @param count
   * @return
   */
  def added(elem: A, count: Int): Repr

  /**
   * Add the element to the bag
   * @param elem
   * @return
   */
  def +(elem: A): Repr = added(elem, 1)

  /**
   *
   * @param elemCount
   * @return
   */
  def +(elemCount: (A, Int)): Repr = added(elemCount._1, elemCount._2)

  /**
   *
   * @param elem
   * @return
   */
  def -(elem: A): Repr

  /** Returns a Bag with the most common elements up to the equivalence defined in the `BagConfiguration`
    * @return An bag with the most common elements up to equivalence
    */
  def mostCommon: Repr

  /** Returns a Bag with the least common elements up to the equivalence defined in the `BagConfiguration`
    * @return An bag with the least common elements up to equivalence
    */
  def leastCommon: Repr


  /**
   * Returns the bucket associated with some key element. All elements in that bucket are equivalent to `elem`
   * @param elem Key element
   * @return `Some(bucket)` if the bucket exists in the bag or `None` if the bucket doesn't exist in the bag.
   */
  def getBucket(elem: A): Option[BagBucket]


  /**
   * Query for the maximum multiplicity of elements in the bag.
   * @return The maximum multiplicity of the bag
   */
  def maxMultiplicity: Int = bucketsIterator.map(_.maxMultiplicity).max

  /**
   * Query for the minimum multiplicity of elements in the bag.
   * @return The minimum multiplicity of the bag
   */
  def minMultiplicity: Int = bucketsIterator.map(_.minMultiplicity).min

  /**
   * Returns a bag containing the multi-set intersection of this `bag` and `that` bag
   * @param that the other bag
   * @return bag containing the multi-set intersection of this `bag` and `that` bag
   */
  def intersect(that: GenBag[A]): Repr

  /** Shorthand for `intersect`
    */
  @inline def &(that: GenBag[A]): Repr = this intersect that

  /**
   * Returns a bag containing the multi-set union of this `bag` and `that` bag
   * @param that the other bag
   * @return bag containing the multi-set union of this `bag` and `that` bag
   */
  def union(that: GenBag[A]): Repr

  /** Shorthand for `union`
    */
  @inline def |(that: GenBag[A]): Repr = this union that

  /**
   * Returns a bag containing the multi-set max union (or generalized set union) of this `bag` and `that` bag
   * @param that the other bag
   * @return bag containing the multi-set max union of this `bag` and `that` bag
   */
  def maxUnion(that: GenBag[A]): Repr

  /**
   * Returns a bag containing the multi-set difference of this `bag` with `that` bag
   * @param that the other bag
   * @return bag containing the multi-set difference of this `bag` and `that` bag
   */
  def diff(that: GenBag[A]): Repr

  /** Shorthand for `diff`
    */
  @inline def &~(that: GenBag[A]): Repr = this diff that

  /**
   * Tests if this bag is subset of `that` bag, where the subset is the multiset subset.
   * @param that the other bag.
   * @return `true` if this bag is subset of `that` bag, `false` if not.
   */
  def subsetOf(that: GenBag[A]): Boolean = {
    bucketsIterator.forall(bucket =>
      bucket.distinctIterator.forall(elem =>
        bucket.multiplicity(elem) <= that.multiplicity(elem)
      )
    )
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

  // TODO design efficient hashCode() for bags
  override def hashCode() = scala.util.hashing.MurmurHash3.seqHash(this.toList)
}

