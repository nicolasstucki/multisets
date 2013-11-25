multisets
=========

Multi-sets for Scala collection

Multi-sets API is a new kind of collection (on scala) that is intended to have efficient implementations of multiset operations. The code is on https://github.com/nicolasstucki/multiset. Multisets are represented as: scala.collection.Bag

The multiset operations consist of: union, intresect, diff, maxUnion (a variant of union that represents the generalised set union), contains, multiplicity, mostCommon, leastCommon, ...

In Scala, the finite Seq can be used to represent multisets but are not optimized for their uses cases.

Other languages usually use a compact representation of multisets where the data structure keeps a mapping from it's elements to the multiplicity of that element.

The implementation of Bag differs from those implementations by making the representation of elements more generic. Bag are mappings from elements to a BagBucket. This gives the possibility of implementing the bucket just like it is implemented in other languages or implementing custom buckets that fit some more specific purpose.

The key difference with other implementations is that buckets contain elements that are equivalent to each other but not necessarily equal. This give the user the possibility of grouping elements in a way that suits different needs.

