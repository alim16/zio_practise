package mo.example
import zio.console._
import zio._
import java.io.IOException
import zio.ZLayer

package object fileStuff {
  type FileIO = Has[FileIO.Service]

  object FileIO {
    trait Service {
      def readFileZio(file: String): ZIO[Any, Throwable, String]
      def writeFileZio(file: String, text: String): ZIO[Any, Throwable, Unit]
      def copyFileZio(
          source: String,
          dest: String
      ): ZIO[Any, Throwable, Unit]
    }

    val live: URLayer[ Console, FileIO] = ZLayer.fromService { consoleService =>
      new Service {
        override def readFileZio(file: String): ZIO[Any, Throwable, String] =
          ZIO.effect {
            val source = scala.io.Source.fromFile(file)
            try source.getLines.mkString
            finally source.close()
          }

        override def writeFileZio(file: String, text: String) =
          ZIO.effect {
            import java.io._
            val pw = new PrintWriter(new File(file))
            try pw.write(text)
            finally pw.close
          }

        override def copyFileZio(source: String, dest: String) =
          for {
            text <- readFileZio(source)
            _ <-   writeFileZio(dest, text)
            _ <- consoleService.putStrLn(s"copied content of ${source} to ${dest}")
            _ <- consoleService.putStrLn(s"content was: ${text}")
          } yield ()
      }
    }

    def copyFileZIO(
        source: String = "src/main/scala/mytext.txt",
        dest: String = "src/main/scala/mytext2.txt"
    ): ZIO[FileIO, Throwable, Unit] = ZIO.accessM(_.get.copyFileZio(source, dest))

  }
}
