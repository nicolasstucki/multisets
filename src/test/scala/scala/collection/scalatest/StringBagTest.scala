package scala.collection.scalatest

import scala.collection.immutable._

import org.scalatest._
import scala.collection
import scala.util.hashing.Hashing

abstract class StringBagTest extends FlatSpec with StringBagBehaviours {

  object StrSize extends Ordering[String] with Hashing[String] {
    def hash(x: String): Int = x.size.hashCode()

    def compare(x: String, y: String): Int = x.size compare y.size
  }

  def emptyBag: collection.Bag[String]

  def bagWithCat = emptyBag + "Cat"

  def bagWithCatCatCat = emptyBag + ("Cat" -> 3)

  def bagWithCatDogMouse = emptyBag + "Cat" + "Dog" + "Mouse"

  def bagWithCatCatDogMouseMouseMouse = emptyBag + ("Cat" -> 2) + ("Dog" -> 1) + ("Mouse" -> 3)

  def bags = Seq(bagWithCat, bagWithCatCatCat, bagWithCatDogMouse, bagWithCatCatDogMouseMouseMouse)


  "An empty Bag" should behave like emptyBagBehaviour(emptyBag, bags)
  it should behave like stringBagBehaviour(emptyBag)

  """Bag {"Cat"}""" should """have the same content as List("Cat")""" in {
    assert(bagWithCat.iterator.toList.sorted == List("Cat"))
  }
  it should """give coherent multiplicities""" in {
    assertResult(1) {
      bagWithCat multiplicity "Cat"
    }
    assertResult(0) {
      bagWithCat multiplicity "Dog"
    }
    assertResult(0) {
      bagWithCat multiplicity "Mouse"
    }
    assertResult(0) {
      bagWithCat multiplicity "Fish"
    }
  }
  it should behave like nonEmptyBagBehaviour(bagWithCat, bags)
  it should behave like stringBagBehaviour(bagWithCat)


  """Bag {"Cat","Cat","Cat"}""" should """have the same content as List("Cat","Cat","Cat")""" in {
    assert(bagWithCatCatCat.iterator.toList.sorted == List("Cat", "Cat", "Cat"))
  }
  it should """give coherent multiplicities""" in {
    assertResult(3) {
      bagWithCatCatCat multiplicity "Cat"
    }
    assertResult(0) {
      bagWithCatCatCat multiplicity "Dog"
    }
    assertResult(0) {
      bagWithCatCatCat multiplicity "Mouse"
    }
    assertResult(0) {
      bagWithCatCatCat multiplicity "Fish"
    }
  }
  it should behave like nonEmptyBagBehaviour(bagWithCatCatCat, bags)
  it should behave like stringBagBehaviour(bagWithCatCatCat)


  """Bag {"Cat","Dog","Mouse"}""" should """have the same content as List("Cat","Dog","Mouse")""" in {
    assert(bagWithCatDogMouse.iterator.toList.sorted == List("Cat", "Dog", "Mouse"))
  }
  it should """give coherent multiplicities""" in {
    assertResult(1) {
      bagWithCatDogMouse multiplicity "Cat"
    }
    assertResult(1) {
      bagWithCatDogMouse multiplicity "Dog"
    }
    assertResult(1) {
      bagWithCatDogMouse multiplicity "Mouse"
    }
    assertResult(0) {
      bagWithCatDogMouse multiplicity "Fish"
    }
  }
  it should behave like nonEmptyBagBehaviour(bagWithCatDogMouse, bags)
  it should behave like stringBagBehaviour(bagWithCatDogMouse)


  """Bag {"Cat","Cat","Dog","Mouse","Mouse","Mouse"}""" should """have the same content as List("Cat","Cat","Dog","Mouse","Mouse","Mouse")""" in {
    assert(bagWithCatCatDogMouseMouseMouse.iterator.toList.sorted == List("Cat", "Cat", "Dog", "Mouse", "Mouse", "Mouse"))
  }
  it should """give coherent multiplicities""" in {
    assertResult(2) {
      bagWithCatCatDogMouseMouseMouse multiplicity "Cat"
    }
    assertResult(1) {
      bagWithCatCatDogMouseMouseMouse multiplicity "Dog"
    }
    assertResult(3) {
      bagWithCatCatDogMouseMouseMouse multiplicity "Mouse"
    }
    assertResult(0) {
      bagWithCatCatDogMouseMouseMouse multiplicity "Fish"
    }
  }
  it should behave like nonEmptyBagBehaviour(bagWithCatCatDogMouseMouseMouse, bags)
  it should behave like stringBagBehaviour(bagWithCatCatDogMouseMouseMouse)


}
