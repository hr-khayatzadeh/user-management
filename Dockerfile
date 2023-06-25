FROM amazoncorretto:17-alpine
EXPOSE 8080 9001
COPY target/user-management-1.0.jar user-management.jar
ENTRYPOINT ["java", "-jar", "/user-management.jar"]