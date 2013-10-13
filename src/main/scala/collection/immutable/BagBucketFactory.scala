package scala.collection.immutable

import scala.collection.immutable

trait BagBucketFactory[A]
  extends scala.collection.BagBucketFactory[A] {

  def empty(sentinel: A): immutable.BagBucket[A]

  def apply(elem: A): immutable.BagBucket[A] = {
    empty(elem) + elem
  }

  def apply(elem: A, multiplicity: Int): immutable.BagBucket[A] = {
    var b = empty(elem)
    for (_ <- 1 to multiplicity) {
      b = b + elem
    }
    b
  }

  def apply(elemMultiplicity: (A, Int)): immutable.BagBucket[A] = {
    apply(elemMultiplicity._1, elemMultiplicity._2)
  }

}
