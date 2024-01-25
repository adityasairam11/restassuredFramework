# restassured-framework
* This repo holds a working example on automating REST APIs.
* Endpoint Used: https://reqres.in/ (Sample tests covering POST, GET and DELETE HTTP calls added)
## Requirements:
* Java: >= 14
*	Maven build tool
*	Junit5
*	Allure Reporting
## About the Project:
### src/main/resources/config/QA.properties : Environment Properties File
#### API host, emails, passwords which corresponds to QA environment to be maintained in the above file. If users work on multiple environments, it is advised to create a property file dedicated to the corresponding environment.
*	API host (base URI)
*	Credentials
### src/main/java/com/restassured/util/APIUtils.java : API Utility File
#### Above class has the following utility functions:
*	requestBuilder – This method handles API Request Specification - host Name, API Content Type, header parameters and query parameters to be provided.
*	responseBuilder – This method handles API Response Specification - HTTP action, payload, resource path, expected response code to be provided.
*	validateJsonSchema – This method invokes a Hamcrest matcher that can be used to validate that a JSON document matches a given JSON schema.
*	getAPIResponse – This method returns the API response body.
*	getJsonPath – This method is used to parse the API response and extract the required result.
## Run test:
*	mvn clean test (to run generic tests)
*	mvn clean test -Psystem-tests (-Psystem-tests profile has been created, multiple profiles can be created based on test needs.)
Check Test Report:
### Pre-requisite - ALLURE to be installed.
#### Run the below command in MAC:
~~~
brew install allure
~~~
#### Run the below command in Windows:
~~~
scoop install allure
~~~
#### How to generate test report:
##### Running the below command will create the report results (json file) and store them in allure-reports folder
~~~
mvn clean test
~~~
##### Running the below command will generate the HTML report and open it in the default browser
~~~
allure serve
~~~