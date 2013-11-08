package scala
package collection
package generic

import scala.language.higherKinds

abstract class BagFactory[CC[X] <: Bag[X] with BagLike[X, CC[X]]]
  extends GenBagFactory[CC] with GenericBagCompanion[CC]
