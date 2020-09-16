package com.uwetrottmann.androidutils;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

public class AndroidUtils {

    public static boolean isAtLeastAndroid11() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }

    public static boolean isAtLeastAndroid10() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }

    public static boolean isAtLeastAndroid9() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    public static boolean isAtLeastAndroid81() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1;
    }

    public static boolean isAtLeastAndroid8() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    public static boolean isNougatMR1OrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1;
    }

    public static boolean isNougatOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    public static boolean isMarshmallowOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isLollipopMR1OrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    public static boolean isLollipopOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isKitKatWatchOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH;
    }

    public static boolean isKitKatOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isJellyBeanMR2OrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    public static boolean isJellyBeanMR1OrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    public static boolean isJellyBeanOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean isICSMR1OrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
    }

    /**
     * Checks if {@link Environment}.MEDIA_MOUNTED is returned by {@code getExternalStorageState()}
     * and therefore external storage is read- and writeable.
     */
    public static boolean isExtStorageAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * Returns true if the current layout direction is {@link View#LAYOUT_DIRECTION_RTL}.
     *
     * <p>This always returns false on versions below JELLY_BEAN_MR1.
     */
    public static boolean isRtlLayout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            int direction = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault());
            return direction == View.LAYOUT_DIRECTION_RTL;
        }
        return false;
    }

    private static ConnectivityManager getConnectivityManager(Context context) {
        // use application context to avoid leaking any activity
        // https://code.google.com/p/android/issues/detail?id=198852
        return (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    @Nullable
    private static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager connectivityManager = getConnectivityManager(context);
        if (connectivityManager == null) {
            return null;
        }
        return connectivityManager.getActiveNetworkInfo();
    }

    /**
     * Whether there is an active network connection.
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isNetworkConnected(Context context) {
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo(context);
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Whether there is an active network connection and it is via WiFi.
     *
     * <p>If you want to check whether to transmit large amounts of data, you may want to use {@link
     * #isUnmeteredNetworkConnected(Context)}.
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isWifiConnected(Context context) {
        NetworkInfo activeNetwork = getActiveNetworkInfo(context);
        return activeNetwork != null && activeNetwork.isConnected()
                && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * Whether there is an active network connection and it is not metered, e.g. so large amounts of
     * data may be transmitted.
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isUnmeteredNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = getConnectivityManager(context);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected()
                && !connectivityManager.isActiveNetworkMetered();
    }

    /**
     * Copies the contents of one file to the other using {@link FileChannel}s.
     *
     * @param src source {@link File}
     * @param dst destination {@link File}
     */
    public static void copyFile(File src, File dst) throws IOException {
        FileInputStream in = new FileInputStream(src);
        FileOutputStream out = new FileOutputStream(dst);
        FileChannel inChannel = in.getChannel();
        FileChannel outChannel = out.getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            try {
                in.close();
            } catch (IOException ignored) {
            }
            try {
                out.close();
            } catch (IOException ignored) {
            }
        }
    }
}
