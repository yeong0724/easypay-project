# ./gradlew docker : root 하위에 있는 module 전부 docker image 생성
FROM openjdk:17-jdk-slim
EXPOSE 8080
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

