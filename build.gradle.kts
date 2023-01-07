plugins {
    application
    alias(libs.plugins.gitSemVer)
}

group = "it.unibo.smartgh"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
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
    mainClass.set("it.unibo.smartgh.Main")
}