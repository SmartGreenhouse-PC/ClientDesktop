plugins {
    java
    application
    jacoco
    alias(libs.plugins.shadowJar)
    alias(libs.plugins.gitSemVer)
    alias(libs.plugins.javafx)
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
            implementation("org.openjfx:javafx-$module:19:$platform")
        }
    }
    testImplementation(libs.awaitility)
    testImplementation(libs.bundles.testfx)
    testImplementation(libs.junit.api)
    testRuntimeOnly(libs.junit.engine)
    testImplementation("org.skyscreamer:jsonassert:1.5.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application{
    mainClass.set("it.unibo.smartgh.Launcher")
}
