package scala.collection.mutable

import scala.collection.{mutable, Multiplicities, Group}

class MapBag[A, G <: Group[A, G]](multiplicityMap: mutable.Map[A, G])(implicit protected val m: Multiplicities[A, G]) extends mutable.Bag[A, G] {

  def empty: mutable.Bag[A, G] = new mutable.MapBag(mutable.Map.empty[A, G])(m)

  // Added elements
  def +(elem: A): mutable.Bag[A, G] = {
    val newMap = multiplicityMap.clone()
    newMap(elem) = newMap.get(elem) match {
      case Some(group) => group + elem
      case None => m(elem)
    }
    new mutable.MapBag(newMap)(m)
  }

  // Removed elements
  def -(elem: A): mutable.Bag[A, G] = {
    val newMap = multiplicityMap.clone()
    newMap(elem) = newMap.getOrElse(elem, m.empty(elem)) - elem
    new mutable.MapBag(newMap)(m)
  }

  def iterator2: Iterator[Iterator[A]] = multiplicityMap.keysIterator map elemIterator

  private def elemIterator(elem: A): Iterator[A] = multiplicityMap(elem).iterator

  def update(elem: A, count: Int): Unit = multiplicityMap.update(elem, multiplicityMap.getOrElse(elem, m.empty(elem)) + elem)

  override def multiplicity(elem: A): Int = multiplicityMap.get(elem) match {
    case Some(group) => group.multiplicity
    case None => 0
  }

  override def distinctIterator = multiplicityMap.keysIterator

  override def countsIterator = multiplicityMap.iterator map {
    case (elem, group) => elem -> group.multiplicity
  }

  override def iterator = multiplicityMap.valuesIterator.flatMap(_.iterator)

}


object MapBag {

  def apply[A, G <: Group[A, G]](implicit m: Multiplicities[A, G]): mutable.Bag[A, G] = new mutable.MapBag[A, G](mutable.Map.empty[A, G])(m)

  def apply[A, G <: Group[A, G]](elemCount: (A, Int))(implicit m: Multiplicities[A, G]): mutable.Bag[A, G] = new mutable.MapBag[A, G](mutable.Map[A, G](elemCount._1 -> m(elemCount)))

  def apply[A, G <: Group[A, G]](elem1: (A, Int), elem2: (A, Int), elems: (A, Int)*)(implicit m: Multiplicities[A, G]): mutable.Bag[A, G] = {
    new mutable.MapBag[A, G](mutable.Map[A, G]() + (elem1._1 -> m(elem1)) + (elem2._1 -> m(elem2)) ++ elems.map((elemCount: (A, Int)) => elemCount._1 -> m(elemCount)))
  }

  def apply[A, G <: Group[A, G]](elems: scala.collection.Iterable[A])(implicit m: Multiplicities[A, G]): mutable.Bag[A, G] = new mutable.MapBag[A, G](mutable.Map() ++ (elems map (elem => elem -> m(elem -> elems.count(elem == _)))))
}