/**
  * Created by Gerard on 10/05/2016.
  */


import scala.collection.mutable




object QuickTest extends App {
  implicit val ttbConfig = mutable.SortedBagConfiguration.keepAll[Int]
  val ttb = mutable.TreeBag.empty[Int]
  ttb += 2 -> 3
  ttb += 2 -> 78
  println(ttb.bucketsIterator.toList)
  println(ttb)
}
