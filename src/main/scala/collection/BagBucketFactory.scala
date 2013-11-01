package scala.collection


trait BagBucketFactory[A, BagBucket <: collection.BagBucket[A]] {

  def empty (sentinel: A): BagBucket

  def from(elem: A): BagBucket

  def from(elem: A, multiplicity: Int): BagBucket

  def from(bucket: collection.BagBucket[A]): BagBucket

}
