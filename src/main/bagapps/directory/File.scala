package bagapps.directory

import scala.util.hashing.Hashing


object File {
  def withExtension(ext: String) = File("", "", ext)

  def withName(name: String) = File("", name, "")

  def inDirectory(dir: String) = File(dir, "", "")

  object ExtensionEquiv extends Equiv[File] with Hashing[File] {
    def equiv(x: File, y: File): Boolean = x.ext == y.ext

    def hash(x: File): Int = x.ext.hashCode
  }

  object FileNameEquiv extends Equiv[File] with Hashing[File] {
    def equiv(x: File, y: File): Boolean = x.name == y.name

    def hash(x: File): Int = x.name.hashCode
  }

  object DirectoryEquiv extends Equiv[File] with Hashing[File] {
    def equiv(x: File, y: File): Boolean = x.directory == y.directory

    def hash(x: File): Int = x.directory.hashCode
  }

}


case class File(directory: String, name: String, ext: String) {

  override def toString: String = s"$directory/$name.$ext"


  override def hashCode(): Int = toString.hashCode

  override def equals(obj: scala.Any): Boolean = toString.equals(obj)

  def fileName = s"$name.$ext"
}














