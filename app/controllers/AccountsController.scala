package controllers

import db.tables.Account
import javax.inject.Inject
import play.api.mvc._
import implicits.AccountResponse._
import play.api.libs.json.Json
import services.AccountService

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class AccountsController  @Inject()(cc:ControllerComponents,accountService: AccountService)  extends AbstractController(cc){




 //todo: list accounts from the service
  def list() = Action.async{
    val response:Seq[Account]= accountService.list()
    Future.successful(Ok(Json.toJson(response)))
  }


  //todo: check balance on a given account
  def checkBalance(accountNo:String) = Action.async{
    Future.successful(Ok("???"))
  }


}
