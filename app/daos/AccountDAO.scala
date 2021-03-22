package daos

import db.schemas.{AccountTable, TransactionTable}
import db.tables.Account
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

@Singleton
class AccountDAO  @Inject()(dbConfigProvider: DatabaseConfigProvider)   {

  private  val dbConfig = dbConfigProvider.get[JdbcProfile]
  lazy  val accountTable = TableQuery[AccountTable]
  lazy  val transactionTable = TableQuery[TransactionTable]
  import dbConfig._

  def getAccounts():Future[Seq[Account]] ={
    val query = accountTable.result
    db.run(query)
  }

  def getAccountsByAccountNumber(accountNumber:String): Future[Option[Account]] = {
    val query  = accountTable.filter(x=>x.accNumber === accountNumber).result.headOption
    db.run(query)
  }

  //todo: update account balance
  def updateAccountBalance(accountNumber:String, accountBaance:Option[Float]):  Unit={
     db.run(
       accountTable.filter(x=>x.accNumber === accountNumber).map(u=>(u.accBalance)).update((accountBaance))
     )
  }

  /*
  def reconcileAccountBalance(accNumber:String): Unit ={
    val query = transactionTable.filter(record=>record.accNumber === accNumber).result
    db.run(query)
         .map(x=>x.foreach(b=>b.transactionType match {
          case "debit"  => accountBalance = accountBalance - b.amount
          case "credit" => accountBalance = accountBalance + b.amount
        }))

  }
  */

}
