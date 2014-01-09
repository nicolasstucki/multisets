package scala.collection.immutable

import scala.collection.immutable
import scala.collection
import scala.annotation.tailrec

trait BagBucket[A] extends scala.collection.BagBucket[A] {

  final protected override type BagBucket = immutable.BagBucket[A]

}


class MultiplicityBagBucket[A](val sentinel: A, val multiplicity: Int)
  extends scala.collection.MultiplicityBagBucket[A]
  with immutable.BagBucket[A] {

  def added(elem: A, count: Int) = {
    if (count > 0)
      new immutable.MultiplicityBagBucket(sentinel, multiplicity + count)
    else
      this
  }

  def addedBucket(bucket: collection.BagBucket[A]): immutable.BagBucket[A] = {
    new immutable.MultiplicityBagBucket[A](sentinel, this.multiplicity + bucket.multiplicity(sentinel))
  }


  override def -(elem: A): MultiplicityBagBucket[A] = {
    new MultiplicityBagBucket(sentinel, Math.max(0, multiplicity - 1))
  }

  def removed(elem: A, count: Int): BagBucket = {
    new MultiplicityBagBucket(sentinel, Math.max(0, multiplicity - count))
  }

  def intersect(that: collection.BagBucket[A]): BagBucket =
    new immutable.MultiplicityBagBucket(sentinel, Math.min(this.multiplicity, that.multiplicity(sentinel)))

  def diff(that: collection.BagBucket[A]): BagBucket =
    new immutable.MultiplicityBagBucket(sentinel, Math.max(this.multiplicity - that.multiplicity(sentinel), 0))

}

class BagOfMultiplicitiesBagBucket[A](val sentinel: A, val bag: immutable.Bag[A])
  extends scala.collection.BagOfMultiplicitiesBagBucket[A]
  with immutable.BagBucket[A] {

  def intersect(that: collection.BagBucket[A]): BagBucket = that match {
    case bagBucketBag: collection.BagOfMultiplicitiesBagBucket[A] => new BagOfMultiplicitiesBagBucket(sentinel, bag intersect bagBucketBag.bag)
    case _ => new BagOfMultiplicitiesBagBucket(sentinel, bag.intersect(bag.empty ++ that))
  }

  def diff(that: collection.BagBucket[A]): BagBucket = that match {
    case bagBucketBag: collection.BagOfMultiplicitiesBagBucket[A] => new BagOfMultiplicitiesBagBucket(sentinel, bag diff bagBucketBag.bag)
    case _ => new BagOfMultiplicitiesBagBucket(sentinel, bag.diff(bag.empty ++ that))
  }

  def added(elem: A, count: Int): BagBucket = new BagOfMultiplicitiesBagBucket(sentinel, bag.added(elem, count))

  def addedBucket(bucket: collection.BagBucket[A]): BagBucket = new BagOfMultiplicitiesBagBucket(sentinel, bag.addedBucket(bucket))

  override def -(elem: A): BagBucket = new BagOfMultiplicitiesBagBucket(sentinel, bag - elem)

  def removed(elem: A, count: Int): BagBucket = new BagOfMultiplicitiesBagBucket(sentinel, bag.removed(elem, count))
}

class ListBagBucket[A](val sentinel: A, val list: List[A])
  extends scala.collection.ListBagBucket[A]
  with immutable.BagBucket[A] {


  override def toList: scala.List[A] = list

  def added(elem: A, count: Int) = {
    if (count > 0) new immutable.ListBagBucket[A](elem, Iterator.fill(count)(elem) ++: list)
    else this
  }

  def addedBucket(bucket: collection.BagBucket[A]): immutable.BagBucket[A] = {
    new immutable.ListBagBucket[A](sentinel, bucket ++: this.list)
  }

  def intersect(that: collection.BagBucket[A]): BagBucket = new immutable.ListBagBucket[A](sentinel, list.intersect(that.toList))

  def diff(that: collection.BagBucket[A]): BagBucket = new immutable.ListBagBucket[A](sentinel, list.diff(that.toList))

  def removed(elem: A, count: Int): BagBucket = new immutable.ListBagBucket[A](sentinel, removedFromList(elem, list, count))
}



