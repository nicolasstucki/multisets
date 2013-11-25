package scala.collection.scalameter.mutable.HashBag

import org.scalameter.api._

object HashBag_forall extends HashBagBenchmark {

  def sizes = Gen.range("size")(20000, 200000, 20000)

  def funName: String = "forall{result:=true}"

  def fun(bag: Bag[BigInt]): Unit = bag.forall(_ => true)

  def listFun(list: List[BigInt]): Unit = list.forall(_ => true)

  runBenchmark()
}
