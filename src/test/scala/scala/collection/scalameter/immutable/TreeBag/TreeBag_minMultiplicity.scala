package scala.collection.scalameter.immutable.TreeBag

import org.scalameter.api._

object TreeBag_minMultiplicity extends TreeBagBenchmark {

  def sizes = Gen.range("size")(5000, 50000, 5000)

  def funName: String = "maxMultiplicity"

  def fun(bag: TreeBag_product.Bag[BigInt]): Unit = bag.minMultiplicity


  override val compareWithLists: Boolean = false

  def listFun(list: List[BigInt]): Unit = Unit

  runBenchmark()
}
