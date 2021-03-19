package services

import daos.AccountDAO
import db.tables.Account
import javax.inject.{Inject, Singleton}

import scala.concurrent.Future

@Singleton
class AccountService @Inject()(accountDAO: AccountDAO) {
  val account =   Account("Muyinda Rogers","12345",None)

  //todo: Get all Accounts
  def list(): Seq[Account] = Seq(account)

  //todo: filter list by account number
  def getByAccNumber(accNumber:String):Option[Account]= list().filter(x=>x.accNumber==accNumber).headOption

}
