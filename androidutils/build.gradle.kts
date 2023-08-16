plugins {
    id("com.android.library")
    kotlin("android")
    id("maven-publish")
    id("signing")
}

group = "com.uwetrottmann.androidutils"
version = "3.1.1-SNAPSHOT"

android {
    namespace = "com.uwetrottmann.androidutils"
    compileSdk = 34 // Android 14

    defaultConfig {
        minSdk = 15
        targetSdk = 34 // Android 14
    }

    compileOptions {
        encoding = "UTF-8"
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    lint {
        // for CI server (reports are not public)
        textReport = true
        // Note: do not use textOutput = file("stdout"), just set no file.
    }

    publishing {
        singleVariant("release") {
            withJavadocJar()
            withSourcesJar()
        }
    }
}

dependencies {
    // https://developer.android.com/jetpack/androidx/releases/core
    implementation("androidx.core:core-ktx:1.10.1")
    // https://developer.android.com/jetpack/androidx/releases/annotation
    implementation("androidx.annotation:annotation:1.6.0")
    // https://developer.android.com/jetpack/androidx/releases/loader
    implementation("androidx.loader:loader:1.1.0")
}

// Because the components are created only during the afterEvaluate phase, you must
// configure your publications using the afterEvaluate() lifecycle method.
publishing {
    publications {
        create<MavenPublication>("central") {
            // Because the components are created only during the afterEvaluate phase,
            // configure publications using the afterEvaluate() lifecycle method.
            afterEvaluate {
                // Applies the component for the release variant created in android block.
                from(components["release"])
            }

            artifactId = "androidutils"

            pom {
                name.set("AndroidUtils")
                description.set("Little helpers for Android development.")
                url.set("https://github.com/UweTrottmann/AndroidUtils/api")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("uwetrottmann")
                        name.set("Uwe Trottmann")
                    }
                }

                scm {
                    connection.set("scm:git:git@github.com:UweTrottmann/AndroidUtils.git")
                    developerConnection.set("scm:git:git@github.com:UweTrottmann/AndroidUtils.git")
                    url.set("https://github.com/UweTrottmann/AndroidUtils")
                }
            }
        }
    }

    // Sonatype Central repository created by gradle-nexus.publish-plugin, see root build.gradle.
}

signing {
    // https://docs.gradle.org/current/userguide/signing_plugin.html#sec:using_gpg_agent
    useGpgCmd()
    sign(publishing.publications["central"])
}

