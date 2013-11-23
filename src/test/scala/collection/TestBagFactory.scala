package scala.collection

object TestBagFactory {


  def squareBag[A, Bag <: collection.Bag[A]](size: Int, builder: mutable.BagBuilder[A, Bag], tab: Int => A): Bag = {
    val sqrt = Math.sqrt(size).toInt

    for (n <- 1 to sqrt) {
      builder.add(tab(n), sqrt)
    }
    builder.add(tab(sqrt + 1), size - sqrt * sqrt)

    builder.result()
  }

  def stepsBag[A, Bag <: collection.Bag[A]](size: Int, builder: mutable.BagBuilder[A, Bag], tab: Int => A): Bag = {
    var i = 1
    var n = 0
    while (n < size) {
      builder.add(tab(i), Math.min(i, size - n))
      n += i
      i += 1
    }
    builder.result()
  }

  def setBag[A, Bag <: collection.Bag[A]](size: Int, builder: mutable.BagBuilder[A, Bag], tab: Int => A): Bag = {
    for (n <- 1 to size) {
      builder += tab(n)
    }
    builder.result()
  }


}
