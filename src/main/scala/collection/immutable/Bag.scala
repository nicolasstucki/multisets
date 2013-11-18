package scala.collection.immutable

import scala.collection._
import scala.collection.generic._

trait Bag[A]
  extends collection.Bag[A]
  with immutable.BagLike[A, immutable.Bag[A]] {


}


object Bag extends generic.ImmutableBagFactory[Bag] {

  implicit def canBuildFrom[A](implicit b: BagBucketFactory[A]): CanBuildFrom[Coll, A, Bag[A]] = bagCanBuildFrom[A]

  override def empty[A](implicit bucketFactory: Bag.BagBucketFactory[A]): Bag[A] = immutable.VectorBag.empty

}