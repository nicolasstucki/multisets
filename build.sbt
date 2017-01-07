name := "multisets"

version := "0.4"

scalaVersion := "2.12.1"

organization := "io.github.nicolasstucki"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/releases"

libraryDependencies ++= Seq(
  "com.storm-enroute" %% "scalameter" % "0.8.2" % "test",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)

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


