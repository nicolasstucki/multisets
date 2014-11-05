
package scala.collection
package generic

import scala.language.higherKinds

abstract class BagFactory[CC[X] <: Bag[X] with BagLike[X, CC[X]], BB[X] <: scala.collection.BagBucket[X], BC[X] <: scala.collection.BagConfiguration[X, BB[X]]]
  extends GenBagFactory[CC, BB, BC] with GenericBagCompanion[CC, BB, BC]

