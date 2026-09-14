FROM eclipse-temurin:25-jdk-jammy
WORKDIR /app

# Use Maven to build the application
COPY backend/pom.xml ./
COPY backend/.mvn .mvn
COPY backend/mvnw mvnw
RUN chmod +x mvnw || true
RUN ./mvnw -B -DskipTests package || true

# Copy the full backend source and build
COPY backend/ ./
RUN ./mvnw -B -DskipTests package

ENV JAVA_OPTS=""
EXPOSE 8081
CMD ["sh", "-c", "java $JAVA_OPTS -jar target/*.jar --server.port=${PORT:-8081}"]
