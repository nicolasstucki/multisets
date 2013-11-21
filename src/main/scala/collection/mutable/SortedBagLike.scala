package scala.collection.mutable

import scala.language.higherKinds
import scala.collection._

trait SortedBagLike[A, +This <: mutable.SortedBagLike[A, This] with mutable.SortedBag[A]]
  extends collection.mutable.BagLike[A, This]
  with collection.SortedBagLike[A, This] {
  self =>

  protected override type BagBucketFactory[X] = mutable.SortedBagBucketFactory[X]


}
