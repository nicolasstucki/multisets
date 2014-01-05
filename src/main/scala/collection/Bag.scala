package scala
package collection


import generic._


trait Bag[A]
  extends Iterable[A]
  with BagLike[A, Bag[A]]
  with GenBag[A] {


  override def stringPrefix: String = "Bag"

  override def toString() = bucketsIterator.map(_.mkString(", ")).mkString(stringPrefix + "(", "; ", ")")

}


object Bag extends generic.ImmutableHashedBagFactory[immutable.Bag] {

  implicit def canBuildFrom[A](implicit bagConfiguration: immutable.HashedBagConfiguration[A]): CanBuildFrom[Coll, A, immutable.Bag[A]] = bagCanBuildFrom[A]

  def empty[A](implicit bagConfiguration: immutable.HashedBagConfiguration[A]): immutable.Bag[A] = immutable.Bag.empty[A]
}

private[scala] abstract class AbstractBag[A] extends AbstractIterable[A] with Bag[A]

