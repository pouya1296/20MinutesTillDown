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
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:1.11.0")
    implementation("com.badlogicgames.gdx:gdx-platform:1.11.0:natives-desktop")
    implementation("org.xerial:sqlite-jdbc:3.40.1.0")
    implementation("org.hibernate:hibernate-core:6.2.0.Final")
    implementation("com.google.code.gson:gson:2.10.1")

}

tasks.test {
    useJUnitPlatform()
}
tasks.withType<JavaExec> {
    jvmArgs("--add-opens", "java.base/java.nio=ALL-UNNAMED")
    jvmArgs("--add-opens", "java.base/java.io=ALL-UNNAMED")
    jvmArgs("--add-opens=java.base/jdk.internal.ref=ALL-UNNAMED")
}
