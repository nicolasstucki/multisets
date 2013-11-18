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

  def +(elem: A): MultiplicityBagBucket[A] = {
    new immutable.MultiplicityBagBucket(sentinel, multiplicity + 1)
  }


  def added(elem: A, count: Int) = {
    if (count > 0)
      new immutable.MultiplicityBagBucket(sentinel, multiplicity + count)
    else
      this
  }

  def addedBucket(bucket: collection.BagBucket[A]): immutable.BagBucket[A] = {
    new immutable.MultiplicityBagBucket[A](sentinel, this.multiplicity + bucket.multiplicity)
  }

  def -(elem: A): MultiplicityBagBucket[A] = {
    new MultiplicityBagBucket(sentinel, Math.max(0, multiplicity - 1))
  }


}


class VectorBagBucket[A](val sentinel: A, val vector: Vector[A])
  extends scala.collection.VectorBagBucket[A]
  with immutable.BagBucket[A] {


  def +(elem: A): VectorBagBucket[A] = {
    new immutable.VectorBagBucket(sentinel, vector :+ elem)
  }


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
}



