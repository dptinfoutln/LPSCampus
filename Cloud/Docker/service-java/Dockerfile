FROM openjdk:8-jre-alpine

RUN mkdir /usr/server

WORKDIR /usr/server

COPY . .

CMD ["java", "-jar", "EchoServer.jar"]

