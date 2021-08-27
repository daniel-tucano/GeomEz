import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
    id("org.openjfx.javafxplugin") version "0.0.10"
}

group = "me.daanr"
version = "1.0-SNAPSHOT"


//val swtNatives = when ( OperatingSystem.current() ) {
//    OperatingSystem.WINDOWS -> "org.eclipse.swt.win32.win32.x86_64"
//    OperatingSystem.LINUX -> "org.eclipse.swt.gtk.linux.x86_64"
//    else -> "org.eclipse.swt.cocoa.macosx.x86_64"
//}

configurations.all {
    resolutionStrategy {
        dependencySubstitution {
            // The maven property ${osgi.platform} is not handled by Gradle
            // so we replace the dependency, using the osgi platform from the project settings
            val os = System.getProperty("os.name").toLowerCase()
            if (os.contains("windows")) {
                substitute(module("org.eclipse.platform:org.eclipse.swt.\${osgi.platform}")).with(module("org.eclipse.platform:org.eclipse.swt.win32.win32.x86_64:3.108.0"))
            }
            else if (os.contains("linux")) {
                substitute(module("org.eclipse.platform:org.eclipse.swt.\${osgi.platform}")).with(module("org.eclipse.platform:org.eclipse.swt.gtk.linux.x86_64:3.108.0"))
            }
            else if (os.contains("mac")) {
                substitute(module("org.eclipse.platform:org.eclipse.swt.\${osgi.platform}")).with(module("org.eclipse.platform:org.eclipse.swt.cocoa.macosx.x86_64:3.108.0"))
            }
        }
    }
}

repositories {
    mavenCentral()
    maven("http://maven.jzy3d.org/releases/")
    maven("http://maven.jzy3d.org/snapshots/")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")


    implementation("org.jetbrains.lets-plot:lets-plot-jfx:2.1.0")
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin-jvm:3.0.3-alpha1")

    implementation("org.jzy3d:jzy3d-all:2.0.0-SNAPSHOT")
    implementation("org.jzy3d:jzy3d-native-jogl-swt:2.0.0-SNAPSHOT")
    implementation("org.eclipse.platform:org.eclipse.swt.win32.win32.x86_64:3.109.0")

    implementation("org.slf4j:slf4j-simple:1.7.30")

    implementation(project(":geomez-core"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}