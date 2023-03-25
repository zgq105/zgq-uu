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

package com.guoqiang.uu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guoqiang.uu.data.TestRepository
import com.guoqiang.uu.data.UserRepository
import com.guoqiang.uu.utils.LogUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    fun test() {
        LogUtil.d("zgq", "UserViewModel")
    }

    fun getAll() {
        viewModelScope.launch {
            repository.getAll()
        }
    }

    fun testInsertUserData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.testInsertUserData()
                val list = repository.getAll()
                list.forEach {
                    LogUtil.d("user:" + it.username)
                }
            }

        }
    }
}
