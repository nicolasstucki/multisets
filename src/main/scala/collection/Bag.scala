package scala
package collection


import generic._


trait Bag[A]
  extends Iterable[A]
  with BagLike[A, Bag[A]]
  with GenBag[A] {


  override def stringPrefix: String = "Bag"

}


object Bag extends BagFactory[immutable.Bag] {

  type BB[A] = immutable.BagBucket[A]
  type BBC[A] = immutable.BagBucketConfiguration[A]

  implicit def canBuildFrom[A](implicit bagBucketConfiguration: BBC[A]): CanBuildFrom[Coll, A, immutable.Bag[A]] = bagCanBuildFrom[A]

  def newBuilder[A](implicit bagBucketConfiguration: BBC[A]): mutable.BagBuilder[A, immutable.Bag[A]] = immutable.Bag.newBuilder[A]

  override def empty[A](implicit bagBucketConfiguration: BBC[A]): immutable.Bag[A] = immutable.Bag.empty[A]

}

private[scala] abstract class AbstractBag[A] extends AbstractIterable[A] with Bag[A]

