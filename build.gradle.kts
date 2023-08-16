// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.library") version "8.1.0" apply false
    kotlin("android") version "1.7.21" apply false // Can be compiled with at least Kotlin 1.6
    // https://github.com/ben-manes/gradle-versions-plugin/releases
    id("com.github.ben-manes.versions") version "0.43.0"
    // https://github.com/gradle-nexus/publish-plugin/releases
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

nexusPublishing {
    packageGroup.set("com.uwetrottmann")
    this.repositories {
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
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
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
