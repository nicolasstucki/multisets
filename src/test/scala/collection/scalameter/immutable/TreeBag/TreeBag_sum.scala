package scala.collection.scalameter.immutable.TreeBag

import scala.language.higherKinds

import org.scalameter.api._

object TreeBag_sum extends TreeBagBenchmark {

  def sizes = Gen.range("size")(10000, 100000, 10000)

  def funName: String = "sum"

  def fun(bag: TreeBag_sum.Bag[BigInt]): Unit = bag.sum

  def listFun(list: List[BigInt]): Unit = list.sum

  runBenchmark()

}