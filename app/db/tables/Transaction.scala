package db.tables

import org.joda.time.DateTime

case class Transaction(accNumber:String, amount:Float, transactionType:String, transactionDate:String, dateCreated:String)

