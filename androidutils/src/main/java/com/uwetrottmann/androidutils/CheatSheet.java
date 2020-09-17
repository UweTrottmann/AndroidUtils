/*
 * Copyright 2012 Roman Nurik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uwetrottmann.androidutils;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.TooltipCompat;

/**
 * Helper class for showing cheat sheets (tooltips) for icon-only UI elements on
 * long-press or hover. This is already default platform behavior for icon-only
 * {@link android.app.ActionBar} items and tabs. This class provides this
 * behavior for any other such UI element.
 * <p>
 * Note: This is now just a wrapper for {@link TooltipCompat#setTooltipText(View, CharSequence)},
 * new code should probably use it directly.
 */
public class CheatSheet {

    /**
     * Sets up a cheat sheet (tooltip) for the given view using
     * {@link TooltipCompat#setTooltipText(View, CharSequence)}. When the view is
     * long-pressed or hovered, a tooltip with the view's
     * {@link View#getContentDescription() content description}
     * will be shown either above (default) or below the view (if there isn't
     * room above it).
     */
    public static void setup(@NonNull View view) {
        TooltipCompat.setTooltipText(view, view.getContentDescription());
    }

    /**
     * Sets up a cheat sheet (tooltip) for the given view using
     * {@link TooltipCompat#setTooltipText(View, CharSequence)}. When the view is
     * long-pressed or hovered, a tooltip with the given text will be shown either
     * above (default) or below the view (if there isn't room above it).
     */
    public static void setup(@NonNull View view, @StringRes int textResId) {
        TooltipCompat.setTooltipText(view, view.getContext().getString(textResId));
    }

    /**
     * Sets up a cheat sheet (tooltip) for the given view using
     * {@link TooltipCompat#setTooltipText(View, CharSequence)}. When the view is
     * long-pressed or hovered, a tooltip with the given text will be shown either
     * above (default) or below the view (if there isn't room above it).
     * will be shown near the view.
     * <p>
     * Will remove the tooltip when a null text is passed.
     */
    public static void setup(@NonNull View view, @Nullable CharSequence text) {
        TooltipCompat.setTooltipText(view, text);
    }

    /**
     * Removes the cheat sheet for the given view.
     */
    public static void remove(@NonNull View view) {
        TooltipCompat.setTooltipText(view, null);
    }

}
