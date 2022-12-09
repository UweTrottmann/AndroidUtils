// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // https://github.com/ben-manes/gradle-versions-plugin/releases
    id("com.github.ben-manes.versions") version "0.43.0"
    // https://github.com/gradle-nexus/publish-plugin/releases
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

buildscript {
    val kotlin_version by extra("1.6.10")
    val dokka_version by extra("1.6.10")

    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:$dokka_version")
    }
}

nexusPublishing {
    packageGroup.set("com.uwetrottmann")
    repositories {
        sonatype {
            if (rootProject.hasProperty("SONATYPE_NEXUS_USERNAME")
                && rootProject.hasProperty("SONATYPE_NEXUS_PASSWORD")) {
                username.set(rootProject.property("SONATYPE_NEXUS_USERNAME").toString())
                password.set(rootProject.property("SONATYPE_NEXUS_PASSWORD").toString())
            }
        }
    }
}

// reject preview releases for dependencyUpdates task
fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

tasks.register("clean", Delete::class) {
    group = "build"
    delete(rootProject.buildDir)
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}
