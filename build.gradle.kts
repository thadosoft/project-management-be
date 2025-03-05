plugins {
    java
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.flywaydb:flyway-core:11.0.0")
    implementation("org.flywaydb:flyway-mysql")

    implementation("org.mapstruct:mapstruct:1.6.3")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.1")

    implementation("org.springframework.security:spring-security-core:6.4.1")

    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

    implementation("org.springframework.security:spring-security-web:6.4.1")
    implementation("org.springframework.security:spring-security-config:6.4.1")

    implementation("jakarta.validation:jakarta.validation-api:3.1.0")

    implementation("mysql:mysql-connector-java:8.0.28")

    implementation("net.sf.jasperreports:jasperreports:6.21.3")
    implementation("net.sf.jasperreports:jasperreports-fonts:6.21.3")

    runtimeOnly("com.mysql:mysql-connector-j")

    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
