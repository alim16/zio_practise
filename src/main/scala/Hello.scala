import zio._, zio.console._

import scala.util.Try
import scala.io.Source
import scala.util.Success
import scala.util.Failure
import java.io.IOException

object Hello extends zio.App {
  def readFileZio(file: String) =
    ZIO.effect {
      val source = scala.io.Source.fromFile(file)
      try source.getLines.mkString
      finally source.close()
    }

  def writeFileZio(file: String, text: String) =
    ZIO.effect {
      import java.io._
      val pw = new PrintWriter(new File(file))
      try pw.write(text)
      finally pw.close
    }

  def copyFileZio(source: String, dest: String) =
    readFileZio(source).flatMap(text => writeFileZio(dest, text))

  val appLogic = for {
    _    <- putStrLn("starting")
    _    <- copyFileZio("src/main/scala/mytext.txt", "src/main/scala/mytext2.txt")
    text <- readFileZio("src/main/scala/mytext2.txt")
    _    <- putStrLn(text)
  } yield ()

  def run(args: List[String]) = appLogic.orDie.as(1) 
  //.refineToOrDie[IOException]
    
    //appLogic.exitCode 
}
