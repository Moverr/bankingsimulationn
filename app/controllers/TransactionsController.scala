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

    try {


    val accnumber = request.body.asJson.get("accnumber").as[String]
    val amount =    request.body.asJson.get("amount").as[String].toFloat
    val transactionType = TransactionType.credit
    val transaction_date =request.body.asJson.get("transaction_date").as[DateTime]


    val transactionRequest:TransactionRequest = TransactionRequest(accnumber,amount,transactionType,transaction_date)

    val response =  transactionService.credit(transactionRequest)

    response
      .flatMap{
        result=> Future.successful(Ok(Json.toJson(result)))
      }
    }catch{
      case e:RuntimeException=>  Future.successful(BadRequest(Json.toJson(e.getMessage)))
      case _: Throwable =>  Future.successful(InternalServerError("Something Went wrong, contact system administratior"))
    }
  }



  //todo: Withdraw
  def debit() = Action.async{implicit  request =>
    try {

    val accnumber = request.body.asJson.get("accnumber").as[String]
    val amount =    request.body.asJson.get("amount").as[String].toFloat
    val transactionType = TransactionType.debit
    val transaction_date =request.body.asJson.get("transaction_date").as[DateTime]


    val transactionRequest:TransactionRequest = TransactionRequest(accnumber,amount,transactionType,transaction_date)

    val response =  transactionService.debit(transactionRequest)

    response
      .flatMap{
        result=> Future.successful(Ok(Json.toJson(result)))
      }
    }catch{
      case e:RuntimeException=>  Future.successful(BadRequest(Json.toJson(e.getMessage)))
      case _: Throwable =>  Future.successful(InternalServerError("Something Went wrong, contact system administratior"))
    }
  }






}
