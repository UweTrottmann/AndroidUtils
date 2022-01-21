package com.uwetrottmann.androidutils

import android.Manifest
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import android.net.ConnectivityManager
import androidx.annotation.RequiresPermission
import android.net.NetworkInfo
import android.annotation.TargetApi
import android.content.Context
import android.view.View
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.core.content.getSystemService
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.Throws

/**
 * Provides various static helper methods to
 *
 *  * check for Android version (like [isAtLeastOreo]),
 *  * an active or unmetered network connection,
 *  * external storage state availability.
 *
 */
object AndroidUtils {

    /*
     * NOTE: new Android SDK version checks should contain the code name,
     * otherwise Lint does not seem to detect them?!
     */

    /**
     * API level 30+, Android 11.
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.R)
    @JvmStatic
    val isAtLeastR: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

    /**
     * API level 29+, Android 10.
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
    @JvmStatic
    val isAtLeastQ: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    /**
     * API level 28+, Android 9.
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
    @JvmStatic
    val isAtLeastPie: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

    /**
     * API level 27+, Android 8.1.
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O_MR1)
    @JvmStatic
    val isAtLeastOreoMR1: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1

    /**
     * API level 26+, Android 8.0.
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
    @JvmStatic
    val isAtLeastOreo: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    /**
     * API level 25+, Android 7.1.
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N_MR1)
    @JvmStatic
    val isNougatMR1OrHigher: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1

    /**
     * API level 24+, Android 7.0.
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N)
    @JvmStatic
    val isNougatOrHigher: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

    /**
     * API level 23+, Android 6.0.
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.M)
    @JvmStatic
    val isMarshmallowOrHigher: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    /**
     * API level 22+, Android 5.1.
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @JvmStatic
    val isLollipopMR1OrHigher: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1

    /**
     * API level 21+, Android 5.0.
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    val isLollipopOrHigher: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    /**
     * API level 20+, Android 4.4.
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.KITKAT_WATCH)
    @JvmStatic
    val isKitKatWatchOrHigher: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH

    /**
     * API level 19+, Android 4.4.
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.KITKAT)
    @JvmStatic
    val isKitKatOrHigher: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

    /**
     * API level 18+, Android 4.3.
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @JvmStatic
    val isJellyBeanMR2OrHigher: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2

    /**
     * API level 17+, Android 4.2.
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @JvmStatic
    val isJellyBeanMR1OrHigher: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1

    /**
     * API level 16+, Android 4.1.
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.JELLY_BEAN)
    @JvmStatic
    val isJellyBeanOrHigher: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN

    /**
     * Checks if [Environment].MEDIA_MOUNTED is returned by `getExternalStorageState()`
     * and therefore external storage is read- and writeable.
     */
    @JvmStatic
    val isExtStorageAvailable: Boolean
        get() = Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()

    /**
     * Returns true if the current layout direction is [View.LAYOUT_DIRECTION_RTL].
     *
     * This always returns false on versions below JELLY_BEAN_MR1.
     */
    @JvmStatic
    val isRtlLayout: Boolean
        get() {
            if (isJellyBeanMR1OrHigher) {
                val direction = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault())
                return direction == View.LAYOUT_DIRECTION_RTL
            }
            return false
        }

    private fun getConnectivityManager(context: Context): ConnectivityManager? {
        // use application context to avoid leaking any activity
        // https://code.google.com/p/android/issues/detail?id=198852
        return context.applicationContext.getSystemService()
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun getActiveNetworkInfo(context: Context): NetworkInfo? {
        val connectivityManager = getConnectivityManager(context) ?: return null
        return connectivityManager.activeNetworkInfo
    }

    /**
     * Whether there is an active network connection.
     */
    @JvmStatic
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isNetworkConnected(context: Context): Boolean {
        val activeNetworkInfo = getActiveNetworkInfo(context)
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    /**
     * Whether there is an active network connection and it is via WiFi.
     *
     * If you want to check whether to transmit large amounts of data,
     * you may want to use [isUnmeteredNetworkConnected].
     */
    @JvmStatic
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isWifiConnected(context: Context): Boolean {
        val activeNetwork = getActiveNetworkInfo(context)
        return (activeNetwork != null && activeNetwork.isConnected
                && activeNetwork.type == ConnectivityManager.TYPE_WIFI)
    }

    /**
     * Whether there is an active network connection and it is not metered,
     * e.g. so large amounts of data may be transmitted.
     */
    @JvmStatic
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun isUnmeteredNetworkConnected(context: Context): Boolean {
        val connectivityManager = getConnectivityManager(context) ?: return false
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected
                && !connectivityManager.isActiveNetworkMetered)
    }

}