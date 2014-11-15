package me.shadaj.genalgo.services

import me.shadaj.genalgo.network.Requester
import microjson._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Failure}


/**
 * Note is is a bit ugly but I need it ASAP
 * will rewrite it when will figure out how to organize JSON parsing
 */
object Ensembl{

  object VersionInfo {
    def apply(obj:JsObject): Option[VersionInfo] =for {
      latest<-obj.value.get("latest")
      version<-obj.value.get("version")
      release<-obj.value.get("release")
    } yield VersionInfo(latest toString,version toString,release toString)
  }

  case class VersionInfo(latest:String,version:String,release:String)

  case class ArchiveResult (id:String, elementType:String,assembly:String, version:Option[VersionInfo]  )

  object ArchiveResult {


    def apply(value:String): Option[ArchiveResult] = apply(Json.read(value))
      /**
       * Ugly parser
       * @param value
       * @return
       */
      def apply(value:JsValue): Option[ArchiveResult] = value match {
        case obj:JsObject=>
          (obj.value.get("id"),  obj.value.get("type"), obj.value.get("assembly"))
          match {
            case (Some(JsString(idValue)),Some(JsString(typeValue)),Some(JsString(assemblyValue)))=>
              Some(ArchiveResult(idValue,typeValue, assemblyValue,VersionInfo(obj)))

            case other=> None

          }
        case _=>None
      }
  }




  def archive(ensId:String)(implicit context: ExecutionContext): Future[ArchiveResult] =
    Requester.get(s"http://rest.ensembl.org/archive/id/$ensId?content-type=application/json").map(
       result=>
         ArchiveResult(result) match {
           case None => throw new IllegalArgumentException(s"cannot read archive for $ensId")
           case Some(res) => res
         }
    )

//  case class HomologyResult()
//
//  object HomologyResult {
//
//    def apply(string:String) = apply(Json.read(string))
//
//    def apply(value:JsValue):Option[HomologyResult] =
//  }
//
//  def homology(ensId:String)(implicit context:ExecutionContext):Future[ArchiveResult] =
//    Requester.get(s"http://rest.ensembl.org/archive/id/$ensId?content-type=application/json").map(
//      result=>
//        this.parseArchive(Json.read(result)) match {
//          case None => throw new IllegalArgumentException(s"cannot read archive for $ensId")
//          case Some(res) => res
//        }
//    )
}


