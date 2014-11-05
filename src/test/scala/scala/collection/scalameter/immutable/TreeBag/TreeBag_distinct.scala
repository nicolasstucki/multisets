package scala.collection.scalameter.immutable.TreeBag

import org.scalameter.api._

object TreeBag_distinct extends TreeBagBenchmark {

  def sizes = Gen.range("size")(5000, 50000, 5000)

  def funName: String = "distinct"

  def fun(bag: TreeBag_product.Bag[BigInt]): Unit = bag.distinct

  def listFun(list: List[BigInt]): Unit = list.distinct

  runBenchmark()

}
