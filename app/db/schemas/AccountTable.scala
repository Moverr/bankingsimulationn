package db.schemas
import db.tables.{Account, Transaction}
import slick.jdbc.H2Profile.api._

class AccountTable(tag: Tag)  extends Table[Account] (tag,"account"){
  def id = column[Long]("id",O.PrimaryKey,O.AutoInc)
  def accName = column[String]("accName")
  def accNumber = column[String]("accNumber")
  def accBalance = column[Option[Float]]("accBalance")


  override  def * = (id,accName,accNumber,accBalance).mapTo[Account]
}

