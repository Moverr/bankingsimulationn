package services

import daos.AccountDAO
import db.tables.Account
import javax.inject.{Inject, Singleton}

import scala.concurrent.Future

@Singleton
class AccountService  {
  val account =   Account("Muyinda Rogers","12345",None)

  //todo: Get all Accounts
  def list(): Seq[Account] = Seq(account)

  //todo: filter list by account number
  def getByAccNumber(accNumber:String):Option[Account]= list().filter(record=>record.accNumber==accNumber).headOption

  //todo: update Account Balance
  def updateAccountBalance(accNumber:String,accountBalance:Float): Option[Account] = {
    getByAccNumber(accNumber) match {
      case Some(account) => Some(Account(account.accName, account.accName, Some(accountBalance)))
      case None =>  None
    }
  }

}
