Multisets API for Scala collections (`Bag` trait)
=========

Multisets API for Scala is a new kind of scala.collection (on scala) that is intended to have efficient implementations of multiset operations. Multisets are represented as: `scala.scala.collection.Bag`

The multiset operations consist of: `union`, `intresect`, `diff`, `maxUnion` (a variant of union that represents the generalised set union), `contains`, `multiplicity`, `mostCommon`, `leastCommon`, ...

In Scala, the finite `Seq` can be used to represent multisets but are not optimized for their uses cases. Bag is not a subtype of Seq mainly because it has no indexing. `Bag` is not subtype (or supertype) of Set mainly because of incoherences between the definition of `union`, `intersect`, and `diff`. `Bag` is a subtype of `Iterable`.

Other languages usually use a compact representation of multisets where the data structure keeps a mapping from it's elements to the multiplicity of that element.

The implementation of `Bag` differs from those implementations by making the representation of elements more generic. Bag are mappings from elements to a BagBucket. This gives the possibility of implementing the bucket just like it is implemented in other languages or implementing custom buckets that fit some more specific purpose.

The key difference with other implementations is that buckets contain elements that are equivalent to each other but not necessarily equal. This give the user the possibility of grouping elements in a way that suits different needs.

Bag delegates subtasks of the Iterable methods to the underlying `BagBucket`. Therefore optimizations on buckets are reflected on the overall performance. For example if we take the compact representation it is possible to optimize filter (`forall`, `exists`, `contains`,...) to do only one check on the predicate.

The type of bucket that the bucket will have is defined at the construction of that bucket. It is defined using a `BagBucketFactory` that is implicitly taken into every constructor (ideally some default ones should be defined).

The code of bags looks like (input `-->` output):

```scala
import scala.collection.immutable._ // Note that Bags are in the scala.collections packages but do not have aliases in Predef
implicit val m1 = Bag.configuration.compact[Int] // define compact representation for Int
implicit val m2 = Bag.configuration.compact[Char] // define compact representation for Char
Bag.from('c'->2, 'd'->3)         --> Bag("c", "c"; "d", "d", "d")
Bag(1,2,2,3,3)                   --> Bag(1;2,2;3,3)
Bag(1,1) union Bag(1,2)          --> Bag(1,1,1;2)
Bag(1,2,2,3,3).multiplicity(2)   --> 2
Bag(1,2,2,3,3).multiplicity(5)   --> 0
{ val b = Bag(1,2,2,3,3); b(2) } --> Bag(2,2) // Return equivalent elements
```

```scala
// buckets will group Int that are equivalent modulo 3
val mod3Equiv = new Ordering[Int] {
   def compare(x: Int, y: Int): Int = (x % 3) - (y % 3)
}

implicit val m = immutable.TreeBag.configuration.keepAll[Int](mod3Equiv)

val bag = scala.collection.immutable.TreeBag.from(1 -> 2, 3 -> 3, 2 -> 1, 4 -> 4, 5 -> 1, 6 -> 1, 7 -> 1, 8 -> 1)

bag(0)    --> Bag(6, 3, 3, 3) // equivalent modulo 0
bag(1)    --> Bag(7, 4, 4, 4, 4, 1, 1) // equivalent modulo 1
bag(2)    --> Bag(8, 5, 2) // equivalent modulo 2
```

Maven repository
----------------
It is availabe on Maven Central [https://github.com/nicolasstucki/multisets/blob/master/MavenRepository.md]

