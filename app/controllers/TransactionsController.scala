package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.Future

class TransactionsController   @Inject()(cc:ControllerComponents)  extends AbstractController(cc) {

  //todo: debit
  //todo: list accounts from the service
  def debit() = Action.async{

    Future.successful(Ok("???"))
  }

  //todo: credit
  def credit() = Action.async{

    Future.successful(Ok("???"))
  }

  //todo: list transactions
  def list(from:Int,limit:Int) = Action.async{

    Future.successful(Ok("???"))
  }




}
