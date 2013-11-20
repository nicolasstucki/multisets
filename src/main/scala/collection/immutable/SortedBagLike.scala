package scala.collection.immutable


import scala.language.higherKinds
import scala.collection._

trait SortedBagLike[A, +This <: immutable.SortedBagLike[A, This] with immutable.SortedBag[A]]
  extends collection.immutable.BagLike[A, This]
  with collection.SortedBagLike[A, This] {
  self =>

  protected override type BagBucketFactory[X] = immutable.SortedBagBucketFactory[X]


}
