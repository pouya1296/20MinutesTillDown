plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


    dependencies {
        implementation("com.badlogicgames.gdx:gdx:1.11.0")
        implementation("com.badlogicgames.gdx:gdx-backend-lwjgl:1.11.0")
        implementation("com.badlogicgames.gdx:gdx-platform:1.11.0:natives-desktop")
        implementation("org.xerial:sqlite-jdbc:3.40.1.0")
        implementation("org.hibernate:hibernate-core:6.2.0.Final")

    }

tasks.test {
    useJUnitPlatform()
}