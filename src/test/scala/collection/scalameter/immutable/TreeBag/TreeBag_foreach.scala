package scala.collection.scalameter.immutable.TreeBag

import scala.language.higherKinds

import org.scalameter.api._

object TreeBag_foreach extends TreeBagBenchmark {

  def sizes = Gen.range("size")(10000, 100000, 10000)

  def funName: String = "foreach"

  def fun(bag: TreeBag_product.Bag[BigInt]): Unit = bag.foreach(_ => Unit)

  def listFun(list: List[BigInt]): Unit = list.foreach(_ => Unit)

  runBenchmark()

}
