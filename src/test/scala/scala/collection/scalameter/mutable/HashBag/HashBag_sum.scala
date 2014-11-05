package scala.collection.scalameter.mutable.HashBag

import scala.language.higherKinds

import org.scalameter.api._

object HashBag_sum extends HashBagBenchmark {

  def sizes = Gen.range("size")(10000, 100000, 10000)

  def funName: String = "sum"

  def fun(bag: Bag[BigInt]): Unit = bag.sum

  def listFun(list: List[BigInt]): Unit = list.sum

  runBenchmark()

}