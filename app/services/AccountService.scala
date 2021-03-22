package services

import daos._
import db.tables.Account
import javax.inject.{Inject, Singleton}

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
@Singleton
class AccountService @Inject()(accountDAO: AccountDAO) {

  //todo: Get all Accounts
  def list(): Future[Seq[Account]] = accountDAO.getAccounts().map(x=>x.map(y=>y))

  //todo: filter list by account number
  def getByAccNumber(accNumber:String):Future[Option[Account]]=
    accountDAO.getAccountsByAccountNumber(accNumber).map(x=>x.headOption)

  //todo: update Account Balance
  def updateAccountBalance(accNumber:String,accountBalance:Option[Float]): Future[Account] = {
   val x:Option[Account] = Await.result( getByAccNumber(accNumber),Duration.Inf)
    if(x == None){
      throw new RuntimeException("Account does noot Exist")
    }
    accountDAO.updateAccountBalance(accNumber,accountBalance)
    getByAccNumber(accNumber).map(x=>x.get)
  }




}
