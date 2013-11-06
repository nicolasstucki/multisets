package scala.collection.mutable

import scala.collection._

trait BagLike[A, +This <: mutable.BagLike[A, This] with mutable.Bag[A]]
  extends collection.BagLike[A, This]
  with GenBagLike[A, This]
  with generic.Subtractable[A, This]
  with mutable.BagBuilder[A, This] {
  self =>

  override protected[this] def newBagBuilder: mutable.BagBuilder[A, This] = empty

}
