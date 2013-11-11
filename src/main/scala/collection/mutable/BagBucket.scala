package scala.collection.mutable

import scala.collection.{immutable, mutable}
import scala.collection._
import scala.collection.generic.GrowableBag

trait BagBucket[A]
  extends scala.collection.BagBucket[A]
  with GrowableBag[A] {

  protected override type BagBucket[X] = mutable.BagBucket[X]

}


class MultiplicityBagBucket[A](val sentinel: A, var multiplicity: Int)
  extends scala.collection.MultiplicityBagBucket[A]
  with mutable.BagBucket[A] {


  def update(elem: A, multiplicity: Int): this.type = {
    if (elem == sentinel) this.multiplicity = multiplicity
    this
  }


  def clear(): Unit = multiplicity = 0

  override def +=(elem: A): this.type = {
    multiplicity += 1
    this
  }

  def -=(elem: A): this.type = {
    multiplicity = Math.max(0, multiplicity - 1)
    this
  }

  def +(elem: A): BagBucket[A] = {
    new mutable.MultiplicityBagBucket[A](sentinel, multiplicity + 1)
  }


  def added(elem: A, count: Int): BagBucket[A] = {
    new mutable.MultiplicityBagBucket[A](sentinel, multiplicity + Math.max(count, 0))
  }


  def add(elem: A, count: Int): this.type = {
    this.multiplicity += Math.max(count, 0)
    this
  }

  def addBucket(bucket: collection.BagBucket[A]): this.type = {
    this.multiplicity += bucket.multiplicity
    this
  }

  def addedBucket(bucket: collection.BagBucket[A]): BagBucket[A] = {
    new mutable.MultiplicityBagBucket[A](sentinel, this.multiplicity + bucket.multiplicity)
  }

  def -(elem: A): BagBucket[A] = {
    new mutable.MultiplicityBagBucket[A](sentinel, Math.max(0, multiplicity - 1))
  }
}


class SeqBagBucket[A](val sentinel: A, var sequence: immutable.Seq[A])
  extends scala.collection.SeqBagBucket[A]
  with mutable.BagBucket[A] {


  def clear(): Unit = {
    sequence = immutable.Seq.empty[A]
  }

  def result(): mutable.SeqBagBucket[A] = new mutable.SeqBagBucket[A](sentinel, sequence)


  override def +=(elem: A) = {
    sequence = sequence :+ elem
    this
  }

  def -=(elem: A) = {
    sequence = sequence.tail
    this
  }


  def added(elem: A, count: Int) = {
    new mutable.SeqBagBucket[A](sentinel, sequence ++ mutable.Iterable.fill(count)(elem))
  }


  def add(elem: A, count: Int) = {
    sequence = sequence ++ Iterator.fill(count)(elem)
    this
  }

  def addBucket(bucket: collection.BagBucket[A]) = {
    sequence = sequence ++ bucket
    this
  }

  def +(elem: A) = {
    new mutable.SeqBagBucket(sentinel, sequence :+ elem)
  }


  def addedBucket(bucket: collection.BagBucket[A]) = {
    new mutable.SeqBagBucket[A](sentinel, sequence ++ bucket)
  }

  def -(elem: A) = {
    if (sequence.isEmpty) this
    else new mutable.SeqBagBucket(sentinel, sequence.tail)
  }
}


