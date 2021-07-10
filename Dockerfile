  
FROM openjdk:11
EXPOSE 5005
EXPOSE 8080
COPY target/recycle-0.0.1.jar recycle-0.0.1.jar
ENTRYPOINT ["java","-jar","/recycle-0.0.1.jar"]





