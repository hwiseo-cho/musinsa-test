FROM ubuntu:22.04

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk && \
    apt-get install -y redis-tools && \
    apt-get clean;

WORKDIR /app

COPY build/libs/Musinsa-0.0.1.jar /app/app.jar

CMD ["java", "-jar", "app.jar"]