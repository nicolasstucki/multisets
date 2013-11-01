package scala.collection.immutable

import scala.collection.immutable
import scala.collection

trait BagBucketFactory[A]
  extends scala.collection.BagBucketFactory[A, immutable.BagBucket[A]] {


  def from(elem: A) = {
    empty(elem) + elem
  }

  def from(elem: A, multiplicity: Int) = {
    var b = empty(elem)
    for (_ <- 1 to multiplicity) {
      b = b + elem
    }
    b
  }

  def from(bucket: collection.BagBucket[A]) = bucket match {
    case immutableBucket: immutable.BagBucket[A] => immutableBucket
    case _ => empty(bucket.sentinel) addedBucket bucket
  }

}

object BagBucketFactory {

  def ofMultiplicities[A] = new MultiplicityBagBucketFactory[A]

  def ofSeq[A] = new SeqBagBucketFactory[A]


  class MultiplicityBagBucketFactory[A] extends immutable.BagBucketFactory[A] {

    def empty(sentinel: A): MultiplicityBagBucket[A] = new MultiplicityBagBucket(sentinel, 0)

    override def from(elem: A): MultiplicityBagBucket[A] = new MultiplicityBagBucket(elem, 1)

    override def from(elem: A, multiplicity: Int): MultiplicityBagBucket[A] = new MultiplicityBagBucket(elem, multiplicity)

  }

  class SeqBagBucketFactory[A] extends immutable.BagBucketFactory[A] {

    def empty(sentinel: A): immutable.SeqBagBucket[A] = new immutable.SeqBagBucket(sentinel, Seq.empty[A])

    override def from(elem: A, multiplicity: Int): immutable.SeqBagBucket[A] = new immutable.SeqBagBucket(elem, Seq.fill(multiplicity)(elem))

  }

}
