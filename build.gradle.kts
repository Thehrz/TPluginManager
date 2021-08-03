plugins {
    java
    id("io.izzel.taboolib") version "1.12"
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
    install("module-nms")
    install("module-nms-util")
    install("module-chat")
    install("module-lang")
    install("module-metrics")
    install("module-porticus")
    install("module-configuration")
    install("platform-bukkit", "platform-bungee", "platform-nukkit", "platform-velocity")
    install("platform-sponge-api7", "platform-sponge-api8")
    version = "6.0.0-pre25"
}


repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
    maven { url = uri("https://repo.codemc.org/repository/maven-public") }
}

dependencies {
    compileOnly("ink.ptms.core:v11600:11600:all")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib")
    compileOnly("me.clip:placeholderapi:2.10.9")
    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}