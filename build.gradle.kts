plugins {
    application
    alias(libs.plugins.gitSemVer)
    alias(libs.plugins.javafx)
}

group = "it.unibo.smartgh"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

javafx {
    version = "16"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation(libs.bundles.vertx.dependencies)
    implementation(libs.gson)
    testImplementation(libs.junit.api)
    testRuntimeOnly(libs.junit.engine)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application{
    mainClass.set("it.unibo.smartgh.Launcher")
}