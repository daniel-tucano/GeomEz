import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
}

group = "me.daanr"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api("space.kscience:kmath-core:0.2.1")
    implementation("space.kscience:kmath-ejml:0.2.1")
    implementation("org.ejml:ejml-simple:0.41")

    implementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation(kotlin("test"))
    implementation(kotlin("script-runtime"))

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}