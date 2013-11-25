package scala.collection.scalameter.mutable.HashBag

import org.scalameter.api._

object HashBag_distinct extends HashBagBenchmark {

  def sizes = Gen.range("size")(5000, 50000, 5000)

  def funName: String = "distinct"

  def fun(bag: Bag[BigInt]): Unit = bag.distinct

  def listFun(list: List[BigInt]): Unit = list.distinct

  runBenchmark()

}
