# AndroidUtils

Some helper code for Android app development.

* [`AndroidUtils`](androidutils/src/main/java/com/uwetrottmann/androidutils/AndroidUtils.kt)
  helper methods to
  * check for Android version (like `AndroidUtils.isLollipopOrHigher()`),
  * an active or unmetered network connection,
  * external storage state availability.
* [`GenericSimpleLoader`](androidutils/src/main/java/com/uwetrottmann/androidutils/GenericSimpleLoader.kt)
  offers a basic `AsyncTaskLoader` implementation to load simple objects or a list of objects.
  * Note: new code should probably use [ViewModel and LiveData](https://developer.android.com/topic/libraries/architecture/livedata) 
    or [Kotlin Flows](https://developer.android.com/kotlin/flow) instead.

## Usage
This library is available on Maven Central.

<a href="https://search.maven.org/search?q=com.uwetrottmann.androidutils"><img src="https://img.shields.io/maven-central/v/com.uwetrottmann.androidutils/androidutils.svg?style=flat-square"></a>

```groovy
implementation("com.uwetrottmann.androidutils:androidutils:3.1.0")
```

## License

This work by [Uwe Trottmann](https://uwetrottmann.com) is licensed under the [Apache License 2.0](LICENSE.txt).

[Contributors](https://github.com/UweTrottmann/AndroidUtils/graphs/contributors) and changes are tracked by Git.

Do not just copy, make it better.
