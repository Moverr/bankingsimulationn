package db.tables

import org.joda.time.DateTime

case class Transaction(id:Long,accNumber:String, amount:Float, transactionType:String, transactionDate:String)

