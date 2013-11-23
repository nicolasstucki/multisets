package scala.collection

import scala.language.higherKinds


trait HashedBagLike[A, +This <: HashedBagLike[A, This] with HashedBag[A]]
  extends BagLike[A, This] {
  self =>

  protected type BagBucketFactory[X] <: HashedBagBucketFactory[X, BagBucket[X]]
}
