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

  implicit def canBuildFrom[A](implicit bucketFactory: BagBucketFactory[A], equivClass: Equiv[A]): CanBuildFrom[Coll, A, immutable.Bag[A]] = bagCanBuildFrom[A](bucketFactory, equivClass)

  def newBuilder[A](implicit bucketFactory: Bag.BagBucketFactory[A], equivClass: Equiv[A]): mutable.BagBuilder[A, immutable.Bag[A]] = immutable.Bag.newBuilder[A](bucketFactory, equivClass)

  override def empty[A](implicit bucketFactory: Bag.BagBucketFactory[A], equivClass: Equiv[A]): immutable.Bag[A] = immutable.Bag.empty(bucketFactory, equivClass)

}

private[scala] abstract class AbstractBag[A] extends AbstractIterable[A] with Bag[A]

