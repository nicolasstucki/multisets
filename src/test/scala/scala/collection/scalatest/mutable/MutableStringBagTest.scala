package scala.collection.scalatest.mutable

import scala.collection.scalatest.StringBagTest

/**
  * @author Tom Warnke
  */
trait MutableStringBagTest extends StringBagTest with MutableBagBehaviours {

  override def emptyBag: collection.mutable.Bag[String]

  override def bagWithCatCatDogMouseMouseMouse =
    emptyBag + ("Cat" -> 2) + ("Dog" -> 1) + ("Mouse" -> 3)

}
