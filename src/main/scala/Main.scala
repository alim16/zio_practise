package mo.example
import zio._, zio.console._

import scala.util.Try
import scala.io.Source
import scala.util.Success
import scala.util.Failure
import java.io.IOException

import mo.example.fileStuff.FileIO 

object Main extends zio.App {
 
  val appLogic:ZIO[FileIO,Throwable,Unit] = {
    FileIO.copyFileZIO()
  }

  def run(args: List[String]) =  appLogic.provideLayer(prepareEnvironment).exitCode

  private val prepareEnvironment: URLayer[Console, FileIO] = FileIO.live 
  

}
