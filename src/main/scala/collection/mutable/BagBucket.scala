package scala.collection.mutable

import scala.collection.{immutable, mutable}
import scala.collection

trait BagBucket[A] extends scala.collection.BagBucket[A] {

  protected override type BagBucket = mutable.BagBucket[A]

  def +=(elem: A): this.type

  def -=(elem: A): this.type

  def add(elem: A, count: Int): this.type

  def addBucket(bucket: collection.BagBucket[A]): this.type

}


class MultiplicityBagBucket[A](val sentinel: A, var multiplicity: Int)
  extends scala.collection.MultiplicityBagBucket[A]
  with mutable.BagBucket[A] {

  def update(elem: A, multiplicity: Int): Unit = if (elem == sentinel) this.multiplicity = multiplicity

  def +=(elem: A) = {
    sentinelCheck(elem)
    multiplicity += 1
    this
  }

  def -=(elem: A) = {
    sentinelCheck(elem)
    multiplicity = Math.max(0, multiplicity - 1)
    this
  }

  def +(elem: A) = {
    sentinelCheck(elem)
    new mutable.MultiplicityBagBucket[A](sentinel, multiplicity + 1)
  }


  def added(elem: A, count: Int) = {
    sentinelCheck(elem)
    new mutable.MultiplicityBagBucket[A](sentinel, multiplicity + Math.max(count, 0))
  }


  def add(elem: A, count: Int) = {
    sentinelCheck(elem)
    this.multiplicity += Math.max(count, 0)
    this
  }

  def addBucket(bucket: collection.BagBucket[A]) = {
    sentinelCheck(bucket.sentinel)
    this.multiplicity += bucket.multiplicity
    this
  }

  def addedBucket(bucket: collection.BagBucket[A]) = {
    sentinelCheck(bucket.sentinel)
    new mutable.MultiplicityBagBucket[A](sentinel, this.multiplicity + bucket.multiplicity)
  }

  def -(elem: A) = {
    assert(elem == sentinel)
    new mutable.MultiplicityBagBucket[A](sentinel, Math.max(0, multiplicity - 1))
  }
}


class SeqBagBucket[A](val sentinel: A, var sequence: immutable.Seq[A])
  extends scala.collection.SeqBagBucket[A]
  with mutable.BagBucket[A] {

  def +=(elem: A) = {
    sentinelCheck(elem)
    sequence = sequence :+ elem
    this
  }

  def -=(elem: A) = {
    sentinelCheck(elem)
    sequence = sequence.tail
    this
  }


  def added(elem: A, count: Int) = {
    sentinelCheck(elem)
    new mutable.SeqBagBucket[A](sentinel, sequence ++ Iterable.fill(count)(elem))
  }


  def add(elem: A, count: Int) = {
    sentinelCheck(elem)
    sequence = sequence ++ Iterator.fill(count)(elem)
    this
  }

  def addBucket(bucket: collection.BagBucket[A]) = {
    sentinelCheck(bucket.sentinel)
    sequence = sequence ++ bucket
    this
  }

  def +(elem: A) = {
    sentinelCheck(elem)
    new mutable.SeqBagBucket(sentinel, sequence :+ elem)
  }


  def addedBucket(bucket: collection.BagBucket[A]) = {
    sentinelCheck(bucket.sentinel)
    new mutable.SeqBagBucket[A](sentinel, sequence ++ bucket)
  }

  def -(elem: A) = {
    sentinelCheck(elem)
    if (sequence.isEmpty) this
    else new mutable.SeqBagBucket(sentinel, sequence.tail)
  }
}


