package scala.collection

import scala.collection._

trait BagBucketFactory[A] {

  def empty(sentinel: A): BagBucket[A]

  def apply(elem: A): BagBucket[A]

  def apply(elem: A, multiplicity: Int): BagBucket[A]

  def apply(elemMultiplicity: (A, Int)): BagBucket[A]

}
