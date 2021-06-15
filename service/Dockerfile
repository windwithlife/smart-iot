FROM java:8-jdk-alpine

COPY ./target/endPointModel-svc-1.0.0.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch endPointModel-svc-1.0.0.jar'

ENTRYPOINT ["java", "-jar", "endPointModel-svc-1.0.0.jar"]