FROM maven:3.8.3-openjdk-16-slim AS build
ARG srcDir=/project
RUN mkdir ${srcDir}
WORKDIR ${srcDir}
# ADD pom.xml ${srcDir}
# RUN mvn verify --fail-never
ADD . ${srcDir}
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests
RUN ls ${srcDir}/target

FROM adoptopenjdk/openjdk16:x86_64-alpine-jdk-16.0.2_7-slim

ARG destDir=/app
RUN mkdir ${destDir}
# RUN echo $(ls ${srcDir})
COPY --from=build /project/target/discovery-server-0.0.1-SNAPSHOT.jar ${destDir}/eureka-server.jar
EXPOSE 8000

WORKDIR ${destDir}

ENTRYPOINT [ "java", "-jar", "eureka-server.jar" ]

# docker build -t eureka-server:0.0.1 . --progress=plain

