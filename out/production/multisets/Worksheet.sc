import scala.collection._

val ms0 = Bag[String]()
val ms1 = Bag("a" -> 2, "c" -> 2)
val ms2 = Bag("a" -> 2, "b" -> 3, "c" -> 1)
val ms3 = Bag("a" -> 2, "c" -> 1)
val ms4 = Bag("frog" -> 2, "cat" -> 2, "dog" -> 5, "mouse" -> 3, "fish" -> 5)




ms2("a")
ms2("b")
ms0 count "b"
ms1 count "b"
ms2 count "b"
ms4 count "cat"
ms4 count "dog"
ms4 count (_.length == 3)
ms4 count (_ contains 'o')
ms4 filter (_.length == 3)
ms1 intersect ms2
ms2.distinct
ms1 contains "a"
ms1 contains "x"
ms3 subsetOf ms2
ms2 subsetOf ms3
ms2 + "x"
ms1 + "a" + "t"
ms1 + ("x" -> 3)
ms0 ++ Seq("a", "a", "a", "b", "c")
ms1 ++ ms2



ms2 removedAll "b"
ms4.mostCommon()




ms4.mostCommon(_.length >= 4)



ms4.mostCommon(_.length < 4)
ms4.mostCommon(_.length > 10)
ms1
ms1 - "a"
ms1 - "x"
ms2 - ("b" -> 2)
ms1 -- Seq("a", "c")

ms2 removedAll "b"
ms2 -* "b"

val mms = mutable.Bag[String]()
mms += "a"
mms += "a"
mms += "b" -> 3
mms
mms("b") = 2
mms("c") = 4
mms





mms ++ ms3



ms4 partition (_.size < 4)




val ms5 = Bag(2 -> 3, 3 -> 2, 7 -> 1)
ms5.countsIterator.toList
ms5.fold(0)(_ + _, _ * _)




ms5.reduce(_ + _, _ * _)


def power(a: Int, b: Int): Int = {
  if (b == 0) 1
  else if (b % 2 == 1) a * power(a, b - 1)
  else {
    val c = power(a, b / 2)
    c * c
  }
}
ms5.fold(1)(_ * _, power)




ms5.reduce(_ * _, power)


ms5 forall ((elem: Int, count: Int) => elem == count)
ms5 exists ((elem: Int, count: Int) => elem < count)
val z = ms5.zipWithCount
z.toList


