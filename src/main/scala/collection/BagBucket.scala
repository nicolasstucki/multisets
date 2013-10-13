package scala.collection


object BagBucket {

  def unapply[A](bkt: BagBucket[A]): Option[(A, Int)] = Some((bkt.sentinel, bkt.multiplicity))

}


trait BagBucket[A]
  extends Iterable[A] {

  assert(multiplicity >= 0)

  def sentinel: A

  def multiplicity: Int

}





