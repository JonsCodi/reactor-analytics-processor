  # JAVA 11 IMAGE ALPINE #
FROM alpine:latest AS ALPINE_OPENJDK11_IMAGE

RUN apk add openjdk11

RUN mkdir /app

ENV JAVA_HOME="/usr/lib/jvm/default-jvm/"
ENV PATH=$PATH:${JAVA_HOME}/bin
ENV GRADLE_VERSION 5.6
ENV APP_HOME=/app/
ENV ARTIFACT_NAME=reactor-analytics-processor-1.0.jar
ENV ARTIFACT=./build/libs/$ARTIFACT_NAME
ENV PATH=${PATH}:/gradle-${GRADLE_VERSION}/bin

# get gradle and supporting libs
RUN apk -U add --no-cache curl; \
    curl https://downloads.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip > gradle.zip; \
    unzip gradle.zip; \
    rm gradle.zip; \
    apk del curl; \
    apk update && apk add --no-cache libstdc++ && rm -rf /var/cache/apk/*


COPY . ./
RUN gradle clean build
COPY $ARTIFACT ${APP_HOME}
WORKDIR $APP_HOME
EXPOSE 8080

ENTRYPOINT exec java -jar ${ARTIFACT_NAME}