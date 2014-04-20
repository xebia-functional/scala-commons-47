name := "scala-commons-47"

version := "0.1-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.typelevel" %% "scalaz-contrib-210" % "0.1.5",
  "org.typelevel" %% "scalaz-contrib-validation" % "0.1.5",
  "org.typelevel" %% "scalaz-contrib-undo" % "0.1.5",
  "org.typelevel" %% "scalaz-lift" % "0.1.5",
  "org.typelevel" %% "scalaz-nscala-time" % "0.1.5",
  "org.typelevel" %% "scalaz-spire" % "0.1.5",
  "org.specs2" %% "specs2" % "2.3.11" % "test"
)

scalacOptions in Test ++= Seq("-Yrangepos")

credentials += Credentials(new File(Path.userHome.absolutePath + "/.ivy2/.credentials"))

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

resolvers += "47deg Public" at "http://clinker.47deg.com/nexus/content/groups/public"

resolvers += "47deg Public Snapshot Repository" at "http://clinker.47deg.com/nexus/content/repositories/public-snapshots"

resolvers += "47deg Public Release Repository" at "http://clinker.47deg.com/nexus/content/repositories/public-releases"

// Add your own project settings here
Keys.fork in Test := false

organization := "ly.apps"

organizationName := "47 Degrees"

organizationHomepage := Some(new URL("http://47deg.com"))

publishMavenStyle := true

publishTo <<= version {
  v: String =>
    if (v.trim.endsWith("SNAPSHOT"))
      Some("47deg Public Snapshot Repository" at "http://clinker.47deg.com/nexus/content/repositories/public-snapshots")
    else
      Some("47deg Public Release Repository" at "http://clinker.47deg.com/nexus/content/repositories/public-releases")
}

startYear := Some(2014)

description := "47 Degrees Commons Scala Utils"

homepage := Some(url("http://47deg.com"))

scmInfo := Some(ScmInfo(url("https://github.com/47deg/appsly-server-ninecards"), "https://github.com/47deg/appsly-server-9.git"))

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