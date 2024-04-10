FROM maven:3.8.4-openjdk-17-slim as prapare

WORKDIR /app/source
COPY pom.xml .

# cache maven artifacts
RUN mvn dependency:go-offline -P allow-snapshots
#RUN mvn dependency:go-offline -o allow-snapshots
 #mvn dependency allow-snapshots

FROM prapare as build

COPY . .
RUN mvn package -Dmaven.test.skip -P allow-snapshots

FROM openjdk:17

WORKDIR /app

ARG USER="user"
ARG GROUP="group"

ENV USER ${USER}

COPY --from=build --chown=1001:80 /app/source/target/service-0.0.1-SNAPSHOT.jar /app/build/app.jar

COPY --chown=1001:80 ./docker/files/entrypoint.sh /app/

RUN chmod +x /app/entrypoint.sh

Workdir /app
EXPOSE 8080
USER ${USER}
ENTRYPOINT ["/app/entrypoint.sh"]
