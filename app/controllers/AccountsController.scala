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



  //todo: check balance on a given account
  def checkBalance(accountNo:String) = Action.async{
     accountService.getByAccNumber(accountNo).map(x=>x).flatMap{
       y=>
         y match {
           case Some(value) => Future.successful(Ok(Json.toJson(value)))
           case None => Future.successful(BadRequest("Record does not exist"))
         }
     }

  }


}
