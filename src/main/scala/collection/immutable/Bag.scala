package scala.collection.immutable

import scala.collection._
import scala.collection.generic._

trait Bag[A]
  extends collection.Bag[A]
  with immutable.BagLike[A, immutable.Bag[A]] {


}


object Bag extends generic.ImmutableBagFactory[Bag] {

  implicit def canBuildFrom[A](implicit bucketFactory: BagBucketFactory[A], equivClass: Equiv[A]): CanBuildFrom[Coll, A, Bag[A]] = bagCanBuildFrom[A](bucketFactory, equivClass)

  def empty[A](implicit bucketFactory: Bag.BagBucketFactory[A], equivClass: Equiv[A]): Bag[A] = immutable.VectorBag.empty(bucketFactory, equivClass)

}