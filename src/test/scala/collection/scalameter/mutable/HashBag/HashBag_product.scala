package scala.collection.scalameter.mutable.HashBag

import scala.language.higherKinds

import org.scalameter.api._

object HashBag_product extends HashBagBenchmark {

  def sizes = Gen.range("size")(200, 2000, 200)

  def funName: String = "product"

  def fun(bag: Bag[BigInt]): Unit = bag.product

  def listFun(list: List[BigInt]): Unit = list.product

  runBenchmark()

}
