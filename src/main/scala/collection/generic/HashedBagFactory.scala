package scala.collection.generic

import scala.language.higherKinds

import scala.collection._

abstract class HashedBagFactory[CC[X] <: Bag[X] with BagLike[X, CC[X]]]
  extends BagFactory[CC] {

  type BagBucketFactory[X] <: collection.HashedBagBucketFactory[X, BagBucket[X]]

}
