package controllers

import controllers.requests.TransactionRequest
import helpers.TransactionType
import javax.inject.Inject
import org.joda.time.DateTime
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import services.TransactionService

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import implicits.TransactionResponse._
import implicits.JodaReads._

class TransactionsController   @Inject()(cc:ControllerComponents,transactionService: TransactionService)  extends AbstractController(cc) {

  //todo: Deposit
  def credit() = Action.async{ implicit  request =>

    val accnumber = request.body.asJson.get("accnumber").as[String]
    val amount =    request.body.asJson.get("amount").as[String].toFloat
    val transactionType = TransactionType.credit
    val date_created =request.body.asJson.get("date_created").as[DateTime]


    val transactionRequest:TransactionRequest = TransactionRequest(accnumber,amount,transactionType,date_created)

    val response =  transactionService.credit(transactionRequest)

    response
      .flatMap{
        result=> Future.successful(Ok(Json.toJson(result)))
      }

  }



  //todo: Withdraw
  def debit() = Action.async{implicit  request =>

    val accnumber = request.body.asJson.get("accnumber").as[String]
    val amount =    request.body.asJson.get("amount").as[String].toFloat
    val transactionType = TransactionType.debit
    val date_created =request.body.asJson.get("date_created").as[DateTime]


    val transactionRequest:TransactionRequest = TransactionRequest(accnumber,amount,transactionType,date_created)

    val response =  transactionService.debit(transactionRequest)

    response
      .flatMap{
        result=> Future.successful(Ok(Json.toJson(result)))
      }

  }



  //todo:  Check Balance
  def balance() = Action.async{

    Future.successful(Ok("???"))
  }




}
