plugins {
    java
    id("io.izzel.taboolib") version "1.18"
    id("org.jetbrains.kotlin.jvm") version "1.5.10"
}

taboolib {
    description {
        contributors {
            name("Thehrz")
        }
    }
    install("common")
    install("common-5")
    install("module-ui")
    install("module-chat")
    install("module-lang")
    install("module-metrics")
    install("module-configuration")
    install("platform-bukkit", "platform-bungee", "platform-nukkit", "platform-velocity")
    install("platform-sponge-api7", "platform-sponge-api8")
    version = "6.0.0-pre50"
}


repositories {
    mavenCentral()
    maven { url = uri("https://repo.codemc.org/repository/maven-public") }
//    maven { url = uri("https://repo.spongepowered.org/maven") }
    maven { url = uri("https://repo.nukkitx.com/maven-snapshots") }
//    maven { url = uri("https://nexus.velocitypowered.com/repository/maven-public/") }
}

dependencies {
    compileOnly("ink.ptms.core:v11600:11600:all")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib")
    compileOnly("cn.nukkit:nukkit:2.0.0-SNAPSHOT")
//    compileOnly("net.md_5.bungee:BungeeCord:1:all")
//    compileOnly("org.spongepowered:spongeapi:7.2.0") {
//        exclude("org.apache.logging.log4j")
//    }
//    compileOnly("org.spongepowered:spongeapi:8.0.0-SNAPSHOT")
//    compileOnly("com.velocitypowered:velocity-api:1.1.8")
    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}