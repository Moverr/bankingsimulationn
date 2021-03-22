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
class TransactionDAO  @Inject()(dbConfigProvider: DatabaseConfigProvider)  {

  private  val dbConfig = dbConfigProvider.get[JdbcProfile]
  lazy  val transactionTable = TableQuery[TransactionTable]
  import dbConfig._


  var transactions =  mutable.Seq[Transaction]()


  //todo: Add Transactiioon
  def Create(transaction:Transaction): Future[Transaction] ={
    val query = transactionTable.returning(transactionTable) += transaction
     db.run(query)

  }

  //todo: update absolute account
 // def updateAbsoluteAccountBalance(accNumber:String): Option[Account] =  updateAccountBalance(accNumber,getAbsoluteBalance(accNumber))

  //todo: Get Absolute Balance
  def getAbsoluteBalance(accNumber:String):  Future[Seq[Transaction]] ={

    val query = transactionTable.filter(record=>record.accNumber === accNumber).result
    db.run(query)

  }
  //todo: Get Transactioons by date and account
  def list(accountNumber:String, transactionDate:String,transactiontype:String):Future[Seq[Transaction]] = {

    val query = transactionTable
      .filter(record=>record.accNumber === accountNumber)
      .filter(record=>record.transactionDate === transactionDate)
      .filter(record=>record.transactionType === transactiontype)
      .result
    db.run(query)


  }

}
