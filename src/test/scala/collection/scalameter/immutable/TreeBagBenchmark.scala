package scala.collection.scalameter.immutable

import scala.language.higherKinds

import org.scalameter.api._
import scala.collection._

trait TreeBagBenchmark extends BigIntImmutableBagBenchmark {

  type Bag[X] = immutable.TreeBag[X]

  def newBuilder(implicit m: immutable.SortedBagBucketFactory[BigInt]) = immutable.TreeBag.newBuilder[BigInt](m)

  def bagName: String = "immutable.TreeBag"

}
