FROM openjdk:11-slim

COPY build/libs/app.jar /app/app.jar

ENTRYPOINT exec java $JAVA_OPTS -jar /app/app.jar
EXPOSE 8080