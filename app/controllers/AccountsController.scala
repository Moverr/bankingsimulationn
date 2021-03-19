package controllers

import javax.inject.Inject
import play.api.mvc._

import scala.concurrent.Future

class AccountsController  @Inject()(cc:ControllerComponents)  extends AbstractController(cc){

 //todo: list accounts from the service
  def list(from:Int,limit:Int) = Action.async{

    Future.successful(Ok("???"))
  }


  //todo: check balance on a given account
  def checkBalance(accountNo:String) = Action.async{
    Future.successful(Ok("???"))
  }


}
