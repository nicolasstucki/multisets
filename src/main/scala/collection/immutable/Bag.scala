package scala.collection.immutable


trait Bag[A] extends scala.collection.Bag[A] {


}


object Bag {

  def apply[T](): Bag[T] = MapBag()

  def apply[T](elem: (T, Int)): Bag[T] = MapBag(elem)

  def apply[T](elem1: (T, Int), elem2: (T, Int), elems: (T, Int)*): Bag[T] = MapBag(elem1, elem2, elems: _*)

  def apply[T](elems: scala.collection.Iterable[T]): Bag[T] = MapBag(elems)

}