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

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.guoqiang.uu.ui.home.HomeScreen
import com.guoqiang.uu.viewmodel.UserViewModel

const val UU_HOME_NAVIGATION_ROUTE = "uu_home"

fun NavController.navigateToForHome(navOptions: NavOptions? = null) {
    this.navigate(UU_HOME_NAVIGATION_ROUTE, navOptions)
}

fun NavGraphBuilder.navHomeScreen(navController: NavHostController) {
    composable(route = UU_HOME_NAVIGATION_ROUTE) {
        HomeScreen(navController)
    }
}


