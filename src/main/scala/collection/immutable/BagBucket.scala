package scala.collection.immutable

import scala.collection.immutable
import scala.collection
import scala.language.higherKinds

trait BagBucket[A] extends scala.collection.BagBucket[A] {

  protected override type BagBucket[X] = immutable.BagBucket[X]

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


  def -(elem: A): MultiplicityBagBucket[A] = {
    new MultiplicityBagBucket(sentinel, Math.max(0, multiplicity - 1))
  }

  def removed(elem: A, count: Int): MultiplicityBagBucket[A]#BagBucket[A] = {
    new MultiplicityBagBucket(sentinel, Math.max(0, multiplicity - count))
  }

  def intersect(that: collection.BagBucket[A]): MultiplicityBagBucket[A]#BagBucket[A] =
    new immutable.MultiplicityBagBucket(sentinel, Math.min(this.multiplicity, that.multiplicity(sentinel)))

  def diff(that: collection.BagBucket[A]): MultiplicityBagBucket[A]#BagBucket[A] =
    new immutable.MultiplicityBagBucket(sentinel, Math.max(this.multiplicity - that.multiplicity(sentinel), 0))

}

class BagBucketBag[A](val sentinel: A, val bag: immutable.Bag[A])
  extends scala.collection.BagBucketBag[A]
  with immutable.BagBucket[A] {

  protected type Bag[X] = immutable.Bag[X]

  def intersect(that: collection.BagBucket[A]): BagBucket[A] = that match {
    case bagBucketBag: collection.BagBucketBag[A] => new BagBucketBag(sentinel, bag intersect bagBucketBag.bag)
    case _ => new BagBucketBag(sentinel, bag.intersect(bag.empty ++ that))
  }

  def diff(that: collection.BagBucket[A]): BagBucket[A] = that match {
    case bagBucketBag: collection.BagBucketBag[A] => new BagBucketBag(sentinel, bag diff bagBucketBag.bag)
    case _ => new BagBucketBag(sentinel, bag.diff(bag.empty ++ that))
  }

  def added(elem: A, count: Int): BagBucket[A] = new BagBucketBag(sentinel, bag.added(elem, count))

  def addedBucket(bucket: collection.BagBucket[A]): BagBucket[A] = new BagBucketBag(sentinel, bag.addedBucket(bucket))

  def -(elem: A): BagBucket[A] = new BagBucketBag(sentinel, bag - elem)

  def removed(elem: A, count: Int): BagBucket[A] = new BagBucketBag(sentinel, bag.removed(elem, count))
}

class VectorBagBucket[A](val sentinel: A, val vector: Vector[A])
  extends scala.collection.VectorBagBucket[A]
  with immutable.BagBucket[A] {

  def added(elem: A, count: Int) = {
    if (count > 0)
      new immutable.VectorBagBucket[A](elem, vector ++ Iterator.fill(count)(elem))
    else
      this
  }

  def addedBucket(bucket: collection.BagBucket[A]): immutable.BagBucket[A] = {
    new immutable.VectorBagBucket[A](sentinel, this.vector ++ bucket)
  }

  def -(elem: A): VectorBagBucket[A] = {
    if (vector.isEmpty)
      new VectorBagBucket(sentinel, Vector.empty[A])
    else
      new VectorBagBucket(sentinel, vector.tail)
  }

  def intersect(that: collection.BagBucket[A]): BagBucket[A] = new immutable.VectorBagBucket[A](sentinel, this.toList.intersect(that.toSeq).toVector)

  def diff(that: collection.BagBucket[A]): BagBucket[A] = new immutable.VectorBagBucket[A](sentinel, this.toList.diff(that.toSeq).toVector)

  def removed(elem: A, count: Int): BagBucket[A] = {
    var c = count
    var v = Vector.empty[A]
    for (e <- iterator) {
      if (e == elem) {
        if (c > 0) {
          v = v :+ elem
          c -= 1
        }
      } else {
        v = v :+ elem
      }

    }
    new immutable.VectorBagBucket[A](sentinel, v)
  }
}



