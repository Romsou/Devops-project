FROM openjdk:11-jdk
ADD /target/Devops-project-1.0-SNAPSHOT.jar demo.jar
ENTRYPOINT ["java","-jar","demo.jar"]
