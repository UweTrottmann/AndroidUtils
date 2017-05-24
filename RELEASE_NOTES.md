Release Notes
=============

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