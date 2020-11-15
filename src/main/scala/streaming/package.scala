package mo.example

import zio.{Has,ZLayer,ZIO, URIO, UIO, Chunk}
import zio.stream.ZStream
import zio.console.Console


package object streaming {
  type StreamingStuff = Has[StreamingStuff.Service]

  object StreamingStuff {
    trait Service {
        def numberStream() : ZIO[Any, Nothing, Chunk[Int]]
    }

    val live: ZLayer[Console,Nothing,StreamingStuff] =
      ZLayer.fromService { console =>
        new Service {
        override def numberStream() = ZStream(1,2,3,4,5).runCollect
        }
      }
    
    def numberStream: URIO[StreamingStuff with Console ,Chunk[Int]] = ZIO.accessM(_.get.numberStream())

  }
}