# ── Stage 1: Build ──────────────────────────────────────────────────────────
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copy dependency descriptors first for layer caching
COPY pom.xml .
RUN mvn -B dependency:go-offline -q

# Copy source and build the fat JAR
COPY src ./src
RUN mvn -B -DskipTests package -q

# ── Stage 2: Runtime ─────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Non-root user for security
RUN addgroup -S chatrop && adduser -S chatrop -G chatrop
USER chatrop

COPY --from=build /app/target/chatrop-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
