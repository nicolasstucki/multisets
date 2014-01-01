package scala.collection.immutable

import scala.collection._
import scala.collection.generic._

trait Bag[A]
  extends collection.Bag[A]
  with immutable.BagLike[A, immutable.Bag[A]] {


}


object Bag extends generic.ImmutableHashedBagFactory[immutable.Bag] {

  implicit def canBuildFrom[A](implicit bagConfiguration: HashedBagConfiguration[A]): CanBuildFrom[Coll, A, Bag[A]] = bagCanBuildFrom[A]

  def empty[A](implicit bagConfiguration: HashedBagConfiguration[A]): Bag[A] = immutable.HashBag.empty[A]
}