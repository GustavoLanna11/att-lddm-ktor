plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    kotlin("plugin.serialization") version "1.9.21"
    application
}

group = "com.fatec.att_lddm_ktor"
version = "1.0.0"

application {
    mainClass.set("com.fatec.att_lddm_ktor.ApplicationKt")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
    // 1. JSON
    implementation("io.ktor:ktor-server-content-negotiation-jvm:2.3.7")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.3.7")

    // 2. Banco de Dados
    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")

    // 3. Driver do PostgreSQL
    implementation("org.postgresql:postgresql:42.6.0")

    // 4. Swagger & OpenAPI
    implementation("io.ktor:ktor-server-swagger:2.3.7")
    implementation("io.ktor:ktor-server-openapi:2.3.7")

    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
}

// Garante o Java 21
kotlin {
    jvmToolchain(21)
}