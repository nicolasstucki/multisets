package scala.collection.immutable

import scala.collection._
import scala.collection.generic._

trait Bag[A]
  extends collection.Bag[A]
  with immutable.BagLike[A, immutable.Bag[A]] {


}


object Bag extends generic.ImmutableBagFactory[Bag] {


  implicit def canBuildFrom[A](implicit bagBucketConfiguration: BBC[A]): CanBuildFrom[Coll, A, Bag[A]] = bagCanBuildFrom[A]

  def empty[A](implicit bagBucketConfiguration: BBC[A]): Bag[A] = ???

}