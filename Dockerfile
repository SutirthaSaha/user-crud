FROM openjdk:11
EXPOSE 8090
ADD target/user-crud.jar user-crud.jar
ENTRYPOINT ["java","-jar","/user-crud.jar"]