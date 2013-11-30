package bagapps.directory

import scala.util.hashing.Hashing


object File {
  def withExtension(ext: String) = File("", "", ext)

  def withName(name: String) = File("", name, "")

  def inDirectory(dir: String) = File(dir, "", "")

  object ExtensionEquiv extends Ordering[File] with Hashing[File] {
    def compare(x: File, y: File): Int = x.ext compareTo y.ext

    def hash(x: File): Int = x.ext.hashCode
  }

  object FileNameEquiv extends Ordering[File] with Hashing[File] {
    def compare(x: File, y: File): Int = x.name compareTo y.name

    def hash(x: File): Int = x.name.hashCode
  }

  object DirectoryEquiv extends Ordering[File] with Hashing[File] {
    def compare(x: File, y: File): Int = x.directory compareTo y.directory

    def hash(x: File): Int = x.directory.hashCode
  }

}


case class File(directory: String, name: String, ext: String) {

  override def toString: String = s"$directory/$name.$ext"

  def fileName = s"$name.$ext"
}














