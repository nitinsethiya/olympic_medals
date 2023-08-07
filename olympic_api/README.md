# olympic-api

API server to get medal data
The sever can be run by simply running ```lein run``` from the root directory of this project.

For now as a cors rule, i have only allowed http://localhost:8280 as the only origon to be accepted on which our API project runs.

This project is using leinegen template and if its not present the it can also be run using the jar file. I have also included the jar file in the source of this project

Please use ```java -jar olympic-api-0.1.0-standalone.jar ``` to run the server.

In order for the server to work, the medals data in a file ```data.json``` should be present in the same directory as the jar directory



