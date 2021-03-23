# Mini Banking Application
This application is a simuation for Deposit, Withdraw and Check Account balance
and rules that apply on number of transactions etc as stated in the Tala Project guide

### Features
    - Deposit
    - Withdraw
    - Check Balance
    
### Technonlogy
This appication has been built in scala and play framework mainly but below is a list of technologies used
    
    -Play Framework 2.8x
    -scalaVersion 2.13.3 
    -Slick DSL between Database and DAO
    -PostgresSql  for database
    -Play Spec for testing
    
  ### Installation 
- You will need postgres installed on your windows/linux machine
- Java version 1.8 or later
- Sbt 
- clone this repo and install packages by running <br/>
sbt on your cmd
- create database bankingapp 
- under database created bankingapp use the default schema public
- import the existing PGSQL file from scripts folder the file name is dbexport.pgsql
- Add Environment variables as stated in the application conf
        
        slick.dbs.default.profile="slick.jdbc.PostgresProfile$"
        slick.dbs.default.db.driver="org.postgresql.Driver"
        slick.dbs.default.db.url=${?DATABASE_URL} //"jdbc:postgresql://localhost:5432/bankingapp"
        slick.dbs.default.db.schema=${?DATABASE_SCHEMA} // "public"
        slick.dbs.default.db.user=${?DATABASE_USER} // "postgres"
        slick.dbs.default.db.password=${?DATABASE_PASSWORD} // "password"

- remember Sbt command wiil help prepare the application by installing depencies
- sbt test will run the automated tests
- sbt run will run the applicatioon to the default port of 9000. eg localhost:9000

### API Doc
 
 | Url | Method | Request Body | Response body |
 |-----|--------|--------------|---------------|
 |http://localhost:9000/deposit|POST|{"accnumber":"12345","amount":"4000","transaction_date":"2021-03-21"}| { "id": 47, "accNumber": "12345", "amount": 4000, "transactionType": "credit",  "transactionDate": "2021-03-21" }| 
 |http://localhost:9000/withdraw|POST|{"accnumber":"12345","amount":"4000","transaction_date":"2021-03-21"}| { "id": 47, "accNumber": "12345", "amount": 4000, "transactionType": "debit",  "transactionDate": "2021-03-21" }|
 |http://localhost:9000/balance/12345|GET| - | {  "id": 1,  "accName": "Muyinda Rogers", "accNumber": "12345", "accBalance": null }|
   
   Constraints.
   There are constraints and error messages that are thrown back and some are validation rules as stipulated in the guiding problem statment
   
 
 