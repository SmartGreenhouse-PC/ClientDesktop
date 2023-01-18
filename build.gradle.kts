plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "5.2.0"
    alias(libs.plugins.gitSemVer)
}

group = "it.unibo.smartgh"
version = "0.1.0"
project.setProperty("mainClassName", "it.unibo.smartgh.Launcher")

repositories {
    mavenCentral()
}

// List of JavaFX modules you need. Comment out things you are not using.
val javaFXModules = listOf(
    "base",
    "controls",
    "fxml",
    "swing",
    "graphics"
)
// All required for OOP
val supportedPlatforms = listOf("linux", "mac", "win")

dependencies {
    implementation(libs.bundles.vertx.dependencies)
    implementation(libs.gson)
    // JavaFX: comment out if you do not need them
    for (platform in supportedPlatforms) {
        for (module in javaFXModules) {
            implementation("org.openjfx:javafx-$module:13:$platform")
        }
    }
    testImplementation(libs.junit.api)
    testRuntimeOnly(libs.junit.engine)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application{
    mainClass.set("it.unibo.smartgh.Launcher")
}
