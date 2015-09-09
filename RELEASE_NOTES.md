Release Notes
=============

2.1.0 (2015-09-09)
------------------

- Add version detectors for Android M, LOLLIPOP_MR1, KITKAT_WATCH, JELLY_BEAN_MR2, ICE_CREAM_SANDWICH_MR1.
- Drop FROYO version detector, is above minimal support of this library.
- `isWifiConnected()` now checks if WiFi is actually the active network connection (default route).
- Added `isUnmeteredNetworkConnected()` to check if there is an active and connected network that is not metered, suitable for large data transfer.

2.0.0 (2015-05-04)
------------------

- Initial deployment to Maven Central.