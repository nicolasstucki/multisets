package scala.collection.scalameter.immutable.TreeBag

import org.scalameter.api._

object TreeBag_exists extends TreeBagBenchmark {

  def sizes = Gen.range("size")(20000, 200000, 20000)

  def funName: String = "exists{result:=false}"

  def fun(bag: TreeBag_product.Bag[BigInt]): Unit = bag.exists(_ => false)

  def listFun(list: List[BigInt]): Unit = list.exists(_ => false)

  runBenchmark()
}
