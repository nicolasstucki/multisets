package scala.collection.immutable

import scala.collection.{immutable, generic, mutable}
import scala.collection.immutable.{RedBlackTree => RB}
import scala.collection.generic.CanBuildFrom


class TreeBag[A] private(tree: RB.Tree[A, BagBucket[A]])(implicit val bagConfiguration: immutable.SortedBagConfiguration[A])
  extends Bag[A]
  with BagLike[A, TreeBag[A]]
  with Serializable {

  implicit val ord: Ordering[A] = bagConfiguration.equivClass

  override protected[this] def newBuilder: mutable.BagBuilder[A, immutable.TreeBag[A]] = immutable.TreeBag.newBuilder

  override def size: Int = bucketsIterator.map(_.size).sum

  def rangeImpl(from: Option[A], until: Option[A]): TreeBag[A] = new TreeBag[A](RB.rangeImpl(tree, from, until))

  def range(from: A, until: A): TreeBag[A] = new TreeBag[A](RB.range(tree, from, until))

  def from(from: A): TreeBag[A] = new TreeBag[A](RB.from(tree, from))

  def to(to: A): TreeBag[A] = new TreeBag[A](RB.to(tree, to))

  def until(until: A): TreeBag[A] = new TreeBag[A](RB.until(tree, until))


  def firstKey = RB.smallest(tree).key

  def lastKey = RB.greatest(tree).key


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

  def bucketsIterator: Iterator[BagBucket] = RB.valuesIterator(tree)

  override def getBucket(elem: A): Option[BagBucket] = RB.get(tree, elem)

  override protected[collection] def updatedBucket(bucket: BagBucket): TreeBag[A] = new TreeBag(RB.update(tree, bucket.sentinel, bucket, overwrite = true))

}


object TreeBag extends generic.ImmutableSortedBagFactory[immutable.TreeBag] {

  implicit def canBuildFrom[A](implicit bagConfiguration: SortedBagConfiguration[A]): CanBuildFrom[Coll, A, immutable.TreeBag[A]] = bagCanBuildFrom[A]

  def empty[A](implicit bagConfiguration: SortedBagConfiguration[A]): TreeBag[A] = new TreeBag[A](null)
}
