package scala.collection


trait MultiplicityBagBucket[A] extends BagBucket[A] {

  override def isEmpty: Boolean = multiplicity == 0

  override def iterator: Iterator[A] = Iterator.fill(multiplicity)(sentinel)
}


