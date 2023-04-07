package com.guoqiang.uu.ui.chat

import android.content.ClipData
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.paging.compose.itemsIndexed
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
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
 * destcription: AI绘图
 */
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalGlideComposeApi::class
)
@Composable
fun AIDrawingScreen(navController: NavController) {
    val listState = rememberLazyListState()
    var loading by remember {
        mutableStateOf(false)
    }
    val lifecycle = rememberCoroutineScope()

    var classify = "人物"
    var imageUrl by remember {
        mutableStateOf("")
    }
    var inputText by remember { mutableStateOf("") }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (title_bar, list, bottom_input, loading_ui) = createRefs()
        Box(modifier = Modifier.constrainAs(title_bar) {
            top.linkTo(parent.top)
            bottom.linkTo(list.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            AICenterAlignedTopAppBar("AI作图", {
                navController.popBackStack()
            }, false, {})
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

            item {
                AIDrawingOutlinedTextField {
                    inputText = it
                }
            }

            item {
                AIDrawingClassify {
                    classify = it
                }
            }

            item {
                GlideImage(
                    model = imageUrl,
                    contentDescription = "暂无图片",
                    modifier = Modifier
                        .padding(6.dp)
                        .clickable(onClick = {

                        })
                        .fillMaxWidth()
                        .height(300.dp),
                )
            }

        }

        Box(modifier = Modifier.constrainAs(bottom_input) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(list.bottom)
            bottom.linkTo(parent.bottom)
        }) {
            Button(onClick = {
                loading = true
                lifecycle.launch {
                    loading = true
                    withContext(Dispatchers.IO) {
                        val images = ChatGptHelper.createImages("$classify,$inputText")
                        imageUrl = images.first()
                    }
                    loading = false
                }
            }) {
                Text(text = "立即生成")
            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIDrawingOutlinedTextField(textChangeListener: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))

    ) {

        TextField(
            value = text,
            onValueChange = {
                text = it
                textChangeListener.invoke(text)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            label = { Text("写下你的创意") },
            minLines = 3,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(0.dp, 8.dp, 16.dp, 8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "23", style = MaterialTheme.typography.bodySmall)
            Text(text = "/200", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "清空",
                style = TextStyle(color = MaterialTheme.colorScheme.primary, fontSize = 14.sp)
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun AIDrawingScreenPreview() {
    ZgquuTheme {

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AIDrawingClassify(selectedClick: (String) -> Unit) {
    var selectedButtonIndex by remember { mutableStateOf(0) }
    val buttonLabels = listOf("人物", "建筑", "动物", "风景旅行", "设计素材", "绘画", "餐饮美食", "植物", "市场化妆", "家具")
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        for (index in buttonLabels.indices) {
            Button(
                onClick = { selectedButtonIndex = index },
                modifier = Modifier
                    .selectable(
                        selected = (index == selectedButtonIndex),
                        onClick = {
                            selectedButtonIndex = index
                            selectedClick.invoke(buttonLabels[selectedButtonIndex])
                        }
                    )
                    .padding(horizontal = 2.dp, vertical = 2.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (index == selectedButtonIndex) MaterialTheme.colorScheme.primary else
                        MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (index == selectedButtonIndex) MaterialTheme.colorScheme.onPrimary else
                        MaterialTheme.colorScheme.primary
                ),
                contentPadding = PaddingValues(12.dp, 6.dp)
            ) {
                Text(
                    text = buttonLabels[index],
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}







