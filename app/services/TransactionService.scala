package services

import controllers.requests.TransactionRequest
import daos.TransactionDAO
import db.tables.{Account, Transaction}
import helpers.TransactionType
import javax.inject.{Inject, Singleton}
import org.joda.time.DateTime

@Singleton
class TransactionService @Inject()(
                                    accountService: AccountService,transactionDAO: TransactionDAO){

  //todo: debit
  def debit(request:TransactionRequest): List[Transaction] ={
    //todo: validate account
    accountService.getByAccNumber(request.accNumber) match {
      case Some(account: Account) =>{
        //todo: Debit
        val transaction = Transaction(account.accNumber,request.amount,TransactionType.debit.toString,DateTime.now(),DateTime.now());
        transactionDAO.Create(transaction)
        //todo: Do Reconnciliatioon
         Seq[Transaction](transaction).toList
      }
      case None => throw new NullPointerException("Account does not exist")
    }


  }

  //todo: credit
  def credit(request:TransactionRequest): List[Transaction] ={
    //todo: validate account
    accountService.getByAccNumber(request.accNumber) match {
      case Some(account: Account) =>{
        //todo: Debit
        val transaction = Transaction(account.accNumber,request.amount,TransactionType.credit.toString,DateTime.now(),DateTime.now());
        transactionDAO.Create(transaction)
        //todo: Do Reconnciliatioon
        Seq[Transaction](transaction).toList
      }
      case None => throw new NullPointerException("Account does not exist")
    }
  }

  //todo: view transaction based on date and time

}
