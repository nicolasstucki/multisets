package scala.collection


trait BagBuckets[A, G <: BagBucket[A, G]] {

  def empty(sentinel: A): G

  def apply(elem: A): G

  def apply(elemCount: (A, Int)): G

}


object SeqBagBuckets {
  def of[A]: SeqBagBuckets[A] = new SeqBagBuckets[A]
}

class SeqBagBuckets[A] extends BagBuckets[A, SeqBagBucket[A]] {

  def empty(sentinel: A): SeqBagBucket[A] = new SeqBagBucket(sentinel, Seq.empty)

  def apply(elem: A): SeqBagBucket[A] = new SeqBagBucket(elem, Seq(elem))

  def apply(elemCount: (A, Int)): SeqBagBucket[A] = {
    val (elem, count) = elemCount
    new SeqBagBucket(elem, Seq.fill(count)(elem))
  }

}

object IntBagBuckets {
  def of[A]: IntBagBuckets[A] = new IntBagBuckets[A]
}

class IntBagBuckets[A] extends BagBuckets[A, CountBagBucket[A]] {

  def empty(sentinel: A): CountBagBucket[A] = new CountBagBucket(sentinel, 0)

  def apply(elem: A): CountBagBucket[A] = new CountBagBucket(elem, 1)

  def apply(elemCount: (A, Int)): CountBagBucket[A] = {
    val (elem, count) = elemCount
    new CountBagBucket(elem, count)
  }

}


trait BagBucket[A, G <: BagBucket[A, G]] {

  def sentinel: A

  def iterator: Iterator[A]

  def multiplicity: Int

  def +(elem: A): G

  def -(elem: A): G

  def isEmpty: Boolean
}


class SeqBagBucket[A](val sentinel: A, seq: Seq[A]) extends BagBucket[A, SeqBagBucket[A]] {

  def isEmpty: Boolean = seq.isEmpty

  def iterator: Iterator[A] = seq.iterator

  def multiplicity: Int = seq.length

  def +(elem: A): SeqBagBucket[A] = {
    assert(elem == sentinel)
    new SeqBagBucket(sentinel, seq :+ elem)
  }

  def -(elem: A): SeqBagBucket[A] = {
    assert(elem == sentinel)
    if (seq.isEmpty) this
    else new SeqBagBucket(sentinel, seq.drop(1))
  }

}


class CountBagBucket[A](val sentinel: A, count: Int) extends BagBucket[A, CountBagBucket[A]] {

  assert(count >= 0)

  def isEmpty: Boolean = count > 0

  def iterator: Iterator[A] = Iterator.fill(count)(sentinel)

  def multiplicity: Int = count

  def +(elem: A): CountBagBucket[A] = {
    assert(elem == sentinel)
    new CountBagBucket(sentinel, count + 1)
  }

  def -(elem: A): CountBagBucket[A] = {
    assert(elem == sentinel)
    new CountBagBucket(sentinel, Math.max(count - 1, 0))
  }

}


