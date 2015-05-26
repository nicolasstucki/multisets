package scala.collection.immutable

import scala.collection._
import scala.collection.generic.CanBuildFrom
import scala.collection.generic

private[immutable] final class ReHashBox[A](elem: A, hash: Int) {
    override def hashCode() = hash
    override def equals(o: Any) = o match {
        case a: ReHashBox[_] =>
            a.hashCode() == hashCode()
        case _ => false
    }
    def getElem = elem
}

class HashBag[A] private[collection](contents: HashMap[ReHashBox[A], immutable.BagBucket[A]])(implicit protected  val bagConfiguration: immutable.HashedBagConfiguration[A])
  extends immutable.Bag[A]
  with immutable.BagLike[A, immutable.HashBag[A]]
  with Serializable {
    private val hash = bagConfiguration.equivClass.hash _

    implicit def toBox(elem: A): ReHashBox[A] =
        new ReHashBox(elem, hash(elem))

    override protected[this] def newBuilder: mutable.BagBuilder[A, HashBag[A]] =
        HashBag.newBuilder

    override def empty: immutable.HashBag[A] =
        immutable.HashBag.empty[A](bagConfiguration)

    override def getBucket(elem: A): Option[BagBucket] = {
        contents.get(elem)
    }

    protected def updatedBucket(bucket: BagBucket): HashBag[A] = {
        new HashBag[A](contents.updated(bucket.sentinel, bucket))
    }

    def bucketsIterator: Iterator[BagBucket] = {
        contents.valuesIterator
    }

}


object HashBag extends generic.ImmutableHashedBagFactory[immutable.HashBag] {

    implicit def canBuildFrom[A](implicit bagConfiguration: immutable.HashedBagConfiguration[A]): CanBuildFrom[Coll, A, immutable.HashBag[A]] = bagCanBuildFrom[A]

    def empty[A](implicit bagConfiguration: immutable.HashedBagConfiguration[A]): immutable.HashBag[A] = new immutable.HashBag[A](HashMap.empty)

    override def newBuilder[A](implicit bagConfiguration: immutable.HashedBagConfiguration[A]): mutable.BagBuilder[A, HashBag[A]] = new HashBagBuilder[A]

    final private class HashBagBuilder[A](implicit bagConfiguration: immutable.HashedBagConfiguration[A])
      extends mutable.BagBuilder[A, HashBag[A]] {
        private val hash = bagConfiguration.equivClass.hash _

        implicit def toBox(elem: A): ReHashBox[A] =
            new ReHashBox(elem, hash(elem))

        private var hashMap = HashMap.empty[ReHashBox[A], immutable.BagBucket[A]]

        def +=(elem: A): this.type = add(elem, 1)

        def clear(): Unit =
            hashMap = HashMap.empty[ReHashBox[A], immutable.BagBucket[A]]

        def result(): HashBag[A] =
            new HashBag[A](hashMap)

        def add(elem: A, count: Int): this.type = {
            addBucket(bagConfiguration.newBuilder(elem).add(elem, count).result())
        }

        def addBucket(bucket: collection.BagBucket[A]): this.type = {
            hashMap.get(bucket.sentinel) match {
                case Some(bucket2) => updateBucket(bagConfiguration.bucketFrom(bucket, bucket2))
                case None => updateBucket(bagConfiguration.bucketFrom(bucket))
            }
        }

        def updateBucket(bucket: immutable.BagBucket[A]): this.type = {
            hashMap = hashMap.updated(bucket.sentinel, bucket)
            this
        }
    }

}