/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.guoqiang.uu.navigation

import com.guoqiang.uu.R
import com.guoqiang.uu.ui.icon.UUIcons
import com.guoqiang.uu.ui.icon.Icon
import com.guoqiang.uu.ui.icon.Icon.DrawableResourceIcon
import com.guoqiang.uu.ui.icon.Icon.ImageVectorIcon
/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */
enum class TopLevelDestination(
    val selectedIcon: Icon,
    val unselectedIcon: Icon,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    UU_HOME(
        selectedIcon = DrawableResourceIcon(UUIcons.Upcoming),
        unselectedIcon = DrawableResourceIcon(UUIcons.UpcomingBorder),
        iconTextId = R.string.home,
        titleTextId = R.string.home,
    ),
    UU_MIME(
        selectedIcon = DrawableResourceIcon(UUIcons.Bookmarks),
        unselectedIcon = DrawableResourceIcon(UUIcons.BookmarksBorder),
        iconTextId = R.string.mime,
        titleTextId = R.string.mime,
    ),
}
