package scala.collection.mutable

import scala.collection._

trait BagLike[A, This <: mutable.BagLike[A, This] with mutable.Bag[A]]
  extends collection.BagLike[A, This]
  with GenBagLike[A, This]
  with generic.Subtractable[A, This]
  with mutable.BagBuilder[A, This] {
  self =>

  protected override type BagBucket[X] = mutable.BagBucket[X]
  protected override type BagBucketFactory[X] = mutable.BagBucketFactory[X]


  override protected[this] def newBagBuilder: mutable.BagBuilder[A, This] = empty

}
