package controllers

import db.tables.Account
import javax.inject.Inject
import play.api.mvc._
import implicits.AccountResponse._
import play.api.libs.json.Json

import scala.concurrent.Future

class AccountsController  @Inject()(cc:ControllerComponents)  extends AbstractController(cc){


  val account =   Account("Muyinda Rogers","12345",None)

 //todo: list accounts from the service
  def list() = Action.async{
    val accounts:Seq[Account] = Seq(account)
    Future.successful(Ok(Json.toJson(accounts)))
  }


  //todo: check balance on a given account
  def checkBalance(accountNo:String) = Action.async{
    Future.successful(Ok("???"))
  }


}
