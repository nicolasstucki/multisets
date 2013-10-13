package scala.collection.immutable

class SeqBagBucket[A](val sentinel: A, val sequence: Seq[A])
  extends scala.collection.SeqBagBucket[A]
  with BagBucket[A, SeqBagBucket[A]] {


  def +(elem: A): SeqBagBucket[A] = new SeqBagBucket(sentinel, sequence :+ elem)

  def -(elem: A): SeqBagBucket[A] = new SeqBagBucket(sentinel, sequence.tail)
}


object SeqBagBucketFactory {
  def of[A] = new SeqBagBucketFactory[A]
}

class SeqBagBucketFactory[A] extends scala.collection.BagBucketFactory[A, SeqBagBucket[A]] {

  def empty(sentinel: A): SeqBagBucket[A] = new SeqBagBucket(sentinel, Seq.empty[A])

  def apply(elem: A): SeqBagBucket[A] = new SeqBagBucket(elem, Seq(elem))

  def apply(elem: A, multiplicity: Int): SeqBagBucket[A] = new SeqBagBucket(elem, Seq.fill(multiplicity)(elem))

}
