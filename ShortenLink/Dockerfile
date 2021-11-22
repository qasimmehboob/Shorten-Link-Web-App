FROM openjdk:17
LABEL maintainer="qasim.mehboob@hotmail.com"
VOLUME /tmp
EXPOSE 8080
ADD target/shorten-link-api-0.0.1-SNAPSHOT.jar shorten-link-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","shorten-link-api-0.0.1-SNAPSHOT.jar"]
