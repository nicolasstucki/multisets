package scala.collection.scalameter.immutable.TreeBag

import org.scalameter.api._

object TreeBag_forall extends TreeBagBenchmark {

  def sizes = Gen.range("size")(20000, 200000, 20000)

  def funName: String = "forall{result:=true}"

  def fun(bag: TreeBag_product.Bag[BigInt]): Unit = bag.forall(_ => true)

  def listFun(list: List[BigInt]): Unit = list.forall(_ => true)

  runBenchmark()
}
