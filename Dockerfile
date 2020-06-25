FROM openjdk:11

ARG JAR_FILE=target/ru.alpha.task3-1.0-SNAPSHOT.jar

ADD ${JAR_FILE} test_task.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "test_task.jar"]