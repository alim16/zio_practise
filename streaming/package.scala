package mo.example

import zio.{Has,ZLayer,ZIO, URIO, UIO, Chunk}
import zio.stream.ZStream
import zio.console.Console

import java.nio.file.{Paths, Path}
import zio.blocking.Blocking
import zio.stream.ZTransducer

package object streaming {
  type StreamingStuff = Has[StreamingStuff.Service]

  object StreamingStuff {
    trait Service {
        def numberStream() : ZIO[Any, Nothing, Chunk[Int]]
        def readCsv(fileName:String): ZIO[Any, Nothing,ZStream[Blocking,Throwable,String]]
    }

    val live: ZLayer[Any,Nothing,StreamingStuff] =
      ZLayer.succeed {
        new Service {
        override def numberStream() = ZStream(1,2,3,4,5).runCollect
        override def readCsv(fileName:String): ZIO[Any, Nothing,ZStream[Blocking,Throwable,String]] = 
        ZIO.succeed(ZStream.fromFile(Paths.get(fileName)).transduce(ZTransducer.utf8Decode >>> ZTransducer.splitLines ))
        }
      }
    
    def numberStream: URIO[StreamingStuff, Chunk[Int]] = ZIO.accessM(_.get.numberStream())
    def readCsv(fileName:String): URIO[StreamingStuff, ZStream[Blocking,Throwable,String]]= ZIO.accessM(_.get.readCsv(fileName))
  }
}