name := "multi-sets"

version := "0.1"

scalaVersion := "2.10.2"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq(
	"com.github.axel22" %% "scalameter" % "0.3",
    "org.scalatest" % "scalatest_2.10" % "2.0.M5b" % "test",
    "org.scalacheck" %% "scalacheck" % "1.10.1" % "test")

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

EclipseKeys.withSource := true
