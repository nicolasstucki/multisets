package scala.collection.mutable

trait BagBucket[A] extends scala.collection.BagBucket[A] {

  def +=(elem: A): this.type

  def -=(elem: A): this.type

}





