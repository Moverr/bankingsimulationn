package helpers

object Constants {
  //todo: Rules applying to Deposit
  val MAX_DEPOSIT_FOR_DAY:Float = 150000
  val MAX_DEPOSIT_PER_TRANSACTION:Float = 150000
  val MAX_DEPOSIT_FREQUENCY_PER_DAY:Int = 4

  //todo: Rules applying to Withdraw
  val MAX_WITHDRAW_FOR_DAY:Float = 50000
  val MAX_WITHDRAW_PER_TRANSACTION:Float = 20000
  val MAX_WITHDRAW_FREQUENCY_PER_DAY:Int = 3
}
