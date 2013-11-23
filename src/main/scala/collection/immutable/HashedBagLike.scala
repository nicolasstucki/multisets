package scala.collection.immutable


import scala.language.higherKinds
import scala.collection._

trait HashedBagLike[A, +This <: immutable.HashedBagLike[A, This] with immutable.HashedBag[A]]
  extends collection.immutable.BagLike[A, This]
  with collection.HashedBagLike[A, This] {
  self =>

  protected override type BagBucketFactory[X] = immutable.HashedBagBucketFactory[X]


}
