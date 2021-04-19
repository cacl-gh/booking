FROM openjdk:16-alpine
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} booking.jar
ENTRYPOINT ["java", "-jar", "booking.jar"]