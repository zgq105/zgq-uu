package com.guoqiang.uu.ui.chat

import android.widget.Space
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.guoqiang.uu.R
import com.guoqiang.uu.data.AppDatabase
import com.guoqiang.uu.navigation.UU_MIME_PERSON_INFO_ROUTE
import com.guoqiang.uu.ui.icon.Icon
import com.guoqiang.uu.ui.mime.ShowAvatarSetting
import com.guoqiang.uu.ui.theme.PurpleGrey80
import com.guoqiang.uu.ui.theme.ZgquuTheme
import com.guoqiang.uu.utils.LogUtil
import com.guoqiang.uu.viewmodel.MessageViewModel
import com.guoqiang.uu.viewmodel.UserViewModel
import com.guoqiang.uu.widget.AICenterAlignedTopAppBar
import com.guoqiang.uu.widget.AICenterLoading

/**
 * author: zgq
 * date: 2023/3/11 15:33
 * destcription: AI管家
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AIMajordomoScreen(navController: NavController) {
    val userViewModel: UserViewModel = hiltViewModel()
    val messageViewModel: MessageViewModel = hiltViewModel()

    val messages =
        messageViewModel.getMessages(arrayListOf(AppDatabase.VISITOR_UID, AppDatabase.ROBOT_UID))
            .collectAsLazyPagingItems()

    var isShowMoreDialog by remember { mutableStateOf(false) }
    if (isShowMoreDialog) {
        ShowMoreDialog {
            isShowMoreDialog = false
            return@ShowMoreDialog
        }
    }

    Column(Modifier.fillMaxSize()) {
        Row(Modifier.weight(1f)) {
            LazyColumn() {
                stickyHeader {
                    AICenterAlignedTopAppBar("AI管家", {
                        navController.popBackStack()
                    }, true, {
                        isShowMoreDialog = true
                    })
                }

                items(messages) {
                    if (it?.from == 1) {
                        RobotMessageItem(
                            robotIconText = "AI管家",
                            text = it?.content ?: ""
                        ) {}
                    } else {
                        UserMessageItem(it?.content ?: "", textClick = {})
                    }
                }

                when (messages.loadState.refresh) { //FIRST LOAD
                    is LoadState.Error -> {

                    }
                    is LoadState.Loading -> { // Loading UI
                        item {
                            AICenterLoading("Refresh loading", Modifier.fillParentMaxSize())
                        }
                    }
                    else -> {

                    }

                }

                when (messages.loadState.append) { // Pagination
                    is LoadState.Error -> {

                    }
                    is LoadState.Loading -> { // Pagination Loading UI
                        LogUtil.d("zgq", "loadState.append Loading")
                        item {
                            //todo 不显示问题
                            AICenterLoading("Pagination loading", Modifier.fillParentMaxSize())
                        }
                    }
                    else -> {}
                }

            }
        }
        Spacer(Modifier.height(8.dp))
        Row() {
            UserChatInput()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ZgquuTheme {
        //UserMessageItem("今天星期几今天星期几今天星期几今天星期几今天星期几今天星期几今天星期几") {}
        RobotMessageItem("厨师", "今天星期几今天星期几今天星期几今天星期几今天星期几今天星期几今天星期几") {}
    }
}


