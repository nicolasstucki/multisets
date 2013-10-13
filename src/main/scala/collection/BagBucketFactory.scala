package scala.collection


trait BagBucketFactory[A, B <: BagBucket[A]] {

  def empty(sentinel: A): B

  def apply(elem: A): B

  def apply(elem: A, multiplicity: Int): B

  def apply(elemMultiplicity: (A, Int)): B = apply(elemMultiplicity._1, elemMultiplicity._2)


}
