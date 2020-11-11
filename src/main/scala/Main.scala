package mo.example
import zio._, zio.console._

import mo.example.fileStuff.FileIO 

import mo.example.http.Http4Server.Http4Server
import mo.example.http.Http4Server 
import org.http4s.server.Server
//import //Http4Server.Http4Server
//https://github.com/kovacshuni/zio-http4s-zlayer-example

object Main extends zio.App {
 
  //file read
  // val appLogic:ZIO[FileIO,Throwable,Unit] = {
  //   FileIO.copyFileZIO()
  // }

    val appLogic:ZIO[Has[Server] with Console, Nothing, Nothing] = ZIO.never

  def run(args: List[String]) =  appLogic.provideLayer(prepareEnvironment).exitCode

  val httpServerLayer: ZLayer[ZEnv, Throwable, Http4Server] = Http4Server.createHttp4sLayer

  private val prepareEnvironment = httpServerLayer ++ Console.live //++ FileIO.live 
  

}
