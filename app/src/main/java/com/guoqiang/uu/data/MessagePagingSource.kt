package com.guoqiang.uu.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.guoqiang.uu.utils.LogUtil
import kotlinx.coroutines.delay

/**
 * author: zgq
 * date: 2023/3/25 19:19
 * destcription:
 */
class MessagePagingSource(private val dao: MessageDao, private val userIds: List<String>) :
    PagingSource<Int, Message>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Message> {
        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize
            val offset = page * pageSize
            val data = dao.getMessages(offset, pageSize, userIds)
            LoadResult.Page(
                data = data,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (data.isEmpty() || data.size < pageSize) null else page + 1
            )
        } catch (e: Exception) {
            // Handle the error case
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Message>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}
