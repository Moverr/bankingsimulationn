package daos

import akka.http.scaladsl.model.DateTime
import db.tables.Transaction
import javax.inject.Singleton
 import  collection.mutable

@Singleton
class TransactionDAO {

  var transactions =  mutable.Seq[Transaction]()

  //todo: Add Transactiioon
  def Create(transaction:Transaction): Transaction ={
     transactions = transactions :+ transaction ;
     transaction
  }

  //todo: Get Transactioons
  def list(limit:Int,datetime:DateTime): mutable.Seq[Transaction] ={
    transactions
  }
}
