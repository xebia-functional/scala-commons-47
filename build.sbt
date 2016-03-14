name := "scala-commons-47"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.typelevel" %% "scalaz-contrib-210" % "0.2",
  "org.typelevel" %% "scalaz-contrib-validation" % "0.2",
  "org.typelevel" %% "scalaz-contrib-undo" % "0.2",
  "org.typelevel" %% "scalaz-nscala-time" % "0.2",
  "org.typelevel" %% "scalaz-spire" % "0.2",
  "org.specs2" %% "specs2" % "3.7" % "test"
)

scalacOptions in Test ++= Seq("-Yrangepos")

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

// Add your own project settings here
Keys.fork in Test := false

organization := "com.fortysevendeg"

organizationName := "47 Degrees"

organizationHomepage := Some(new URL("http://47deg.com"))

startYear := Some(2014)

description := "47 Degrees Commons Scala Utils"

homepage := Some(url("http://47deg.com"))

scmInfo := Some(ScmInfo(url("https://github.com/47deg/appsly-commons-47"), "https://github.com/47deg/appsly-commons-47.git"))

pomExtra :=
  <developers>
    <developer>
      <name>47 Degrees (twitter: @47deg)</name>
      <email>hello@47deg.com</email>
    </developer>
    <developer>
      <name>47 Degrees</name>
    </developer>
  </developers>
