package scala.collection


trait BagBucketFactory[A, B <: BagBucket[A]] {

  def empty(sentinel: A): B

  def apply(elem: A): B

  def apply(elemCount: (A, Int)): B

}
