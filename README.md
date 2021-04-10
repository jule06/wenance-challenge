Overview:
    This REST Api was created for a wenance challenge.

    The application consumes an external service which returns the last bitcoin price,
    exposing some methods showing the price at any timestamp or getting average price between two timestamps

Local deploy:
    By Maven (needs to be installed):
        1) Open a terminal
        2) Step into wenance-challenge folder
        3) run package install
        4) java -jar target/wenance-challenge-1.0.jar
    By Intellij:
        1) File > Open > wenance-challenge
        2) Wait for the build and dependencies download time
        3) Run

Endpoints:
    *The application is configured to run in port 8080. It can be modified by changing the server.port value located in application.properties

    Endpoint that retrieves prices at any timestamp sending it as a parameter ("at") or get all prices saved in h2 database.

        -localhost:8080/bitcoin/price
        -localhost:8080/bitcoin/price?at=2021-04-07 22:11:40

    Endpoint that returns the bitcoin average price by two parameters "from" and "to", it's also brings the a percentage difference price between
    this average price and the max bitcoin price value saved in the h2 database at these moment.

        -localhost:8080/bitcoin/price/average?from=2021-04-07 22:11:40&to2021-04-09 23:55:51

Run Tests:
    1) Right click on src/main/test/java/com/challenge/wenance/test
    2) Run 'Test' in 'com.challenge.wenance.test'
    

Developed By Julian Lopez