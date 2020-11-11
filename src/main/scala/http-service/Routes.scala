package mo.example.http

import zio._
import zio.interop.catz._

import org.http4s._
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits._

object Routes {
    val dsl = Http4sDsl[Task]
    import dsl._

    val helloWorldsService = HttpRoutes
        .of[Task] {
            case GET -> Root / "hello" => Ok("Hello there")
            case GET -> Root / "something" => Ok("something else")
        }.orNotFound
}