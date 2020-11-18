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





///////////////////////////////////////////

// package object server {
//   type HttpServer = Has[HttpServer.Service]

//   object HttpServer {
//     trait Service {
//       def createHttpLayer():ZIO[ZEnv, Throwable, Server]
//     }

//     private def createHttp4Server: ZManaged[ZEnv, Throwable, Server] =
//       ZManaged.runtime[ZEnv].flatMap { implicit runtime: Runtime[ZEnv] =>
//         BlazeServerBuilder[Task](runtime.platform.executor.asEC)
//           .bindHttp(8080, "localhost")
//           .withHttpApp(Routes.helloWorldsService)
//           .resource
//           .toManagedZIO
//       }

//     val live : ZLayer[Any,Nothing,HttpServer]  = ZLayer.succeed(
//       new Service {
//         override def createHttpLayer() = createHttp4Server //ZLayer.fromManaged(createHttp4Server)
//       }
//     )

//     def createHttpLayer:  URIO[HttpServer, Server] = ZIO.accessM(_.get.createHttpLayer)
//   }

//   // def createHttp4sLayer: ZLayer[ZEnv, Throwable, Http4Server] =
//   //     ZLayer.fromManaged(createHttp4Server)
// }
