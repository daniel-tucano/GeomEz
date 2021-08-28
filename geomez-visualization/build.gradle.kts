import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
    id("org.openjfx.javafxplugin") version "0.0.10"
}

group = "me.daanr"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
    maven("http://maven.jzy3d.org/releases/")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")


    implementation("org.jetbrains.lets-plot:lets-plot-jfx:2.1.0")
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin-jvm:3.0.3-alpha1")

    implementation("org.jzy3d:jzy3d-api:1.0.2")

    implementation("com.github.sh0nk:matplotlib4j:0.5.0")

    implementation("org.slf4j:slf4j-simple:1.7.30")

    implementation(project(":geomez-core"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}