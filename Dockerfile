FROM openjdk:8
VOLUME /tmp
ADD ./target/pet-examle-0.0.1-SNAPSHOT.jar pets-service.jar
ENTRYPOINT ["java", "-jar", "/pets-service.jar"]