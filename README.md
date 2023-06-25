# Code Challenge
#### A coding/design challenge to provide a User Data Service to other teams in the company. The service should offer the common CRUD operations. It should be also possible to list the users and search for user names.
| Property      | DataType                   |
|---------------|----------------------------|
| user ID       | provided as an UUID        |
| username      | Up to your decision        |
| firstName     | Up to your decision        |
| lastName      | Up to your decision        |
| address       | Up to your decision        |
| postalCode    | Up to your decision        |
| countryCode   | ISO 3166-1 alpha-2 country |

## Author
Hamidreza Khayatzadeh [linkedin]

## Description
The service should offer the basic CRUD operations for users.  
Provide a concept on how you would setup CI/CD.  
Provide a simple concept how you would run and deploy the service in a cloud of your choice (AWS, GCP, Azure, etc.)


## How to Build
It is a maven project then to build, please run
```
mvn clean install 
```

## How to Test
To start the tests, please run
```
mvn test 
```

## How to Run the App
```
docker-compose up --build -d 
```
The APIs are available via [ApiLink]

## How to Stop the App
```
docker-compose down
```
## Database
H2 File System for application  
H2 Memory for test

## Postman Collection
For the sake of clarification, it is also possible to import **user-management.postman_collection.json** file in Postman application in order to call the APIs

## Deployment Pipeline
The deploy-branch Github workflow is just to demonstrate the general process and approach to define a CI/CD pipeline in order to register and deploy the application in AWS  

## License
**Free Software**

[linkedin]: <https://www.linkedin.com/in/hamidreza-khayatzadeh/>
[APILink]: http://localhost:8080/swagger-ui.html