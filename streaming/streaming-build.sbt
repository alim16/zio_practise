scalaVersion := "2.13.3"
name := "streaming"
val zioVersion      = "1.0.2"

libraryDependencies ++= Seq("dev.zio" %% "zio-streams" % zioVersion)