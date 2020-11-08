import zio.ZIO
import zio.console._

object fileIO {
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

  val fileStuff = for {
    _    <- putStrLn("starting")
    _    <- copyFileZio("src/main/scala/mytext.txt", "src/main/scala/mytext2.txt")
    text <- readFileZio("src/main/scala/mytext2.txt")
    _    <- putStrLn(text)
  } yield ()
}