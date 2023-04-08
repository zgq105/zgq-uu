package com.guoqiang.uu.ui.chat

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.paging.compose.itemsIndexed
import com.guoqiang.uu.ChatGptHelper
import com.guoqiang.uu.R
import com.guoqiang.uu.data.AppDatabase
import com.guoqiang.uu.data.Message
import com.guoqiang.uu.data.MessageSession
import com.guoqiang.uu.data.MessageSource
import com.guoqiang.uu.navigation.UU_MIME_PERSON_INFO_ROUTE
import com.guoqiang.uu.ui.icon.Icon
import com.guoqiang.uu.ui.mime.ShowAvatarSetting
import com.guoqiang.uu.ui.theme.PurpleGrey80
import com.guoqiang.uu.ui.theme.ZgquuTheme
import com.guoqiang.uu.utils.LogUtil
import com.guoqiang.uu.viewmodel.MessageSessionViewModel
import com.guoqiang.uu.viewmodel.MessageViewModel
import com.guoqiang.uu.viewmodel.UserViewModel
import com.guoqiang.uu.widget.AICenterAlignedTopAppBar
import com.guoqiang.uu.widget.AICenterLoading
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.filter
import java.util.*

/**
 * author: zgq
 * date: 2023/3/11 15:33
 * destcription: AI管家
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AIMajordomoScreen(navController: NavController) {
    val lifeScope = rememberCoroutineScope()
    val messageViewModel: MessageViewModel = hiltViewModel()
    val messageSessionViewModel: MessageSessionViewModel = hiltViewModel()
    val sessionId by rememberSaveable {
        mutableStateOf(UUID.randomUUID().toString())
    }

    val listState = rememberLazyListState()
    var selectedItem by remember { mutableStateOf(arrayListOf<String>()) }

    var messages =
        messageViewModel.getMessages(arrayListOf(AppDatabase.VISITOR_UID, AppDatabase.ROBOT_UID))
            .collectAsLazyPagingItems()

    //弹窗
    var isShowMoreDialog by remember { mutableStateOf(false) }
    if (isShowMoreDialog) {
        ShowMoreDialog {
            isShowMoreDialog = false
            return@ShowMoreDialog
        }
    }

    var refreshUi by remember {
        mutableStateOf(false)
    }

    if (refreshUi) {
        messages =
            messageViewModel.getMessages(
                arrayListOf(
                    AppDatabase.VISITOR_UID,
                    AppDatabase.ROBOT_UID
                )
            )
                .collectAsLazyPagingItems()
        refreshUi = false
    }

    var loading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = null , block = {

    })

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (title_bar, list, bottom_input, loading_ui) = createRefs()
        Box(modifier = Modifier.constrainAs(title_bar) {
            top.linkTo(parent.top)
            bottom.linkTo(list.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            AICenterAlignedTopAppBar("AI管家", {
                navController.popBackStack()
            }, true, {
                isShowMoreDialog = true
            })
        }

        LazyColumn(modifier = Modifier
            .constrainAs(list) {
                top.linkTo(title_bar.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(bottom_input.top)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
            .padding(0.dp, 0.dp, 0.dp, 8.dp), state = listState) {
            items(messages, key = { message -> message.mid }) { message ->
                if (message == null) {
                    return@items
                }
                if (message.from == 1) {
                    RobotMessageItem(
                        robotIconText = "AI管家",
                        text = message.content,
                        isEditMode = selectedItem.contains(message.mid),
                        textClick = {
                            selectedItem.clear()
                            selectedItem.add(message.mid)
                            refreshUi = true
                        }, iconClick = {
                            selectedItem.clear()
                            refreshUi = true
                        })
                } else {
                    UserMessageItem(
                        message.content,
                        isEditMode = selectedItem.contains(message.mid),
                        textClick = {
                            selectedItem.clear()
                            selectedItem.add(message.mid)
                            refreshUi = true
                        },
                        iconClick = {
                            selectedItem.clear()
                            refreshUi = true
                        })
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
                    item {
                        //todo 不显示问题
                        AICenterLoading("Pagination loading", Modifier.fillParentMaxSize())
                    }
                }
                else -> {}
            }

            lifeScope.launch {
                delay(500)
                listState.animateScrollToItem(listState.layoutInfo.totalItemsCount - 1)
            }
        }

        Box(modifier = Modifier.constrainAs(bottom_input) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(list.bottom)
            bottom.linkTo(parent.bottom)
        }) {
            UserChatInput(sendClick = { input ->
                if (input.isNullOrEmpty() || loading) {
                    return@UserChatInput
                }
                lifeScope.launch {
                    loading = true
                    withContext(Dispatchers.IO){
                        send(sessionId, input, messageSessionViewModel, messageViewModel)
                    }
                    refreshUi = true
                    loading = false
                }
            })
        }

        //loading
        if (loading) {
            Box(
                modifier = Modifier.constrainAs(loading_ui) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                contentAlignment = Alignment.Center
            ) {
                AICenterLoading("执行中...", Modifier.fillMaxSize())
            }
        }
    }
}

private suspend fun send(
    sessionId: String,
    input: String,
    messageSessionViewModel: MessageSessionViewModel,
    messageViewModel: MessageViewModel
) {
    //插入会话表
    val date = Date()
    val messageSession = MessageSession(
        sessionId,
        date,
        date, 1
    )
    messageSessionViewModel.insertMessageSession(messageSession)
    //插入用户消息
    val userMessage = Message(
        UUID.randomUUID().toString(),
        input,
        MessageSource.VISITOR.value,
        date,
        date,
        AppDatabase.VISITOR_UID,
        sessionId
    )
    messageViewModel.insertMessage(userMessage)

    //执行查询
    val results = ChatGptHelper.chatAIMajordomo(input)
    //插入机器人的回复消息
    val messages = arrayListOf<Message>()
    results.forEach { result ->
        val date = Date()
        val message = Message(
            UUID.randomUUID().toString(),
            result,
            MessageSource.ROBOT.value,
            date,
            date,
            AppDatabase.ROBOT_UID,
            sessionId
        )
        messages.add(message)
    }
    if (messages.isNotEmpty()) {
        messageViewModel.insertMessages(messages)
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ZgquuTheme {
        //UserMessageItem("今天星期几今天星期几今天星期几今天星期几今天星期几今天星期几今天星期几") {}
        RobotMessageItem("厨师", "今天星期几今天星期几今天星期几今天星期几今天星期几今天星期几今天星期几", true, {}, {})
    }
}


