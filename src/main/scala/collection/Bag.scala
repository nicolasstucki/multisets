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

  type BagBucket[A] = immutable.BagBucket[A]
  type BagBucketFactory[A] = immutable.BagBucketFactory[A]

  implicit def canBuildFrom[A](implicit bucketFactory: BagBucketFactory[A]): CanBuildFrom[Coll, A, immutable.Bag[A]] = bagCanBuildFrom[A]

  def newBuilder[A](implicit bucketFactory: Bag.BagBucketFactory[A]): mutable.BagBuilder[A, immutable.Bag[A]] = immutable.Bag.newBuilder[A]

  override def empty[A](implicit bucketFactory: Bag.BagBucketFactory[A]): immutable.Bag[A] = immutable.Bag.empty[A]

}

private[scala] abstract class AbstractBag[A] extends AbstractIterable[A] with Bag[A]

