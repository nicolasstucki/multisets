name := "multisets"

version := "0.1"

scalaVersion := "2.10.3"

organization := "io.github.nicolasstucki"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq(
	"com.github.axel22" %% "scalameter" % "0.4" % "test",
    "org.scalatest" % "scalatest_2.10" % "2.0" % "test",
    "org.scalacheck" %% "scalacheck" % "1.11.2" % "test")

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

EclipseKeys.withSource := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>https://github.com/nicolasstucki/multisets</url>
  <licenses>
    <license>
      <name>BSD-style</name>
      <url>http://www.opensource.org/licenses/bsd-license.php</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:nicolasstucki/multisets.git</url>
    <connection>scm:git:git@github.com:nicolasstucki/multisets.git</connection>
  </scm>
  <developers>
    <developer>
      <id>nicolas.stucki</id>
      <name>Nicolas Stucki</name>
      <url>http://io.github.nicolasstucki</url>
    </developer>
  </developers>
)


