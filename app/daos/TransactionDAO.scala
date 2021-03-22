package daos

import akka.http.scaladsl.model.DateTime
import db.schemas.TransactionTable
import db.tables.{Account, Transaction}
import helpers.TransactionType
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import services.{AccountService, TransactionService}

import collection.mutable
import collection.mutable
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class TransactionDAO  @Inject()(dbConfigProvider: DatabaseConfigProvider)  extends AccountService{

  private  val dbConfig = dbConfigProvider.get[JdbcProfile]
  lazy  val transactionTable = TableQuery[TransactionTable]
  import dbConfig._


  var transactions =  mutable.Seq[Transaction]()


  //todo: Add Transactiioon
  def Create(transaction:Transaction): Future[Transaction] ={
    val query = transactionTable.returning(transactionTable) += transaction
    //  Transaction(0L,transaction.accNumber,transaction.amount,transaction.transactionType,transaction.transactionDate)
     db.run(query)

  }

  //todo: update absolute account
  def updateAbsoluteAccountBalance(accNumber:String): Option[Account] =  updateAccountBalance(accNumber,getAbsoluteBalance(accNumber))

  //todo: Get Absolute Balance
  def getAbsoluteBalance(accNumber:String):  Float ={
    var accountBalance:Float  = 0
    val query = transactionTable.filter(record=>record.accNumber === accNumber).result
    val  accTransactions:Future[Seq[Transaction]] =   db.run(query)

    accTransactions.map(x=>x.foreach(b=>b.transactionType match {
      case "debit"  => accountBalance = accountBalance - b.amount
      case "credit" => accountBalance = accountBalance + b.amount
    }))


    /*
    for(transactioon <- accTransactions){
      transactioon.transactionType match {
        case "debit"  => accountBalance = accountBalance - transactioon.amount
        case "credit" => accountBalance = accountBalance + transactioon.amount
       }
    } */
    accountBalance
  }
  //todo: Get Transactioons by date and account
  def list(accountNumber:String, transactionDate:String): mutable.Seq[Transaction] =
    transactions.filter(record=>record.accNumber == accountNumber).filter(record=>record.transactionDate ==transactionDate)

}
