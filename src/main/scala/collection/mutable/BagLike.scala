package scala.collection.mutable

import scala.collection._

trait BagLike[A, +This <: mutable.BagLike[A, This] with mutable.Bag[A]]
  extends collection.BagLike[A, This]
  with GenBagLike[A, This]
  with generic.Subtractable[A, This]
  with mutable.BagBuilder[A, This] {
  self =>

  protected override type BagBucket = mutable.BagBucket[A]
  protected override type BagBucketFactory = mutable.BagBucketFactory[A]


  override protected[this] def newBagBuilder: mutable.BagBuilder[A, This] = empty

}
