FROM maven:3.9.4-eclipse-temurin-21-alpine

WORKDIR /app

#COPY ./target/Docker_Example-0.0.1-SNAPSHOT.jar .
#
#CMD ["java" , "-jar" , "Docker_Example-0.0.1-SNAPSHOT.jar"]

#we first have to create the jar file of the project then build the image and then run the image

#docker run -p 8080:8080 image_name:v0.0.1 --> Command for port forwarding as without it , we will not be able to use our localhost


#Two ways of running docker file - 1) BY generating the jar file , 2) Maven Plugin

COPY .mvn/ .mvn

COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw" , "spring-boot:run"]