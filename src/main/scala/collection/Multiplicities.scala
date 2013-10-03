package scala.collection


trait Multiplicities[A, G <: Group[A, G]] {

  def empty(sentinel: A): G

  def apply(elem: A): G

  def apply(elemCount: (A, Int)): G

}


object SeqGroups {
  def of[A]: SeqGroups[A] = new SeqGroups[A]
}

class SeqGroups[A] extends Multiplicities[A, SeqGroup[A]] {

  def empty(sentinel: A): SeqGroup[A] = new SeqGroup(sentinel, Seq.empty)

  def apply(elem: A): SeqGroup[A] = new SeqGroup(elem, Seq(elem))

  def apply(elemCount: (A, Int)): SeqGroup[A] = {
    val (elem, count) = elemCount
    new SeqGroup(elem, Seq.fill(count)(elem))
  }

}

object CountGroups {
  def of[A]: CountGroups[A] = new CountGroups[A]
}

class CountGroups[A] extends Multiplicities[A, CountGroup[A]] {

  def empty(sentinel: A): CountGroup[A] = new CountGroup(sentinel, 0)

  def apply(elem: A): CountGroup[A] = new CountGroup(elem, 1)

  def apply(elemCount: (A, Int)): CountGroup[A] = {
    val (elem, count) = elemCount
    new CountGroup(elem, count)
  }

}


trait Group[A, G <: Group[A, G]] {

  protected def sentinel: A

  def iterator: Iterator[A]

  def multiplicity: Int

  def +(elem: A): G

  def -(elem: A): G

  def isEmpty: Boolean
}


class SeqGroup[A](val sentinel: A, seq: Seq[A]) extends Group[A, SeqGroup[A]] {

  def isEmpty: Boolean = seq.isEmpty

  def iterator: Iterator[A] = seq.iterator

  def multiplicity: Int = seq.length

  def +(elem: A): SeqGroup[A] = {
    assert(elem == sentinel)
    new SeqGroup(sentinel, seq :+ elem)
  }

  def -(elem: A): SeqGroup[A] = {
    assert(elem == sentinel)
    if (seq.isEmpty) this
    else new SeqGroup(sentinel, seq.drop(1))
  }

}


class CountGroup[A](val sentinel: A, count: Int) extends Group[A, CountGroup[A]] {

  assert(count >= 0)

  def isEmpty: Boolean = count > 0

  def iterator: Iterator[A] = Iterator.fill(count)(sentinel)

  def multiplicity: Int = count

  def +(elem: A): CountGroup[A] = {
    assert(elem == sentinel)
    new CountGroup(sentinel, count + 1)
  }

  def -(elem: A): CountGroup[A] = {
    assert(elem == sentinel)
    new CountGroup(sentinel, Math.max(count - 1, 0))
  }

}


