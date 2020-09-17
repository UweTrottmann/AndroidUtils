# AndroidUtils

Some helper code for Android app development.

* `AndroidUtils` helper methods to 
  * check for Android version (like `AndroidUtils.isLollipopOrHigher()`),
  * an active or unmetered network connection,
  * external storage state availability.
* `CheatSheet` by [Roman Nurik](https://gist.github.com/romannurik/3982005) to display info toasts for any view (similar to action items).
  * Deprecated, should probably use [ToolTipCompat](https://developer.android.com/reference/androidx/appcompat/widget/TooltipCompat) instead.
* `GenericSimpleLoader` offers a basic `AsyncTaskLoader` implementation to load simple objects or a list of objects.

## Usage
This library is available on Maven Central.

<a href="https://search.maven.org/search?q=com.uwetrottmann.androidutils"><img src="https://img.shields.io/maven-central/v/com.uwetrottmann.androidutils/androidutils.svg?style=flat-square"></a>

```groovy
implementation("com.uwetrottmann.androidutils:androidutils:2.3.1")
```

## License

This work by [Uwe Trottmann](https://uwetrottmann.com) is licensed under the [Apache License 2.0](LICENSE.txt).

[Contributors](https://github.com/UweTrottmann/AndroidUtils/graphs/contributors) and changes are tracked by Git.

Do not just copy, make it better.
