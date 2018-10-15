
# money-transfer

## Description
RESTful API for money transfers between accounts executable as a standalone program.

## Technologies:
JAX-RS

TomEE App Server

JPA

CDI

H2 Database

## To run:
```
build - mvn clean package tomee:exec
run - java -jar target/ROOT-exec.jar
```

## Availables services:

###### Add Account
**Path:**  http://localhost:8080/moneyTransfer/account

**Method:** POST

**Example:** 
```
{
	"number":"12345",
	"balance":"1000.50"
}
```
###### Get Account
**Path:**  http://localhost:8080/moneyTransfer/account/{accountNumber}

**Method:** GET

###### Withdraw
**Path:**  http://localhost:8080/moneyTransfer/account/withdraw/{accountNumber}/{amount}

**Method:** POST

###### Deposit
**Path:**  http://localhost:8080/moneyTransfer/account/deposit/{accountNumber}/{amount}

**Method:** POST

###### Transfer between accounts
**Path:**  http://localhost:8080/moneyTransfer/transfer

**Method:** PUT

**Example:** 
```
{
	"originAccount":"12345",
	"destinationAccount":"54321",
	"amount":"50.50"
}
```





