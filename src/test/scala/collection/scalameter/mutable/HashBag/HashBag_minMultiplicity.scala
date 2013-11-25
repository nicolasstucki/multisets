package scala.collection.scalameter.mutable.HashBag

import org.scalameter.api._

object HashBag_minMultiplicity extends HashBagBenchmark {

  def sizes = Gen.range("size")(5000, 50000, 5000)

  def funName: String = "maxMultiplicity"

  def fun(bag: Bag[BigInt]): Unit = bag.minMultiplicity


  override val compareWithLists: Boolean = false

  def listFun(list: List[BigInt]): Unit = Unit

  runBenchmark()
}
