package scala.collection.scalameter.immutable.TreeBag

import scala.language.higherKinds

import scala.collection._

trait TreeBagBenchmark extends scala.collection.scalameter.immutable.BigIntSortedBagBenchmark {

  type Bag[X] = immutable.TreeBag[X]

  def newBuilder(implicit m: BagConfiguration[BigInt]) = immutable.TreeBag.newBuilder[BigInt](m)

  def bagName: String = "immutable.TreeBag"

}
