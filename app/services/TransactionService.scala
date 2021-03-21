package services

import controllers.requests.TransactionRequest
import daos.TransactionDAO
import db.tables.{Account, Transaction}
import helpers.{Constants, TransactionType}
import javax.inject.{Inject, Singleton}

import scala.concurrent.Future
import collection.mutable

@Singleton
class TransactionService @Inject()(  accountService: AccountService,transactionDAO: TransactionDAO){

  //todo: credit
  def credit(request:TransactionRequest): Future[Transaction] ={
    //todo: validate account
    val responsne = accountService.getByAccNumber(request.accNumber)
    responsne match {
      case Some(account: Account) =>{
        //todo: Get Daily Transactions from this account
        val dailyTransnactions:mutable.Seq[Transaction] = getNumberOfDailyTransactions(request.accNumber,request.transactionDate.toString("yyyy-MM-d"),TransactionType.credit)
        val totalNumberofDeposits:Float = dailyTransnactions.map(record=>record.amount).sum

        //todo: Check the number fo daily transactions permitted
        if(dailyTransnactions.length >= Constants.MAX_DEPOSIT_PER_TRANSACTION){
          throw new RuntimeException("Exceeded Maximum Number of Withdraws per day ")
        }


        //todo: Credit
        val transaction = Transaction(account.accNumber,request.amount,TransactionType.credit.toString,request.transactionDate.toString("yyyy-MM-d"));
        transactionDAO.Create(transaction)
        //todo: Do Reconnciliation
        Future.successful(transaction)
      }
      case None => throw new NullPointerException("Account does not exist")
    }
  }

  //todo: debit
  def debit(request:TransactionRequest): Future[Transaction] ={
    //todo: validate account
    accountService.getByAccNumber(request.accNumber) match {
      case Some(account: Account) =>{
        //todo: Debit
        val transaction = Transaction(account.accNumber,request.amount,TransactionType.debit.toString,request.transactionDate.toString("yyyy-MM-d"));

        transactionDAO.Create(transaction)
        //todo: Do Reconnciliation
        Future.successful(transaction)
      }
      case None => throw new NullPointerException("Account does not exist")
    }

  }

  //todo: Get Number of Daily Transactions
  def getNumberOfDailyTransactions(accNumber:String,transactionDate:String,transactionType: TransactionType.Value): mutable.Seq[Transaction] =
     transactionDAO.list(accNumber,transactionDate).filter(x=>x.transactionType==transactionType)




  //todo: view transaction based on date and time

}
