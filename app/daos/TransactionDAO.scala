package daos

import akka.http.scaladsl.model.DateTime
import db.tables.Transaction
import helpers.TransactionType
import javax.inject.Singleton
import services.{AccountService, TransactionService}

import collection.mutable

@Singleton
class TransactionDAO extends AccountService{

  var transactions =  mutable.Seq[Transaction]()


  //todo: Add Transactiioon
  def Create(transaction:Transaction): Transaction ={
     transactions = transactions :+ transaction ;
     transaction
  }

  //todo: update absolute account
  def updateAbsoluteAccountBalance(accNumber:String): Unit =  updateAccountBalance(accNumber,getAbsoluteBalance(accNumber))

  //todo: Get Absolute Balance
  def getAbsoluteBalance(accNumber:String):  Float ={
    val accTransactions = transactions.filter(record=>record.accNumber == accNumber)
    var accountBalance:Float  = 0
    for(transactioon <- accTransactions){
      transactioon.transactionType match {
        case "debit"  => accountBalance = accountBalance - transactioon.amount
        case "credit" => accountBalance = accountBalance + transactioon.amount
       }
    }
    accountBalance
  }
  //todo: Get Transactioons by date and account
  def list(accountNumber:String, transactionDate:String): mutable.Seq[Transaction] ={
    transactions.filter(record=>record.accNumber == accountNumber).filter(record=>record.transactionDate ==transactionDate)
  }
}
