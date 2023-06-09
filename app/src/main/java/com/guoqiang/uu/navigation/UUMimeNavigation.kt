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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.guoqiang.uu.ui.mime.MimeScreen
import com.guoqiang.uu.ui.mime.PersonInfoScreen
import com.guoqiang.uu.ui.mime.SelectIndustryScreen
import com.guoqiang.uu.ui.mime.VerifiedInfo

const val UU_MIME_NAVIGATION_ROUTE = "uu_mime"
const val UU_MIME_PERSON_INFO_ROUTE = "uu_mime_person_info"
const val UU_MIME_SELECT_INDUSTRY = "uu_mime_select_industry"
const val UU_MIME_VERIFIED_INFO = "uu_mime_verified_info"

fun NavController.navigateToForMime(navOptions: NavOptions? = null) {
    this.navigate(UU_MIME_NAVIGATION_ROUTE, navOptions)
}

fun NavGraphBuilder.navMimeScreen(
    nestedGraphs: NavGraphBuilder.() -> Unit,
    navController: NavHostController
) {
    composable(route = UU_MIME_NAVIGATION_ROUTE) {
        // 创建渐变色
        val verticalGradientBrush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFFFAF8F8),
                Color(0xFFDAD6D6)
            )
        )
        Surface(
            Modifier
                .background(brush = verticalGradientBrush)
                .fillMaxSize()
        ) {
            MimeScreen(navController)
        }
    }
    nestedGraphs()
}


fun NavGraphBuilder.navPersonInfoScreen(navController: NavHostController, onBackClick: () -> Unit) {
    composable(route = UU_MIME_PERSON_INFO_ROUTE) {
        PersonInfoScreen(navController,onBackClick)
    }
}

fun NavGraphBuilder.navSelectIndustryScreen(
    onBackClick: () -> Unit
) {
    composable(route = UU_MIME_SELECT_INDUSTRY) {
        SelectIndustryScreen(onBackClick)
    }
}

fun NavGraphBuilder.navVerifiedInfoScreen(onBackClick: () -> Unit){
    composable(route = UU_MIME_VERIFIED_INFO) {
        VerifiedInfo(onBackClick)
    }
}

