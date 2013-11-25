package scala.collection.scalameter.mutable.HashBag

import scala.language.higherKinds

import org.scalameter.api._

object HashBag_foreach extends HashBagBenchmark {

  def sizes = Gen.range("size")(10000, 100000, 10000)

  def funName: String = "foreach"

  def fun(bag: Bag[BigInt]): Unit = bag.foreach(_ => Unit)

  def listFun(list: List[BigInt]): Unit = list.foreach(_ => Unit)

  runBenchmark()

}
