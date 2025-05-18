FROM gradle:8.6-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle build -x test

FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENV SMPP_HOST=smpp.server.host
ENV SMPP_PORT=2775
ENV SMPP_SYSTEM_ID=testUser
ENV SMPP_PASSWORD=testPass
ENV JAVA_OPTS=""

EXPOSE 9898
EXPOSE 9090

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]