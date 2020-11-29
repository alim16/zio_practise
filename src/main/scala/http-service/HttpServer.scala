package mo.example.http

import zio._
import zio.interop.catz._
import zio.interop.catz.implicits._

import org.http4s.server._
//import org.http4s.server.blaze._
import org.http4s.server.blaze.BlazeServerBuilder

//https://github.com/kovacshuni/zio-http4s-zlayer-example
package object server {
  type HttpServer = Has[HttpServer.Service]

  object HttpServer {
    trait Service {
      //TODO:not sure about HttpServer dependency is required
      def startHttpServer():ZManaged[HttpServer, Nothing, Server] 
    }

    private def startHttp4Server: ZManaged[Any, Throwable, Server] =
      ZIO.runtime[Any].toManaged_.flatMap { implicit runtime =>
        BlazeServerBuilder[Task](runtime.platform.executor.asEC)
          .bindHttp(8080, "localhost")
          .withHttpApp(Routes.helloWorldsService)
          .resource
          .toManagedZIO
      }
  val live : ZLayer[Any,Nothing,HttpServer]  = ZLayer.succeed(
      new Service {
        override def startHttpServer():ZManaged[Any, Nothing, Server] = startHttp4Server.orDie
      }
    )

    def startHttpServer:  URIO[HttpServer, Server] = ZIO.accessM(_.get.startHttpServer.useForever)
  }

}