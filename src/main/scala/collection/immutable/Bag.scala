package scala.collection.immutable

import scala.collection._

trait Bag[A]
  extends collection.Bag[A]
  with immutable.BagLike[A, Bag[A]] {

  protected override type BagBucket = immutable.BagBucket[A]
  protected override type BagBucketFactory = immutable.BagBucketFactory[A]

}


object Bag {

  def empty[A](implicit bktFactory: immutable.BagBucketFactory[A]): immutable.Bag[A] = {
    immutable.DummyMapBag.empty(bktFactory)
  }

  def apply[A](elem: (A, Int))(implicit bktFactory: immutable.BagBucketFactory[A]): immutable.Bag[A] = {
    immutable.DummyMapBag(elem)(bktFactory)
  }

  def apply[A](elem1: (A, Int), elem2: (A, Int), elems: (A, Int)*)(implicit bktFactory: immutable.BagBucketFactory[A]): immutable.Bag[A] = {
    immutable.DummyMapBag(elem1, elem2, elems: _*)(bktFactory)
  }

  def apply[A](elems: GenTraversable[A])(implicit bktFactory: immutable.BagBucketFactory[A]): immutable.Bag[A] = {
    immutable.DummyMapBag(elems)(bktFactory)
  }

}