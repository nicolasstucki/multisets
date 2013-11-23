package scala.collection.mutable

import scala.language.higherKinds
import scala.collection._

trait HashedBagLike[A, +This <: mutable.HashedBagLike[A, This] with mutable.HashedBag[A]]
  extends collection.mutable.BagLike[A, This]
  with collection.HashedBagLike[A, This] {
  self =>

  protected override type BagBucketFactory[X] = mutable.HashedBagBucketFactory[X]


}
