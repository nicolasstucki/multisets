package scala.collection.mutable

import scala.collection.mutable

trait BagBucketFactory[A]
  extends scala.collection.BagBucketFactory[A] {

  def empty(sentinel: A): mutable.BagBucket[A]

  override def apply(elem: A): mutable.BagBucket[A] = {
    empty(elem) += elem
  }

  override def apply(elem: A, multiplicity: Int): mutable.BagBucket[A] = {
    var b = empty(elem)
    for (_ <- 1 to multiplicity) {
      b += elem
    }
    b
  }

  override def apply(elemMultiplicity: (A, Int)): mutable.BagBucket[A] = apply(elemMultiplicity._1, elemMultiplicity._2)

}
