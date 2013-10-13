package scala.collection


class Multiplicities[A](bag: Bag[A]) extends Map[A, Int] {

  def get(key: A): Option[Int] = Some(bag(key).multiplicity)

  def iterator: Iterator[(A, Int)] = bag.bucketsIterator map (g => (g.sentinel, g.multiplicity))

  def +[B1 >: Int](kv: (A, B1)): Map[A, B1] = kv match {
    case (k, v: Int) => new Multiplicities(bag + (k -> v))
    case (k, v) => ??? //new Multiplicities(bag.setMultiplicity(kv._1, kv._2))
  }


  def -(key: A): Multiplicities[A] = new Multiplicities(bag -* key)

}
