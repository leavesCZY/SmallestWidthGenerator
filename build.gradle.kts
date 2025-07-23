plugins {
    kotlin("jvm") version "2.2.0"
}

group = "github.leavesczy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    testImplementation(kotlin("test"))
}