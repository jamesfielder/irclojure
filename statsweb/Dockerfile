FROM java:8-alpine

COPY target/uberjar/statsweb.jar /statsweb/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/statsweb/app.jar"]
