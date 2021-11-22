# Shorten Link Web App
Spring Boot and Angular based web application which takes a HTTP or HTTPs URL and generates a hash which still direct to the required page. 
# Getting Started
## Prerequisites

### Backend
- Java 
- Spring Boot
- Maven
- H2 Database
- Lombok

### Frontend
- Node js
- Angular-Cli
- npm
#### Tools
- Eclipse or IntelliJ IDEA (recommended) or any preferred IDE with embedded Maven **(For Backend)**
- Maven **(For Backend)**
- Postman (or any RESTful API testing tool) **(For Backend)**
- Visual Studio Code (recomended) or any code editor for web application **(For Frontend)**
## How to Run
#### 1. With Docker (Pull images from docker hub)(Fastest Way)
using terminal navigate to docker-deployment folder and run below command
> docker-compose up -d
#### 2. With Docker (Build images locally)
using terminal navigate to root folder and run below commands
> docker-compose build

> docker-compose up -d
#### 3. Without Docker
##### Backend
- ``` mvn clean install ``` **to clean if exists files and install packages**
- ``` mvn spring-boot:run ``` **to start spring boot**
##### Frontend
- ``` ng serve ``` **to build and start web app**
#### Application URLs
- After deployment with docker web application is accessible on [http://localhost:80/](http://localhost:80/)
- After deployment without docker web application application is accessible on [http://localhost:4200/](http://localhost:4200/)
- API endpoints are accessible at [http://localhost:8080/](http://localhost:8080/)
## Shortening URL Technique
- For all input urls hashes of length 6 are generating and storing them in H2 database. The length of hashes is customizable using property file. 
## Limitation and Future Enhancement
Since this app is developed for demo purpose only, so this application have limitations there are many new features can be added in it.
- API endpoints are not secured. Using spring security api endpoints can be secured
- Currently there is no health check endpoint available. Using sprint actuator it can be implemented easily.
- Application is using H2 Databse which is in memory database and good for quick demo projects. But not for production ready project because it gets flushed from memory, once the application stops. So it can be replaceable easily with MYSQL database.   
- User experience can be improved by adding on screen error/success messages
- UI can be imroved using CSS
- More techniques/Algorithms can be explored to improve URL shortening.
