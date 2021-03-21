package services

import controllers.requests.TransactionRequest
import daos.TransactionDAO
import db.tables.{Account, Transaction}
import helpers.TransactionType
import javax.inject.{Inject, Singleton}


import scala.concurrent.Future

@Singleton
class TransactionService @Inject()(  accountService: AccountService,transactionDAO: TransactionDAO){

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

  //todo: Do Reconciliation
  def reconcile(accNumber:String): Unit ={

  }

  //todo: credit
  def credit(request:TransactionRequest): Future[Transaction] ={
    //todo: validate account
    val responsne = accountService.getByAccNumber(request.accNumber)
    responsne match {
      case Some(account: Account) =>{
        //todo: Debit
        val transaction = Transaction(account.accNumber,request.amount,TransactionType.credit.toString,request.transactionDate.toString("yyyy-MM-d"));
        transactionDAO.Create(transaction)
        //todo: Do Reconnciliation
        Future.successful(transaction)
      }
      case None => throw new NullPointerException("Account does not exist")
    }
  }

  //todo: view transaction based on date and time

}
