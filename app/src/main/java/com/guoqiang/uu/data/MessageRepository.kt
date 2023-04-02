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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(
    private val dao: MessageDao
) {

    suspend fun getAll(): List<Message> {
        return dao.getAll()
    }

    suspend fun insertMessage(entity: Message) = dao.insertMessage(entity)

    suspend fun insertMessages(entities: List<Message>) = dao.insertAll(entities)

    fun getMessages(userIds: List<String>) = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
        pagingSourceFactory = {
            MessagePagingSource(dao, userIds)
        }
    ).flow

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: MessageRepository? = null

        fun getInstance(dao: MessageDao) =
            instance ?: synchronized(this) {
                instance ?: MessageRepository(dao).also { instance = it }
            }
    }
}
