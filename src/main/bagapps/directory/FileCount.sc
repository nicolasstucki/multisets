import bagapps.directory._

import scala.collection.immutable._


val files = List(
  "/dirA/file1.jpeg",
  "/dirA/file2.jpeg",
  "/dirA/file3.jpeg",
  "/dirA/file4.jpeg",
  "/dirA/dir1/file1.jpeg",
  "/dirA/dir1/file2.jpeg",
  "/dirA/dir1/file3.jpeg",
  "/dirA/dir1/file4.jpeg",
  "/dirA/dir1/file5.jpeg",
  "/dirA/dir1/file6.jpeg",
  "/dirA/dir1/file7.jpeg",
  "/dirA/dir1/file8.jpeg",
  "/dirA/dir2/file1.png",
  "/dirA/dir2/file2.png",
  "/dirA/dir2/file3.png",
  "/dirB/dir1/file4.txt",
  "/dirB/dir1/file1.jpeg",
  "/dirB/dir1/file2.jpeg",
  "/dirB/dir2/file3.png",
  "/dirB/dir2/file4.png",
  "/dirB/dir2/file5.png",
  "/dirB/dir2/file6.png",
  "/dirB/dir3/file1.txt",
  "/dirB/dir3/file2.txt",
  "/dirB/dir3/file3.txt"
).map(Path.parse)


















































val bag1 = Bag(files: _*)(BagBucketFactory.ofVectors[File](FileEquiv.ExtensionEquiv))

























































































val bag2 = Bag(files: _*)(BagBucketFactory.ofVectors[File](FileEquiv.FileNameEquiv))




















































































val bag3 = Bag(files: _*)(BagBucketFactory.ofVectors[File](FileEquiv.DirEquiv))




















































































val bag4 = Bag(files: _*)(BagBucketFactory.ofVectors[File](FileEquiv.DirDepthEquiv))




















































































def m(bag: Bag[File], path: String) = {
  val file = Path.parse(path)
  (path, bag.multiplicity(file))
}





m(bag1, "/f.jpeg")

m(bag1, "/f.txt")
m(bag1, "/f.png")
m(bag2, "/file1.x")
m(bag2, "/file2.x")
m(bag2, "/file3.x")
m(bag2, "/file4.x")
m(bag2, "/file5.x")
m(bag2, "/file6.x")
m(bag3, "/dirA/dir1/x.x")
m(bag3, "/dirA/dir2/x.x")
m(bag3, "/dirB/dir1/x.x")
m(bag3, "/dirB/dir2/x.x")
m(bag3, "/dirB/dir3/x.x")
m(bag4, "/x.x")
m(bag4, "/dirB/x.x")
m(bag4, "/dirB/dir3/x.x")
bag1.mostCommon



















bag2.mostCommon



















bag3.mostCommon














bag4.mostCommon





























bag1.leastCommon




bag2.leastCommon








bag3.leastCommon

















bag4.leastCommon










