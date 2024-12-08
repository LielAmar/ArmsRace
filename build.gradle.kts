import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    java
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "com.lielamar.armsrace"
version = "1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.dmulloy2.net/nexus/repository/public/")
}

dependencies {
    // Minecraft Dependencies
    compileOnly("org.spigotmc:spigot:1.20.6-R0.1-SNAPSHOT")
    // Maven dependencies
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
    testCompileOnly("org.projectlombok:lombok:1.18.34")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.34")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("org.jetbrains:annotations:24.0.0")
    implementation("com.github.cryptomorin:XSeries:12.0.0")
    implementation("xyz.xenondevs:particle:1.8.1")
}

tasks.shadowJar {
    minimize()
    archiveBaseName.set("ArmsRace")
    archiveVersion.set("")
    archiveClassifier.set("")
    relocate("com.cryptomorin.xseries", "com.lielamar.armsrace.libs.xseries")
    relocate("xyz.xenondevs.particle", "com.lielamar.armsrace.libs.particle")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.processResources {
    from(sourceSets.main.get().resources.srcDirs) {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        include("**/*.yml")
        filter<ReplaceTokens>("tokens" to mapOf("version" to project.version.toString()))
    }
}
