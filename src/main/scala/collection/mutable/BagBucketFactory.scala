package scala.collection.mutable

import scala.collection.{immutable, mutable}

trait BagBucketFactory[A]
  extends scala.collection.BagBucketFactory[A, mutable.BagBucket[A]] {

  override def from(elem: A) = {
    empty(elem) += elem
  }

  override def from(elem: A, multiplicity: Int) = {
    var b = empty(elem)
    for (_ <- 1 to multiplicity) {
      b += elem
    }
    b
  }

  def from(bucket: collection.BagBucket[A]) = empty(bucket.sentinel) addedBucket bucket
}


object BagBucketFactory {

  def ofMultiplicities[A] = new MultiplicityBagBucketFactory[A]

  def ofSeq[A] = new SeqBagBucketFactory[A]


  class MultiplicityBagBucketFactory[A] extends scala.collection.mutable.BagBucketFactory[A] {

    def empty(sentinel: A) = new mutable.MultiplicityBagBucket(sentinel, 0)

    override def from(elem: A) = new mutable.MultiplicityBagBucket(elem, 1)

    override def from(elem: A, multi: Int) = new mutable.MultiplicityBagBucket(elem, multi)

  }


  class SeqBagBucketFactory[A] extends scala.collection.mutable.BagBucketFactory[A] {

    def empty(sentinel: A) = new mutable.SeqBagBucket(sentinel, immutable.Seq.empty[A])

    override def from(elem: A) = new mutable.SeqBagBucket(elem, immutable.Seq(elem))

    override def from(elem: A, multiplicity: Int) = new mutable.SeqBagBucket(elem, immutable.Seq.fill(multiplicity)(elem))

  }

}


