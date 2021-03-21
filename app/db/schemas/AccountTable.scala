package db.schemas
import db.tables.{Account, Transaction}
import slick.jdbc.H2Profile.api._

class AccountTable(tag: Tag)  extends Table[Account] (tag,"account"){
  def id = column[Int]("id",O.PrimaryKey,O.AutoInc)
  def accName = column[String]("accName")
  def amount = column[Float]("amount")
  def transactionType = column[String]("transactionType")
  def transactionDate = column[String]("transactionDate")

  override  def * = (id,accName,amount,transactionType,transactionDate).mapTo[Account]
}

