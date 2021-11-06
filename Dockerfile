FROM openjdk:11-jre-stretch

WORKDIR /app

EXPOSE 9000 9090

ARG PROFILE
ENV ENV_PROFILE=$PROFILE

COPY build .

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "libs/demo-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod","-XX:MaxRAMPercentage=70","-XX:MinRAMPercentage=70"]
