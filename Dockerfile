FROM openjdk:11.0-jre-sid

COPY ./target/drone-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["/bin/sh", "-c", "java $JAVA_OPTIONS -jar drone-0.0.1-SNAPSHOT.jar"]
