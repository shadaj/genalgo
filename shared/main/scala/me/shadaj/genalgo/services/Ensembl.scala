package me.shadaj.genalgo.services

import me.shadaj.genalgo.network.Requester

import scala.concurrent.{ExecutionContext, Future}

object Ensembl {

  def getById(ensId:String)(implicit context: ExecutionContext): Future[String] =
    Requester.get(s"http://rest.ensembl.org/archive/id/${ensId}?content-type=application/json")

}


