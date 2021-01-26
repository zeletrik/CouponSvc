FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /opt/coupon-service

COPY coupon-service.jar service.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","service.jar"]