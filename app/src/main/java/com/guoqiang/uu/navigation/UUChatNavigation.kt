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
import com.guoqiang.uu.ui.chat.AIMajordomoScreen
import com.guoqiang.uu.ui.mime.MimeScreen
import com.guoqiang.uu.ui.mime.PersonInfoScreen
import com.guoqiang.uu.ui.mime.SelectIndustryScreen
import com.guoqiang.uu.ui.mime.VerifiedInfo

const val UU_CHAT_AI_MAJORDOMO_ROUTE = "uu_chat_ai_majordomo_route"

fun NavGraphBuilder.navAiMajordomoScreen(navController: NavHostController) {
    composable(route = UU_CHAT_AI_MAJORDOMO_ROUTE) {
        AIMajordomoScreen(navController = navController)
    }
}



