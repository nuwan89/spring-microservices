FROM localhost:33000/maven:3.8.3-openjdk-16-slim AS build
ARG srcDir=/project
ARG buildMode=dev
RUN mkdir ${srcDir}
WORKDIR ${srcDir}
# ADD pom.xml ${srcDir}
# RUN mvn verify --fail-never
ADD . ${srcDir}
# RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests
RUN --mount=type=cache,target=/root/.m2 .build/maven-build.sh ${buildMode}
RUN ls ${srcDir}/target

FROM localhost:33000/adoptopenjdk/openjdk16:x86_64-alpine-jdk-16.0.2_7-slim

ARG destDir=/app
RUN mkdir ${destDir}
# RUN echo $(ls ${srcDir})
COPY --from=build /project/target/account-manager-0.0.1-SNAPSHOT.jar ${destDir}/account-manager.jar
EXPOSE 8002

WORKDIR ${destDir}

# ENV SPRING_PROFILES_ACTIVE=${buildMode}

ENTRYPOINT [ "java", "-jar", "account-manager.jar" ]

# docker build -t account-manager:0.0.1 . --progress=plain

