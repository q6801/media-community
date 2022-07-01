FROM openjdk:11-jdk-slim
ARG JAR_FILE=build/libs/media-community-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar
COPY media-community-cert/keystore.p12 /var/jenkins_home/workspace/media-community/media-community-cert/keystore.p12
ENTRYPOINT java -Dkms.url=ws://13.209.253.79:8888/kurento -jar /app.jar --jasypt.encryptor.password=$pwd --spring.profiles.active=prod