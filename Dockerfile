FROM gradle:jdk16 AS build-stage

ADD . /GithubRepositoryViewer

WORKDIR /GithubRepositoryViewer

RUN chmod +x gradlew && ./gradlew bootJar

FROM openjdk:16-jdk-alpine

VOLUME /tmp

WORKDIR /GithubRepositoryViewer

COPY --from=build-stage /GithubRepositoryViewer/build/libs/githubrepositoryviewer-0.0.1-SNAPSHOT.jar app.jar

RUN adduser -D appuser && chown appuser ./app.jar

USER appuser

EXPOSE 8080

CMD [ "sh", "-c", "java -Dserver.port=$PORT -Xmx300m -Xss512k -XX:CICompilerCount=2 -Dfile.encoding=UTF-8 -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom -jar ./app.jar" ]
