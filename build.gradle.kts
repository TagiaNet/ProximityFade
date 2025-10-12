plugins {
    kotlin("jvm") version "2.1.20"
    id("com.gradleup.shadow") version "8.3.0"
    // Development server run tasks
    id("xyz.jpenilla.run-paper") version "2.3.1"
    // Generate plugin resources at build time
    id("xyz.jpenilla.resource-factory-paper-convention") version "1.3.1"
}

group = "net.tagia"
version = "1.0.0"
description = "Hide players from each other based on proximity."
paperPluginYaml {
    main = "net.tagia.proximityfade.ProximityFade"
    apiVersion = "1.21.8"
    website = "https://github.com/TagiaNet/ProximityFade"
}


repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://repo.codemc.io/repository/maven-releases/") {
        name = "packetevents-repo"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.10-R0.1-SNAPSHOT")
    implementation("org.bstats:bstats-bukkit:3.0.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.github.retrooper:packetevents-spigot:2.9.5")
}

tasks.runServer {
    minecraftVersion("1.21.8")
    downloadPlugins {
        modrinth("FastAsyncWorldEdit", "2.13.1")
        modrinth("LuckPerms", "v5.5.0-bukkit")
        modrinth("AntiPopup", "12.1")
    }
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.shadowJar {
    relocate("com.github.retrooper.packetevents", "net.tagia.proximityfade.libs")
    relocate("io.github.retrooper.packetevents", "net.tagia.proximityfade.libs")
    relocate("org.bstats", "net.tagia.proximityfade.libs")
}

tasks.processResources {
    filteringCharset = "UTF-8"
}
