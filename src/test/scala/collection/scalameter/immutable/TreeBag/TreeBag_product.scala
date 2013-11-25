package scala.collection.scalameter.immutable.TreeBag

import scala.language.higherKinds

import org.scalameter.api._

object TreeBag_product extends TreeBagBenchmark {

  def sizes = Gen.range("size")(200, 2000, 200)

  def funName: String = "product"

  def fun(bag: TreeBag_product.Bag[BigInt]): Unit = bag.product

  def listFun(list: List[BigInt]): Unit = list.product

  runBenchmark()

}
