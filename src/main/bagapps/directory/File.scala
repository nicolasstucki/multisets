package bagapps.directory

import scala.util.parsing.combinator.RegexParsers


object Path extends RegexParsers {

  private def fileName: Parser[String] = """[a-zA-Z0-9_]*""".r

  private def fileExt: Parser[String] = """[a-zA-Z0-9]*""".r

  private def dirName: Parser[String] = """[a-zA-Z0-9_]*""".r <~ "/"

  private def dir: Parser[Path] = """/""".r ~ rep(dirName) ^^ {
    case "/" ~ list => list.foldLeft[Path](Root)((path, name) => new Dir(path, name))
  }

  private def file: Parser[File] = dir ~ fileName ~ "." ~ fileExt ^^ {
    case dir ~ fileName ~ "." ~ fileExt => new File(dir, fileName, fileExt)
  }


  def parse(path: String): File = parse(file, path) match {
    case Success(result, _) => result
    case failure: NoSuccess => throw new Exception("file format exception: " + path)
  }
}

trait Path {
  def parent: Option[Path]

  def name: String

  def directoryDepth: Int
}

case class Dir(dir: Path, name: String) extends Path {
  def parent: Option[Path] = Some(dir)


  def directoryDepth: Int = dir.directoryDepth + 1

  override def toString: String = dir.toString + name + '/'
}

case class File(dir: Path, name: String, ext: String) extends Path {

  def parent: Option[Path] = Some(dir)


  def directoryDepth: Int = dir.directoryDepth

  override def toString: String = dir.toString + name + '.' + ext
}


object Root extends Path {
  def parent: Option[Path] = None

  def name: String = "/"

  def directoryDepth: Int = 0

  override def toString: String = "/"
}













