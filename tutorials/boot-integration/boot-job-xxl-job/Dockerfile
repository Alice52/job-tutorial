FROM openjdk:8u121-jre-alpine
VOLUME /tmp
ARG MODULE_NAME="boot-job-xxl-job"
ARG JASYPT_ENCRYPTOR_PASSWORD=""
ENV JAVA_OPTS=""

COPY target/${MODULE_NAME}-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080
# Build a shell script because the ENTRYPOINT command doesn't like using ENV
RUN echo -e "#!/bin/sh\njava ${JAVA_OPTS} -Djava.awt.headless=true -Djava.security.egd=file:/dev/./urandom -DJASYPT_ENCRYPTOR_PASSWORD=${JASYPT_ENCRYPTOR_PASSWORD} -Dsun.net.httpserver.nodelay=true -jar /app.jar" > ./entrypoint.sh
RUN chmod +x /entrypoint.sh
RUN echo $PWD
RUN cat ./entrypoint.sh

RUN ls -la
ENTRYPOINT ["/entrypoint.sh"]
