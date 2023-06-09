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
class UserPagingSource(private val dao: UserDao) : PagingSource<Int, User>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize
            val offset = page * pageSize
            LogUtil.d("zgq", "offset:$offset,pageSize:$pageSize")
            delay(500)
            val data = dao.getUsers(offset, pageSize)
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

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}
