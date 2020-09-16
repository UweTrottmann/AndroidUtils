package com.uwetrottmann.androidutils;

import android.content.Context;

import java.util.List;

import androidx.loader.content.AsyncTaskLoader;

/**
 * A generic {@link AsyncTaskLoader} suitable for loading any single object or {@link List} of
 * objects. It takes care of delivering and resetting results, so you only have to implement
 * <code>loadInBackground()</code>.
 *
 * <p>To automatically reload data on content changes implement some form of content observer which
 * just calls this loaders {@link #onContentChanged()} method if data has changed.
 *
 * <p>Make sure to override {@link #onReleaseResources} in a meaningful way to clean up resources
 * associated with the actively loaded data set. If you have to use a {@link
 * android.database.Cursor} you should probably use {@link androidx.loader.content.CursorLoader}
 * instead.
 */
public abstract class GenericSimpleLoader<T> extends AsyncTaskLoader<T> {

    protected T items;

    public GenericSimpleLoader(Context context) {
        super(context);
    }

    /**
     * Called when there is new data to deliver to the client. The super class will take care of
     * delivering it; the implementation here just adds a little more logic.
     */
    @Override
    public void deliverResult(T newItems) {
        if (isReset()) {
            // An async query came in while the loader is stopped. We
            // don't need the result.
            if (newItems != null) {
                onReleaseResources(newItems);
            }
        }
        T oldItems = this.items;
        this.items = newItems;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(newItems);
        }

        if (oldItems != null) {
            onReleaseResources(oldItems);
        }
    }

    @Override
    protected void onStartLoading() {
        if (items != null) {
            deliverResult(items);
        }
        if (takeContentChanged() || items == null) {
            forceLoad();
        }
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Handles a request to cancel a load.
     */
    @Override
    public void onCanceled(T items) {
        if (items != null) {
            onReleaseResources(items);
        }
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        // At this point we can release resources
        if (items != null) {
            onReleaseResources(items);
            items = null;
        }
    }

    /**
     * Helper function to take care of releasing resources associated with an actively loaded data
     * set.
     */
    protected void onReleaseResources(T items) {
        // For simple items there is nothing to do. For something
        // like a Cursor, we would close it here.
    }
}
