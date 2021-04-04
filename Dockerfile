
FROM openjdk:11
ARG PROFILE
ARG ADDITIONAL_OPTS

ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}

WORKDIR /apt/recycle

COPY /target/recycle*.jar recycle.jar

SHELL ["/bin/sh", "-c"]

EXPOSE 8080

CMD java ${ADDITIONAL_OPTS} -jar recycle.jar --spring.profile.active=${PROFILE}