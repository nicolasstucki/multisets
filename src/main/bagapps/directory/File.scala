package bagapps.directory


object File {
  def withExtension(ext: String) = File("", "", ext)

  def withName(name: String) = File("", name, "")

  def inDirectory(dir: String) = File(dir, "", "")
}


case class File(directory: String, name: String, ext: String) {

  override def toString: String = s"$directory/$name.$ext"

  def fileName = s"$name.$ext"
}














