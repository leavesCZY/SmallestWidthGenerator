plugins {
    kotlin("jvm") version "1.9.23"
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