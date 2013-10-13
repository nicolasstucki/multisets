package scala
package collection


trait Bag[A]
  extends (A => BagBucket[A])
  with Iterable[A]
  with BagLike[A, Bag[A]]
  with GenBag[A] {


  override def stringPrefix: String = "Bag"

}


object Bag {

  def empty[A](implicit bktFactory: immutable.BagBucketFactory[A]): immutable.Bag[A] = immutable.Bag.empty(bktFactory)

  def apply[A](elem: (A, Int))(implicit bktFactory: immutable.BagBucketFactory[A]): immutable.Bag[A] = immutable.Bag(elem)(bktFactory)

  def apply[A](elem1: (A, Int), elem2: (A, Int), elems: (A, Int)*)(implicit bktFactory: immutable.BagBucketFactory[A]): immutable.Bag[A] = immutable.Bag(elem1, elem2, elems: _*)(bktFactory)

  def apply[A](elems: GenTraversable[A])(implicit bktFactory: immutable.BagBucketFactory[A]): immutable.Bag[A] = immutable.Bag(elems)(bktFactory)

}


