package scala.collection
package immutable


class MapBag[A](map: Map[A, Int]) extends Bag[A] {

  override def iterator2: Iterator[Iterator[A]] = map.keysIterator map elemIterator

  private def elemIterator(elem: A): Iterator[A] = for (_ <- (1 to map.getOrElse(elem, 0)).iterator) yield elem

}


object MapBag {

  def apply[T](): Bag[T] = new MapBag[T](Map.empty[T, Int])

  def apply[T](elem: (T, Int)): Bag[T] = new MapBag[T](Map(elem))

  def apply[T](elem1: (T, Int), elem2: (T, Int), elems: (T, Int)*): Bag[T] = new MapBag[T](Map() + elem1 + elem2 ++ elems)

  def apply[T](elems: scala.collection.Iterable[T]): Bag[T] = new MapBag[T](Map() ++ (elems map (elem => (elem, elems.count(elem == _)))))
}