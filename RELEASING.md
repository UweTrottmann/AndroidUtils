# Releasing

## Prerequisites

- Sonatype (Maven Central) Account
- GPG keys

### Gradle properties

Define publishing properties in `~/.gradle/gradle.properties`:

```
# Replace with your values
SONATYPE_NEXUS_USERNAME=
SONATYPE_NEXUS_PASSWORD=
```

## Creating a release

1. Update `README.md` and `RELEASE_NOTES.md`.

2. Change version in `androidutils\build.gradle` to a non-snapshot version.

3. Build and publish:

    ```
    ./gradlew clean publishCentralPublicationToSonatypeRepository closeAndReleaseSonatypeStagingRepository
    ```

4. Commit and tag release. Change version back to snapshot, commit. Push to GitHub.

    ```
    git commit -am "Prepare release 1.2.3"
    git tag v1.2.3
    // After changing version back to snapshots
    git commit -am "Start development of next version."
    git push origin main --tags
    ```
