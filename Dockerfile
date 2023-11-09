FROM khipu/openjdk17-alpine

RUN mkdir /app

COPY target/walletBankTransferAPI-0.0.1-SNAPSHOT.jar /app/walletBankTransferAPI-0.0.1-SNAPSHOT.jar

EXPOSE 8080

WORKDIR /app

CMD ls -l && java -jar walletBankTransferAPI-0.0.1-SNAPSHOT.jar

