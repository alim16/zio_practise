package mo.example
import zio._, zio.console._

import mo.example.fileStuff.FileIO 
import mo.example.streaming.StreamingStuff
import mo.example.http.server.HttpServer
import org.http4s.server.Server
import zio.stream.ZStream
import zio.duration._
import zio.clock.Clock

object Main extends zio.App {
 
  val appLogic = 
  for {
    _ <- putStrLn("hello")
    n <- StreamingStuff.numberStream
    _ <- putStrLn(s"the numbers are: ${n.toString}")
    serverFiber <- HttpServer.startHttpServer.fork
    _ <- putStrLn("server is running on localhost:8080")
    //the next line kills the main fiber after 30s which kills the child server fiber
    _ <- ZIO.sleep(30.seconds) *> putStrLn("30 seconds over: shutting down server...")
  } yield  () //ExitCode.success


  def run(args: List[String]) =  appLogic.provideLayer(prepareEnvironment).exitCode

  private val prepareEnvironment = Clock.live ++ Console.live ++ StreamingStuff.live ++ HttpServer.live
  
}

