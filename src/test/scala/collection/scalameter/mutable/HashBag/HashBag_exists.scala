package scala.collection.scalameter.mutable.HashBag

import org.scalameter.api._

object HashBag_exists extends HashBagBenchmark {

  def sizes = Gen.range("size")(20000, 200000, 20000)

  def funName: String = "exists{result:=false}"

  def fun(bag: Bag[BigInt]): Unit = bag.exists(_ => false)

  def listFun(list: List[BigInt]): Unit = list.exists(_ => false)

  runBenchmark()
}
