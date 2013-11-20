package scala.collection

import scala.language.higherKinds


trait SortedBagLike[A, +This <: SortedBagLike[A, This] with SortedBag[A]]
  extends BagLike[A, This] {
  self =>

  protected type BagBucketFactory[X] <: SortedBagBucketFactory[X, BagBucket[X]]
}
