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
package com.uwetrottmann.androidutils

import android.view.View
import androidx.appcompat.widget.TooltipCompat
import androidx.annotation.StringRes

/**
 * Helper class for showing cheat sheets (tooltips) for icon-only UI elements on
 * long-press or hover. This is already default platform behavior for icon-only
 * [android.app.ActionBar] items and tabs. This class provides this
 * behavior for any other such UI element.
 *
 * Note: This is now just a wrapper for [TooltipCompat.setTooltipText],
 * new code should probably use it directly.
 */
object CheatSheet {

    /**
     * Sets up a cheat sheet (tooltip) for the given view using
     * [TooltipCompat.setTooltipText]. When the view is
     * long-pressed or hovered, a tooltip with the view's
     * [content description][View.getContentDescription]
     * will be shown either above (default) or below the view (if there isn't
     * room above it).
     */
    @Deprecated(
        message = "This is now just a wrapper for TooltipCompat.setTooltipText, new code should probably use it directly.",
        replaceWith = ReplaceWith(
            expression = "TooltipCompat.setTooltipText(view, view.contentDescription)",
            "androidx.appcompat.widget.TooltipCompat"
        )
    )
    @JvmStatic
    fun setup(view: View) {
        TooltipCompat.setTooltipText(view, view.contentDescription)
    }

    /**
     * Sets up a cheat sheet (tooltip) for the given view using
     * [TooltipCompat.setTooltipText]. When the view is
     * long-pressed or hovered, a tooltip with the given text will be shown either
     * above (default) or below the view (if there isn't room above it).
     */
    @Deprecated(
        message = "This is now just a wrapper for TooltipCompat.setTooltipText, new code should probably use it directly.",
        replaceWith = ReplaceWith(
            expression = "TooltipCompat.setTooltipText(view, view.context.getString(textResId))",
            "androidx.appcompat.widget.TooltipCompat"
        )
    )
    @JvmStatic
    fun setup(view: View, @StringRes textResId: Int) {
        TooltipCompat.setTooltipText(view, view.context.getString(textResId))
    }

    /**
     * Sets up a cheat sheet (tooltip) for the given view using
     * [TooltipCompat.setTooltipText]. When the view is
     * long-pressed or hovered, a tooltip with the given text will be shown either
     * above (default) or below the view (if there isn't room above it).
     * will be shown near the view.
     *
     *
     * Will remove the tooltip when a null text is passed.
     */
    @Deprecated(
        message = "This is now just a wrapper for TooltipCompat.setTooltipText, new code should probably use it directly.",
        replaceWith = ReplaceWith(
            expression = "TooltipCompat.setTooltipText(view, text)",
            "androidx.appcompat.widget.TooltipCompat"
        )
    )
    @JvmStatic
    fun setup(view: View, text: CharSequence?) {
        TooltipCompat.setTooltipText(view, text)
    }

    /**
     * Removes the cheat sheet for the given view.
     */
    @Deprecated(
        message = "This is now just a wrapper for TooltipCompat.setTooltipText, new code should probably use it directly.",
        replaceWith = ReplaceWith(
            expression = "TooltipCompat.setTooltipText(view, null)",
            "androidx.appcompat.widget.TooltipCompat"
        )
    )
    @JvmStatic
    fun remove(view: View) {
        TooltipCompat.setTooltipText(view, null)
    }
}