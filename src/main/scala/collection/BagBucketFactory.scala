package scala.collection


trait BagBucketFactory[A, +BagBucket <: collection.BagBucket[A]] extends Equiv[A] {


  def empty(sentinel: A): BagBucket

  def from(elem: A): BagBucket

  def newBuilder(sentinel: A): mutable.BagBucketBuilder[A, BagBucket]

}
