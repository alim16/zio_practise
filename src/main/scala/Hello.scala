import zio._, zio.console._

import scala.util.Try
import scala.io.Source
import scala.util.Success
import scala.util.Failure
import java.io.IOException

object Hello extends zio.App {
 
  import fileIO.fileStuff
  val appLogic = fileStuff

  def run(args: List[String]) =  appLogic.exitCode 

}
