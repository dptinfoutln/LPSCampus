# source: https://stackoverflow.com/questions/27767264/how-to-dockerize-maven-project-and-how-many-ways-to-accomplish-it
#
# Build stage
#
FROM maven:3.6.0-jdk-8-slim AS build
COPY Code/Java/Serveur/src /home/app/src
COPY Code/Java/Serveur/pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:8-jre-alpine
COPY --from=build /home/app/target/LPS.jar /usr/local/lib/LPS.jar

EXPOSE 9998
CMD ["java","-jar","/usr/local/lib/LPS.jar"]
