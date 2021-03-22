package services

import controllers.requests.TransactionRequest
import daos.TransactionDAO
import db.tables.{Account, Transaction}
import helpers.{Constants, TransactionType}
import javax.inject.{Inject, Singleton}

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

@Singleton
class TransactionService @Inject()(  accountService: AccountService,transactionDAO: TransactionDAO){

  //todo: credit

  def credit(request:TransactionRequest): Future[Transaction] = {
    //todo: validate account
     val response = accountService.getByAccNumber(request.accNumber)
    response.map(x=>x).flatMap{
      y=>
        y match {
          case Some(account:Account) =>{
            //todo: Get Daily Transactions from this account
            val dailyTransnactions: Seq[Transaction] = Await.result(getNumberOfDailyTransactions(request.accNumber, request.transactionDate.toString("yyyy-MM-d"), TransactionType.credit.toString),Duration.Inf)
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
            if (dailyTransnactions.length >= Constants.MAX_DEPOSIT_FREQUENCY_PER_DAY) {
              throw new RuntimeException("Exceeded Maximum Number of deposits per day ")
            }


            //todo: Credit
            val transaction = Transaction(0L, account.accNumber, request.amount, TransactionType.credit.toString, request.transactionDate.toString("yyyy-MM-d"));
            transactionDAO.Create(transaction).map{
              response => {
                updateAbsoluteBalance(account.accNumber)
                response
              }
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
            val dailyTransnactions: Seq[Transaction] = Await.result(getNumberOfDailyTransactions(request.accNumber, request.transactionDate.toString("yyyy-MM-d"), TransactionType.debit.toString),Duration.Inf)
            val totalNumberofWithdraws: Float = dailyTransnactions.map(record => record.amount).sum

            //todo: Max Withdraw per Day
            if ((totalNumberofWithdraws + request.amount) > Constants.MAX_WITHDRAW_FOR_DAY) {
             Future.successful(throw new RuntimeException("Exceeded Maximum Amount to withdraw per    day "))
            }
            //todo: Max Withdraw per Transaction
            if (request.amount > Constants.MAX_WITHDRAW_PER_TRANSACTION) {
              Future.successful(throw new RuntimeException("Exceeded Maximum Amount to withdraw per   Transaction "))
            }
            //todo: Check the number fo daily transactions permitted
            if (dailyTransnactions.length >= Constants.MAX_WITHDRAW_FREQUENCY_PER_DAY) {
              Future.successful(throw new RuntimeException("Exceeded Maximum Number of withdraws per day "))
            }

            if (account.accBalance.get < request.amount) {
              Future.successful(throw new RuntimeException("Not enough funds to withdraw"))
            }
            //todo: Debit
            val transaction = Transaction(0L, account.accNumber, request.amount, TransactionType.debit.toString, request.transactionDate.toString("yyyy-MM-d"));

            transactionDAO.Create(transaction).map{
              response => {
                updateAbsoluteBalance(account.accNumber)
                response
              }
            }
            //todo: Update Absolute Balancne

          }
          case None =>throw new NullPointerException("Account does not exist")
        }
    }


  }

  //todo: Get Number of Daily Transactions
  def getNumberOfDailyTransactions(accNumber:String,transactionDate:String,transactionType: String): Future[Seq[Transaction]] =
     transactionDAO.list(accNumber,transactionDate,transactionType).map(x=>x)


  def updateAbsoluteBalance(accountName:String): Unit ={
    val res:Seq[Transaction] = Await.result(transactionDAO.getAbsoluteBalance(accountName),Duration.Inf)

    if(res != null){
        var accountBalance:Float = 0

        res.foreach{
          x=> x.transactionType match {
            case "credit" => accountBalance += x.amount
            case "debit" =>  accountBalance -= x.amount
          }
        }
        accountService.updateAccountBalance(accountName,Some(accountBalance))
    }

  }




}
