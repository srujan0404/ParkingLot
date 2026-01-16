FROM maven:3.9-eclipse-temurin-20 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src
COPY checkstyle.xml .

RUN mvn clean package -DskipTests -B

FROM eclipse-temurin:20-jre

LABEL maintainer="ParkingLot DevOps Team"
LABEL description="ParkingLot Management System"
LABEL version="1.0"

RUN groupadd -r appgroup && useradd -r -g appgroup appuser

WORKDIR /app

COPY --from=builder /app/target/parking-lot-app.jar /app/parking-lot-app.jar

RUN chown -R appuser:appgroup /app

USER appuser

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD ps aux | grep -v grep | grep java || exit 1

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=50.0"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/parking-lot-app.jar"]
