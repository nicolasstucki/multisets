package scala.collection.immutable

import scala.collection.generic.Subtractable

import scala.collection._

trait BagLike[A, +This <: immutable.BagLike[A, This] with immutable.Bag[A]]
  extends collection.BagLike[A, This]
  with GenBagLike[A, This]
  with Subtractable[A, This] {
  self =>

  protected override type BagBucket = immutable.BagBucket[A]
  protected override type BagBucketFactory = immutable.BagBucketFactory[A]

}
