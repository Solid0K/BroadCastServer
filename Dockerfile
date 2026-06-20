FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/BroadCastServer.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar","start"]