package scala
package collection


import generic._


trait Bag[A]
  extends (A => Int)
  with Iterable[A]
  with BagLike[A, Bag[A]]
  with GenBag[A] {


  override def stringPrefix: String = "Bag"

}


object Bag extends BagFactory[immutable.Bag] {

  type BagBucket[A] = immutable.BagBucket[A]
  type BagBucketFactory[A] = immutable.BagBucketFactory[A]

  def defaultBagBucketFactory[A]: Bag.BagBucketFactory[A] = immutable.BagBucketFactory.ofMultiplicities[A]

  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, immutable.Bag[A]] = bagCanBuildFrom[A](defaultBagBucketFactory)

  def newBuilder[A](implicit bucketFactory: Bag.BagBucketFactory[A]): mutable.BagBuilder[A, immutable.Bag[A]] = immutable.Bag.newBuilder[A](bucketFactory)

  override def empty[A](implicit bucketFactory: Bag.BagBucketFactory[A]): immutable.Bag[A] = immutable.Bag.empty(bucketFactory)

}

private[scala] abstract class AbstractBag[A] extends AbstractIterable[A] with Bag[A]

