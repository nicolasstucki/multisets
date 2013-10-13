package scala.collection


trait SeqBagBucket[A] extends BagBucket[A] {

  assert(sequence.hasDefiniteSize)

  def sequence: Seq[A]

  def multiplicity: Int = sequence.size

  def iterator: Iterator[A] = sequence.iterator
}
