package controllers

import controllers.requests.TransactionRequest
import db.tables.Transaction
import helpers.TransactionType
import javax.inject.Inject
import org.joda.time.DateTime
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import services.TransactionService

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import implicits.TransactioonResponse._

class TransactionsController   @Inject()(cc:ControllerComponents,transactionService: TransactionService)  extends AbstractController(cc) {

  //todo: debit
  //todo: list accounts from the service
  def debit() = Action.async{implicit  request =>

    val accnumber = request.body.asJson.get("accnumber").as[String]
    val amount =  request.body.asJson.get("amount").as[Float]
    val transactionType = TransactionType.debit
    val date_created = DateTime.now()

    val transactionRequest:TransactionRequest = TransactionRequest(accnumber,amount,transactionType,date_created)
     transactionService.debit(transactionRequest)
      .flatMap{
        result=> Future.successful(Ok(Json.toJson(result)))
      }


  }

  //todo: credit
  def credit() = Action.async{ implicit  request =>

    val accnumber = request.body.asJson.get("accnumber").as[String]
    val amount =  request.body.asJson.get("amount").as[Float]
    val transactionType = TransactionType.credit
    val date_created = DateTime.now()

    val transactionRequest:TransactionRequest = TransactionRequest(accnumber,amount,transactionType,date_created)
    transactionService.credit(transactionRequest)
      .flatMap{
        result=> Future.successful(Ok(Json.toJson(result)))
      }

  }

  //todo: list transactions
  def list(from:Int,limit:Int) = Action.async{

    Future.successful(Ok("???"))
  }




}
