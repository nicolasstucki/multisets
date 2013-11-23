package scala.collection.generic

import scala.language.higherKinds

import scala.collection._

abstract class HashedBagFactory[CC[X] <: HashedBag[X] with HashedBagLike[X, CC[X]]]
  extends BagFactory[CC] {

  type BagBucketFactory[X] <: collection.HashedBagBucketFactory[X, BagBucket[X]]

}
