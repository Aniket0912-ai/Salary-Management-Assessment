FROM maven:3.9.9-eclipse-temurin-25 AS build
WORKDIR /workspace

COPY backend/pom.xml ./backend/pom.xml
RUN mvn -f ./backend/pom.xml -B -q dependency:go-offline

COPY backend/src ./backend/src
RUN mvn -f ./backend/pom.xml -B -DskipTests package

FROM eclipse-temurin:25-jre-jammy
WORKDIR /app

COPY --from=build /workspace/backend/target/*.jar /app/app.jar

ENV JAVA_OPTS=""
EXPOSE 8081
CMD ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar --server.port=${PORT:-8081}"]
