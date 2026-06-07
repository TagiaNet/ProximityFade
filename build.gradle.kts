plugins {
    kotlin("jvm") version "2.3.21"
    id("com.gradleup.shadow") version "9.4.1"
    // Development server run tasks
    id("xyz.jpenilla.run-paper") version "2.3.1"
    // Generate plugin resources at build time
    id("xyz.jpenilla.resource-factory-paper-convention") version "1.3.1"
}

group = "net.tagia"
version = "1.0.2"
description = "Hide players from each other based on proximity."
paperPluginYaml {
    main = "net.tagia.proximityfade.ProximityFade"
    apiVersion = "26.1.2"
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
    compileOnly("io.papermc.paper:paper-api:26.1.2.build.53-stable")
    implementation("org.bstats:bstats-bukkit:3.0.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.github.retrooper:packetevents-spigot:2.12.2")
}

tasks.runServer {
    minecraftVersion("26.1.2")
    downloadPlugins {
//        modrinth("FastAsyncWorldEdit", "2.14.3")
//        modrinth("LuckPerms", "v5.5.17-bukkit")
    }
}

val targetJavaVersion = 25
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
