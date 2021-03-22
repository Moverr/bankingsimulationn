package services

import controllers.requests.TransactionRequest
import controllers.responses.TransactionResponse
import daos.TransactionDAO
import db.tables.{Account, Transaction}
import helpers.{Constants, TransactionType}
import javax.inject.{Inject, Singleton}

import scala.concurrent.{Await, Future}
import collection.mutable
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.impl.Promise
@Singleton
class TransactionService @Inject()(  accountService: AccountService,transactionDAO: TransactionDAO){

  //todo: credit
  def credit(request:TransactionRequest): Future[Transaction] = {
    //todo: validate account
     accountService.getByAccNumber(request.accNumber).map(x=>x).flatMap{
      y=>
        y match {
          case Some(account:Account) =>{
            //todo: Get Daily Transactions from this account
            val dailyTransnactions: mutable.Seq[Transaction] = getNumberOfDailyTransactions(request.accNumber, request.transactionDate.toString("yyyy-MM-d"), TransactionType.credit)
            val totalNumberofDeposits: Float = dailyTransnactions.map(record => record.amount).sum

            //todo: Max Deposits per Day
            if ((totalNumberofDeposits + request.amount) > Constants.MAX_DEPOSIT_FOR_DAY) {
              throw new RuntimeException("Exceeded Maximum Amount to Deposit per    day ")
            }
            //todo: Max Deposit per Transaction
            if (request.amount > Constants.MAX_DEPOSIT_PER_TRANSACTION) {
              throw new RuntimeException("Exceeded Maximum Amount to Deposit per   Transaction ")
            }
            //todo: Check the number fo daily transactions permitted
            if (dailyTransnactions.length >= Constants.MAX_DEPOSIT_PER_TRANSACTION) {
              throw new RuntimeException("Exceeded Maximum Number of deposits per day ")
            }


            //todo: Credit
            val transaction = Transaction(0L, account.accNumber, request.amount, TransactionType.credit.toString, request.transactionDate.toString("yyyy-MM-d"));
            transactionDAO.Create(transaction).map{
              response => response
            }

          }
          case None =>throw new NullPointerException("Account does not exist")
        }
    }


  }


  //todo: debit
  def debit(request:TransactionRequest): Future[Transaction] = {
    //todo: validate account
    accountService.getByAccNumber(request.accNumber).map(x=>x).flatMap{
      y=>
        y match {
          case Some(account:Account) =>{
            val dailyTransnactions: mutable.Seq[Transaction] = getNumberOfDailyTransactions(request.accNumber, request.transactionDate.toString("yyyy-MM-d"), TransactionType.debit)
            val totalNumberofWithdraws: Float = dailyTransnactions.map(record => record.amount).sum

            //todo: Max Withdraw per Day
            if ((totalNumberofWithdraws + request.amount) > Constants.MAX_WITHDRAW_FOR_DAY) {
              throw new RuntimeException("Exceeded Maximum Amount to withdraw per    day ")
            }
            //todo: Max Withdraw per Transaction
            if (request.amount > Constants.MAX_WITHDRAW_PER_TRANSACTION) {
              throw new RuntimeException("Exceeded Maximum Amount to withdraw per   Transaction ")
            }
            //todo: Check the number fo daily transactions permitted
            if (dailyTransnactions.length >= Constants.MAX_DEPOSIT_PER_TRANSACTION) {
              throw new RuntimeException("Exceeded Maximum Number of deposits per day ")
            }

            if (account.accBalance.get < request.amount) {
              throw new RuntimeException("Not enough funds to withdraw")
            }
            //todo: Debit
            val transaction = Transaction(0L, account.accNumber, request.amount, TransactionType.debit.toString, request.transactionDate.toString("yyyy-MM-d"));

            transactionDAO.Create(transaction).map{
              response => response
            }
            //todo: Update Absolute Balancne
          }
          case None =>throw new NullPointerException("Account does not exist")
        }
    }


  }

  //todo: Get Number of Daily Transactions
  def getNumberOfDailyTransactions(accNumber:String,transactionDate:String,transactionType: TransactionType.Value): mutable.Seq[Transaction] =
     transactionDAO.list(accNumber,transactionDate).filter(x=>x.transactionType==transactionType)


  def updateAbsoluteBalance(accountName:String): Future[Unit] ={
    val res:Seq[Transaction] = Await.result(transactionDAO.getAbsoluteBalance(accountName),Duration.Inf)

    if(res != null){
        var accountBalance:Float = 0

        res.foreach{
          x=> x.transactionType match {
            case "credit" => accountBalance += x.amount
            case "debit" =>  accountBalance -= x.amount
          }
        }
        accountService updateAccountBalance(accountName,Some(accountBalance))
    }

    Future.successful("Succesful Transaction")
  }




}
