package scala.collection.scalatest

import scala.collection.immutable._

import org.scalatest._

class Bag extends FlatSpec with Matchers {

  implicit val m = MultiplicityBagBucketFactory.of[Char]

  "A Bag" should " implement basic operations + and - as multiset operations" in {
    val bag = Bag('a' -> 2, 'b' -> 3)

    bag + 'a' should be(Bag('a' -> 3, 'b' -> 3))
    bag + 'b' should be(Bag('a' -> 2, 'b' -> 4))
    bag + 'c' should be(Bag('a' -> 2, 'b' -> 3, 'c' -> 1))

    bag - 'a' should be(Bag('b' -> 3))
    bag - 'b' should be(Bag('a' -> 1, 'b' -> 4))
    bag - 'c' should be(Bag('a' -> 2, 'b' -> 3))



  }




}
