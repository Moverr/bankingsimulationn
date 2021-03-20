package controllers

import helpers.TransactionType
import javax.inject.Inject
import org.joda.time.DateTime
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.Future

class TransactionsController   @Inject()(cc:ControllerComponents)  extends AbstractController(cc) {

  //todo: debit
  //todo: list accounts from the service
  def debit() = Action.async{implicit  request =>
    val accnumber = request.body.asJson.get("accnumber").as[String]
    val amount =  request.body.asJson.get("amount").as[Float]
    val transactionType = TransactionType.debit
    val date_created = DateTime.now()

    Future.successful(Ok("???"))
  }

  //todo: credit
  def credit() = Action.async{ implicit  request =>

    val accnumber = request.body.asJson.get("accnumber").as[String]
    val amount =  request.body.asJson.get("amount").as[Float]
    val transactionType = TransactionType.credit
    val date_created = DateTime.now()

    Future.successful(Ok("???"))
  }

  //todo: list transactions
  def list(from:Int,limit:Int) = Action.async{

    Future.successful(Ok("???"))
  }




}
