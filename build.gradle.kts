plugins {
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

javafx {
    version = "21.0.12"
    modules = listOf("javafx.controls")
}

application {
    mainModule.set("com.example.hellofx")
    mainClass.set("com.example.hellofx.Main")
}