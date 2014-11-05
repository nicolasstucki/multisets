package scala.collection.scalameter.mutable.HashBag

import scala.language.higherKinds

import scala.collection._

trait HashBagBenchmark extends scala.collection.scalameter.mutable.BigIntHashedBagBenchmark {

  type Bag[X] = mutable.HashBag[X]

  def newBuilder(implicit m: BagConfiguration[BigInt]) = mutable.HashBag.newBuilder[BigInt](m)

  def bagName: String = "mutable.HashBag"

}
