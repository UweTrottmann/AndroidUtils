package com.uwetrottmann.androidutils

import android.content.Context
import androidx.loader.content.AsyncTaskLoader

/**
 * A generic [AsyncTaskLoader] suitable for loading any single object or [List] of
 * objects. It takes care of delivering and resetting results, so you only have to implement
 * `loadInBackground()`.
 *
 *
 * To automatically reload data on content changes implement some form of content observer which
 * just calls this loaders [.onContentChanged] method if data has changed.
 *
 *
 * Make sure to override [.onReleaseResources] in a meaningful way to clean up resources
 * associated with the actively loaded data set. If you have to use a [android.database.Cursor]
 * you should probably use [androidx.loader.content.CursorLoader] instead.
 *
 * Note: new code should probably use
 * [ViewModel and LiveData](https://developer.android.com/topic/libraries/architecture/livedata) instead.
 */
abstract class GenericSimpleLoader<T>(context: Context) : AsyncTaskLoader<T>(context) {

    protected var items: T? = null

    /**
     * Called when there is new data to deliver to the client. The super class will take care of
     * delivering it; the implementation here just adds a little more logic.
     */
    override fun deliverResult(newItems: T?) {
        if (isReset) {
            // An async query came in while the loader is stopped. We
            // don't need the result.
            newItems?.let { onReleaseResources(it) }
        }
        val oldItems = items
        items = newItems

        if (isStarted) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(newItems)
        }

        oldItems?.let { onReleaseResources(it) }
    }

    override fun onStartLoading() {
        if (items != null) {
            deliverResult(items)
        }
        if (takeContentChanged() || items == null) {
            forceLoad()
        }
    }

    /**
     * Handles a request to stop the Loader.
     */
    override fun onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad()
    }

    /**
     * Handles a request to cancel a load.
     */
    override fun onCanceled(items: T?) {
        items?.let { onReleaseResources(it) }
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    override fun onReset() {
        super.onReset()

        // Ensure the loader is stopped
        onStopLoading()

        // At this point we can release resources
        if (items != null) {
            onReleaseResources(items!!)
            items = null
        }
    }

    /**
     * Helper function to take care of releasing resources associated with an actively loaded data
     * set.
     *
     * For simple items there is nothing to do. For something like a Cursor,
     * it should be closed here.
     */
    protected open fun onReleaseResources(items: T) {
    }
}