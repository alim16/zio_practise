scalaVersion := "2.13.3"

// Monix Minitest:
//libraryDependencies += "io.monix" %% "minitest" % "2.8.2" % "test"
//testFrameworks += new TestFramework("minitest.runner.Framework")

// Hedgehog:
val hedgehogVersion = "0.4.2"
val zioVersion = "1.0.2"
libraryDependencies ++= Seq(
  // "qa.hedgehog" %% "hedgehog-core" % hedgehogVersion,
  // "qa.hedgehog" %% "hedgehog-runner" % hedgehogVersion,
  // "qa.hedgehog" %% "hedgehog-minitest" % hedgehogVersion,
  "dev.zio" %% "zio" % zioVersion,
 // "dev.zio" %% "zio-streams" % zioVersion
)