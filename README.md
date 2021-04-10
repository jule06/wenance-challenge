Overview:
    
    *This application was created for a wenance challenge.
    
    The application consumes an external service that returns the real time bitcon price, 
    saving it in a memory DB, exposing some methods to get this information filtered by timestamps.

Endpoints:
    
    *The application is configured to run under 8080 port. 
    It can be modified by changing the server.port value located in application.properties

    Endpoint that retrieves prices at any timestamp sending it as a parameter ("at") or 
    get all prices saved in h2 database.

        -localhost:8080/bitcoin/price
        -localhost:8080/bitcoin/price?at=2021-04-07 22:11:40

    Endpoint that returns the bitcoin average price by two parameters "from" and "to", 
    it also brings the a percentage difference price between 
    this average price and the max bitcoin price value saved these moment.

        -localhost:8080/bitcoin/price/average?from=2021-04-07 22:11:40&to2021-04-09 23:55:51

Local deploy:
    
    By Intellij:
        1) File > Open > wenance-challenge
        2) Wait for the build and dependencies download time
        3) Run
    By Maven (needs to be installed):
        1) Open a terminal
        2) Step into wenance-challenge folder
        3) run mvn package install
        4) java -jar target/wenance-challenge-1.0.jar


Run Tests:
    
    By Intellij:
        1) Right click on src/main/test/java/com/challenge/wenance/test
        2) Run 'Test' in 'com.challenge.wenance.test'
    By Maven:
        1) Open a terminal
        2) Step into wenance-challenge folder
        3) run mvn test
        
    
