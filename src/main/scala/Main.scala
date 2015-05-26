import scala.collection.immutable

import immutable.Bag
import scala.util.hashing.Hashing

/**
 * Created by nicolasstucki on 25/05/15.
 */
object Main {

    def main (args: Array[String]) {
        object StrSize extends Ordering[String] with Hashing[String] {
            def hash(x: String): Int = x.size
            def compare(x: String, y: String): Int = x.size compare y.size
        }

        implicit def bagConfiguration = immutable.HashBag.configuration.compactWithEquiv(StrSize)

        val bag = Bag("Cat", "Dog")
        val bag2 = Bag("Cat")
        val union = bag union bag2

        println(union)

        println(union.multiplicity("Cat"))

    }

}
