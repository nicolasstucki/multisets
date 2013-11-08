package scala.collection


trait BagBucketFactory[A, +BagBucket <: collection.BagBucket[A]] {

  def empty(sentinel: A): BagBucket

  def newBuilder(sentinel: A): mutable.BagBucketBuilder[A, BagBucket]
}
