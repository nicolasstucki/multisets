package scala.collection.scalatest

import scala.collection.immutable._

import org.scalatest._

class Bag extends FlatSpec with Matchers {

  implicit val m = BagBucketFactory.ofMultiplicities[Char]

  "A Bag" should " initialise correctly" in {
    Bag.empty.toSeq should be(Seq.empty)

    Bag('a' -> 2).toSeq should be(Seq('a', 'a'))

    Bag('a' -> 2, 'b' -> 3).toSeq.sorted           should be(Seq('a', 'a', 'b', 'b', 'b'))
    Bag('a' -> 2, 'b' -> 3, 'c' -> 1).toSeq.sorted should be(Seq('a', 'a', 'b', 'b', 'b', 'c'))
    Bag('a' -> 2, 'a' -> 1, 'a' -> 1).toSeq.sorted should be(Seq('a', 'a', 'a', 'a'))
    Bag('a' -> 2, 'b' -> 3, 'b' -> 1).toSeq.sorted should be(Seq('a', 'a', 'b', 'b', 'b', 'b'))
  }

  "A Bag" should " implement basic operations + and - as multiset operations" in {
    val bag = Bag('a' -> 2, 'b' -> 3)

    bag + 'a' should be(Bag('a' -> 3, 'b' -> 3))
    bag + 'b' should be(Bag('a' -> 2, 'b' -> 4))
    bag + 'c' should be(Bag('a' -> 2, 'b' -> 3, 'c' -> 1))


    bag + ('a' -> 2) should be(Bag('a' -> 4, 'b' -> 3))
    bag + ('b' -> 1) should be(Bag('a' -> 2, 'b' -> 4))
    bag + ('c' -> 5) should be(Bag('a' -> 2, 'b' -> 3, 'c' -> 5))


    bag - 'a' should be(Bag('a' -> 1, 'b' -> 3))
    bag - 'b' should be(Bag('a' -> 2, 'b' -> 2))
    bag - 'c' should be(Bag('a' -> 2, 'b' -> 3))

    bag - 'a' - 'a' should be(Bag('a' -> 0, 'b' -> 3))
    bag - ('a' -> 2) should be(Bag('a' -> 0, 'b' -> 3))


  }

  "A Bag" should " have a consistent size" in {

    Bag.empty.size should be(0)

    Bag('a' -> 2).size should be(2)
    Bag('a' -> 2, 'b' -> 3).size should be(5)
    Bag('a' -> 2, 'b' -> 3, 'c' -> 1).size should be(6)
    Bag('a' -> 2, 'a' -> 1, 'a' -> 1).size should be(4)
    Bag('a' -> 2, 'b' -> 3, 'b' -> 3).size should be(8)
  }




}
