package scala.collection.mutable

import scala.collection._
import scala.language.higherKinds

trait BagLike[A, +This <: mutable.Bag[A] with mutable.BagLike[A, This]]
  extends collection.BagLike[A, This]
  with generic.Subtractable[A, This]
  with generic.GrowableBag[A] {
  self =>

  final protected override type BagBucket = mutable.BagBucket[A]

  override protected[this] def newBuilder: mutable.BagBuilder[A, This] = new mutable.GrowingBagBuilder[A, This](empty)


}
