package scala.collection.generic

trait GrowableBag[A] extends Growable[A] {

  def +=(elem: A): this.type = add(elem, 1)

  @inline
  def +=(elemCount: (A, Int)): this.type = add(elemCount._1, elemCount._2)

  def add(elem: A, count: Int): this.type

  def addBucket(bucket: scala.collection.BagBucket[A]): this.type

}
