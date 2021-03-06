FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/telegrambot552.jar
ARG JAR_LIB_FILE=target/lib/

# cd /usr/local/runme
WORKDIR /usr/local/runme

# copy target/find-links.jar /usr/local/runme/app.jar
COPY ${JAR_FILE} telegrambot552.jar

# copy project dependencies
# cp -rf target/lib/  /usr/local/runme/lib
ADD ${JAR_LIB_FILE} lib/

# java -jar /usr/local/runme/app.jar
ENTRYPOINT ["java","-jar","telegrambot552.jar"]
CMD ["/TelegrammBot/entrypoint.sh"]