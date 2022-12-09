plugins {
    id("com.android.library")
    kotlin("android")
    id("maven-publish")
    id("signing")
}

group = "com.uwetrottmann.androidutils"
version = "3.1.0-SNAPSHOT"

android {
    namespace = "com.uwetrottmann.androidutils"
    compileSdk = 33 // Android 13

    defaultConfig {
        minSdk = 15
        targetSdk = 33 // Android 13
    }

    compileOptions {
        encoding = "UTF-8"
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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
    implementation("androidx.core:core-ktx:1.7.0")
    // https://developer.android.com/jetpack/androidx/releases/annotation
    implementation("androidx.annotation:annotation:1.3.0")
    // https://developer.android.com/jetpack/androidx/releases/loader
    implementation("androidx.loader:loader:1.1.0")
    // https://developer.android.com/jetpack/androidx/releases/appcompat
    implementation("androidx.appcompat:appcompat:1.4.1")
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
    if (!rootProject.hasProperty("signing.keyId")
        || !rootProject.hasProperty("signing.password")
        || !rootProject.hasProperty("signing.secretKeyRingFile")
    ) {
        println("WARNING: Signing properties missing, published artifacts will not be signed.")
    }
    sign(publishing.publications["central"])
}

