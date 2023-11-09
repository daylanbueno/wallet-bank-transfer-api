# wallet-bank-transfer-api

This API consists of the user being able to withdraw their money from the wallet and transfer it to their bank.

To do this, we have to use the provided APIs (see the attached mocks) which include:
- Wallet transactions API that is used to update the wallet balance through top ups and withdraws operations. (The mock will not actually update the balance because it’s a mock)
- Payments is an external provider API that is used to execute the transfer between Ontop’s bank account and the users’ bank account, but it doesn’t update the wallet balance.Ontop’s bank account will always be the same but
different depending on the environment.
- Wallet balance API that is used to fetch the user’s balance. (The mock will always return the same balance)

The transaction must have:
-  A fee of 10% is applied when the transaction is carried out.
-  A status (we leave the decision of which statuses to use up to you).

## Technologies
- Java 17
- Spring boot 3.1.5
- Maven
- Lombok
- ModelMapper
- Postgres
- H2 database
- JUnit
- Git
- Docker
- docker-compose
 
## Requirements for execution
- Java
- Docker
- docker-compose



  ## How to run
  Once you have cloned the project
  
  - install
    ![mvn install](https://github.com/daylanbueno/wallet-bank-transfer-api/assets/17939912/3b69a709-a645-44aa-8505-87aa7796d3d7)
  - docker-compose
    ![docker-compose-up](https://github.com/daylanbueno/wallet-bank-transfer-api/assets/17939912/3cf195f2-b871-45e2-9efb-6413ce049ac2)
  - import collections
    ![import collections](https://github.com/daylanbueno/wallet-bank-transfer-api/assets/17939912/1eca203f-752a-4c58-b796-2b5536c78d1d)



  
