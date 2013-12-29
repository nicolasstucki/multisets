package scala.collection
package generic

import scala.language.higherKinds
import scala.collection.mutable

abstract class SortedBagFactory[CC[X] <: Bag[X] with BagLike[X, CC[X]]]
  extends BagFactory[CC] {

  type BagBucketFactory[X] <: collection.SortedBagBucketFactory[X, BagBucket[X]]

}
