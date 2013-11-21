package scala.collection.immutable

import scala.collection.generic._
import scala.collection.immutable.{RedBlackTree => RB}
import scala.collection.mutable
import scala.collection


class TreeBag[A] private(tree: RB.Tree[A, BagBucket[A]])(implicit val bucketFactory: SortedBagBucketFactory[A])
  extends SortedBag[A]
  with SortedBagLike[A, TreeBag[A]]
  with BagLike[A, TreeBag[A]]
  with Serializable {

  implicit lazy val ord = new Ordering[BagBucket[A]] {
    def compare(x: BagBucket[A], y: BagBucket[A]): Int = bucketFactory.compare(x.sentinel, y.sentinel)
  }

  override protected[this] def newBuilder: mutable.BagBuilder[A, TreeBag[A]] = TreeBag.newBuilder


  def rangeImpl(from: Option[A], until: Option[A]): TreeBag[A] = new TreeBag[A](RB.rangeImpl(tree, from, until))

  def range(from: A, until: A): TreeBag[A] = new TreeBag[A](RB.range(tree, from, until))

  def from(from: A): TreeBag[A] = new TreeBag[A](RB.from(tree, from))

  def to(to: A): TreeBag[A] = new TreeBag[A](RB.to(tree, to))

  def until(until: A): TreeBag[A] = new TreeBag[A](RB.until(tree, until))


  def firstKey = RB.smallest(tree).key

  def lastKey = RB.greatest(tree).key

  def compare(k0: A, k1: A): Int = bucketFactory.compare(k0, k1)

  override def head = {
    val smallest = RB.smallest(tree)
    smallest.value.sentinel
  }


  override def headOption = if (RB.isEmpty(tree)) None else Some(head)

  override def last = {
    val greatest = RB.greatest(tree)
    greatest.value.sentinel
  }

  override def lastOption = if (RB.isEmpty(tree)) None else Some(last)

  override def tail = new TreeBag(RB.delete(tree, firstKey))

  override def init = new TreeBag(RB.delete(tree, lastKey))

  override def drop(n: Int) = {
    if (n <= 0) this
    else if (n >= size) empty
    else new TreeBag(RB.drop(tree, n))
  }

  override def take(n: Int) = {
    if (n <= 0) empty
    else if (n >= size) this
    else new TreeBag(RB.take(tree, n))
  }

  override def slice(from: Int, until: Int) = {
    if (until <= from) empty
    else if (from <= 0) take(until)
    else if (until >= size) drop(from)
    else new TreeBag(RB.slice(tree, from, until))
  }

  override def dropRight(n: Int) = take(size - n)

  override def takeRight(n: Int) = drop(size - n)

  override def splitAt(n: Int) = (take(n), drop(n))

  private[this] def countWhile(p: (A) => Boolean): Int = {
    var result = 0
    val it = iterator
    while (it.hasNext && p(it.next())) result += 1
    result
  }

  override def dropWhile(p: (A) => Boolean) = drop(countWhile(p))

  override def takeWhile(p: (A) => Boolean) = take(countWhile(p))

  override def span(p: (A) => Boolean) = splitAt(countWhile(p))

  override def empty: TreeBag[A] = TreeBag.empty[A]


  def bucketsIterator: Iterator[BagBucket[A]] = RB.valuesIterator(tree)

  def getBucket(elem: A): Option[BagBucket[A]] = RB.get(tree, elem)

  def addedBucket(bucket: collection.BagBucket[A]): TreeBag[A] = getBucket(bucket.sentinel) match {
    case Some(bucket2) => updated(bucketFactory.from(bucket, bucket2))
    case None => updated(bucketFactory.from(bucket))
  }


  def updated(bucket: BagBucket[A]): TreeBag[A] = new TreeBag(RB.update(tree, bucket.sentinel, bucket, overwrite = true))

}


object TreeBag extends ImmutableSortedBagFactory[TreeBag] {
  def empty[A](implicit bucketFactory: BagBucketFactory[A]) = new TreeBag[A](null)
}
