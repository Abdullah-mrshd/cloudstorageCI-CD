FROM openjdk:latest

WORKDIR /app

COPY . /app

ADD target/cloudstorage-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]

EXPOSE 80
