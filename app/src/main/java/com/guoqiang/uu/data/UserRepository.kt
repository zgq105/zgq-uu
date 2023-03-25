/*
 * Copyright 2018 Google LLC
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

package com.guoqiang.uu.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val dao: UserDao
) {

    suspend fun getAll(): List<User> {
        return dao.getAll()
    }

    suspend fun getUsers() = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            UserPagingSource(dao)
        }
    ).flow

   suspend fun testInsertUserData() {
        val users = arrayListOf<User>()
        for (it in 0..100) {
            val date = Date()
            users.add(
                User(
                    "$it", "zgq$it", "123", null,
                    null, 12, 1, null, date, date
                )
            )
        }
        dao.insertUsers(users)
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(dao: UserDao) =
            instance ?: synchronized(this) {
                instance ?: UserRepository(dao).also { instance = it }
            }
    }
}
