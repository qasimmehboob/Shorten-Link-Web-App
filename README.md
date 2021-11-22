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
#### 1. With Docker (Pull images from docker hub)
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
- **After deployment with docker application URL [http://localhost:80/](http://localhost:80/)**
- **After deployment without docker application URL [http://localhost:4200/](http://localhost:4200/)**

