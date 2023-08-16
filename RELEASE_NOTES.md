Release Notes
=============

## next

- Add `AndroidUtils.isAtLeastUpsideDownCake`.

## 3.1.0
_2022-12-09_

- Fix `AndroidUtils.isNetworkConnected` and `.isUnmeteredNetworkConnected` crashing with 
  `SecurityException` on some Android 11 devices due to a [system bug](https://android-review.googlesource.com/c/platform/frameworks/base/+/1758029).
  See method for details.
- Update Kotlin to 1.7.21, when using the library need to compile with at least Kotlin 1.6.
- Add `AndroidUtils.isAtLeastTiramisu`, `AndroidUtils.isAtLeastSv2`.

## 3.0.0
_2022-01-21_

- Convert to Kotlin.
- Add replace with code for `CheatSheet`, deprecate all methods.
- Add `AndroidUtils.isAtLeastS`, remove `isICSMR1OrHigher`.
- `AndroidUtils` uses `NetworkCapabilities` API to detect network state on Android 6 and higher.
- Remove `AndroidUtils.copyFile`, use a Kotlin extension function instead.

## 2.4.1
_2020-09-18_

- Change new Android SDK version check names to include code-name to appease Lint NewApi check.

## 2.4.0
_2020-09-17_

- Migrate to AndroidX.
- Change `CheatSheet` to internally use `TooltipCompat` from AndroidX appcompat.
- Add Android API level checks up to Android 11 (API level 30).
- Add nullability, permission and type annotations.

## 2.3.1
_2017-08-03_

- `AndroidUtils.copyFile` ensures both streams are closed, no longer throws if closing streams fails. Thanks @NightlyNexus!
- Depend on Android Support library 26.0.0.

## 2.3.0
_2017-05-24_

- Update to `support-core-utils:25.3.1`. No longer depend on whole `support-v4`.
- `minSdk=15` and `targetSdk=25`, added version helper methods for N and N MR1.

## 2.2.1
_2016-05-26_

- Undeprecate version helpers.
- Update support-v4 dependency to 23.4.0.

## 2.2.0
_2016-03-11_

- Removed `executeOnPool()`, use `android.support.v4.os.AsyncTaskCompat` instead.
- Deprecated version helpers, use Studio quick fix to insert `Build.VERSION.SDK_INT` condition instead.

## 2.1.0
_2015-09-09_

- Add version detectors for Android M, LOLLIPOP_MR1, KITKAT_WATCH, JELLY_BEAN_MR2, ICE_CREAM_SANDWICH_MR1.
- Drop FROYO version detector, is above minimal support of this library.
- `isWifiConnected()` now checks if WiFi is actually the active network connection (default route).
- Added `isUnmeteredNetworkConnected()` to check if there is an active and connected network that is not metered, suitable for large data transfer.

## 2.0.0
_2015-05-04_

- Initial deployment to Maven Central.