package com.guoqiang.uu.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.audio.TranscriptionRequest
import com.aallam.openai.api.audio.TranslationRequest
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.completion.CompletionRequest
import com.aallam.openai.api.completion.TextCompletion
import com.aallam.openai.api.edits.EditsRequest
import com.aallam.openai.api.file.FileSource
import com.aallam.openai.api.file.FileUpload
import com.aallam.openai.api.file.Purpose
import com.aallam.openai.api.image.ImageCreation
import com.aallam.openai.api.image.ImageEdit
import com.aallam.openai.api.image.ImageSize
import com.aallam.openai.api.image.ImageVariation
import com.aallam.openai.api.model.Model
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.api.moderation.ModerationRequest
import com.aallam.openai.client.OpenAI
import com.guoqiang.uu.ChatGptTest
import com.guoqiang.uu.GlobalData
import com.guoqiang.uu.R
import com.guoqiang.uu.navigation.UU_CHAT_AI_MAJORDOMO_ROUTE
import com.guoqiang.uu.ui.icon.UUIcons
import com.guoqiang.uu.ui.theme.*
import com.guoqiang.uu.utils.LogUtil
import com.guoqiang.uu.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.Buffer
import okio.source

/**
 * author: zgq
 * date: 2023/3/12 10:48
 * destcription:
 */
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val userViewModel: UserViewModel = hiltViewModel()

    LazyColumn {
        item {
            MainToolbar(settingClick = {
//                userViewModel.testInsertUserData()
                userViewModel.getAll()
            })
        }
        item {
            MainBanner {
                navController.navigate(UU_CHAT_AI_MAJORDOMO_ROUTE)
            }
        }
        item {
            Row(Modifier.padding(16.dp, 0.dp, 8.dp, 0.dp)) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(0.dp, 0.dp, 8.dp, 8.dp)
                ) {
                    MainGridItem(
                        MainContent("AI翻译官", "精通全球各种自然语言，试试让我帮你翻译"),
                        Color(0xFF22ACEB)
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(0.dp, 0.dp, 8.dp, 8.dp)
                ) {
                    MainGridItem(MainContent("AI解惑", "生活琐事，工作困扰，各种问题都可以找我哦"), Color(0xFFF07B72))
                }
            }
        }

        item {
            Row(Modifier.padding(16.dp, 0.dp, 8.dp, 0.dp)) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(0.dp, 0.dp, 8.dp, 8.dp)
                ) {
                    MainGridItem(MainContent("AI玄机", "破解难题，探索宇宙奥义，探讨生命的本质"), Color(0xFF6D7FE6))
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(0.dp, 0.dp, 8.dp, 8.dp)
                ) {
                    MainGridItem(MainContent("Al作家", "我会文案/检讨书/演讲稿等，说出你的要求吧"), Color(0xFFEC5B8C))
                }
            }
        }

        item {
            Row(Modifier.padding(16.dp, 0.dp, 8.dp, 0.dp)) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(0.dp, 0.dp, 8.dp, 8.dp)
                ) {
                    MainGridItem(MainContent("AI厨师", "试试问问我美味佳肴是如何做出来的吧"), Color(0xFFF0C33B))
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(0.dp, 0.dp, 8.dp, 8.dp)
                ) {
                    MainGridItem(MainContent("Al诗人", "试试让我按照您的要求帮您创作独一无二的诗"), Color(0xFFF39E20))
                }
            }
        }

        item {
            MainBottomBanner()
        }

//        GlobalScope.launch {
//            withContext(Dispatchers.IO) {
//                ChatGptTest.testImages()
//            }
//        }
    }
}


@Composable
fun MainToolbar(settingClick: () -> Unit) {
    Row(
        Modifier
            .background(MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            Modifier
                .weight(1F)
                .padding(16.dp)
        ) {
            Row {
                Text(
                    text = "你好",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row {
                Text(
                    text = "欢迎来到人工智能聊天",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        Column(Modifier.padding(16.dp)) {
            Icon(
                imageVector = Icons.Default.Settings,
                null,
                Modifier
                    .clickable {
                        settingClick.invoke()
                    },
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBanner(onclick: () -> Unit) {
    Card(
        onClick = { onclick.invoke() }, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        Modifier
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.mipmap.ic_home_banner), null, Modifier
                                .fillMaxSize(), contentScale = ContentScale.FillBounds
                        )
                    }
                    Box(Modifier.align(Alignment.BottomStart)) {
                        TextButton(
                            onClick = {},
                            Modifier
                                .clip(RoundedCornerShape(2.dp))
                                .padding(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                MaterialTheme.colorScheme.onPrimary,
                                MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = "AI管家",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Box(Modifier.align(Alignment.BottomEnd)) {
                        TextButton(
                            onClick = {},
                            Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .padding(2.dp)
                                .size(80.dp, 30.dp),
                            colors = ButtonDefaults.buttonColors(
                                MaterialTheme.colorScheme.onPrimary,
                                MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = "去试试>>",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
            Row(Modifier.padding(16.dp, 10.dp, 0.dp, 0.dp)) {
                Text(
                    text = "不知道的事都可以尝试问我哦~",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBottomBanner() {
    Card(
        onClick = { }, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color(0xFF8F5AEC))
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.mipmap.ic_home_banner), null, Modifier
                                .fillMaxSize(), contentScale = ContentScale.FillBounds
                        )
                    }
                    Box(Modifier.align(Alignment.BottomStart)) {
                        TextButton(
                            onClick = {},
                            Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .padding(2.dp)
                                .size(80.dp, 34.dp),
                            colors = ButtonDefaults.buttonColors(
                                MaterialTheme.colorScheme.onPrimary,
                                MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = "AI作画",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }

            Row(Modifier.padding(16.dp, 10.dp, 0.dp, 0.dp)) {
                Text(
                    text = "告诉我你想生成什么风格的图片，我会帮你精心出图哦~",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainGridItem(item: MainContent, color: Color) {
    Card(
        onClick = {}, Modifier
            .clip(RoundedCornerShape(4.dp))
    ) {
        Column(
            Modifier
                .background(color)
                .padding(8.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.title,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.desc,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

data class MainContent(
    val title: String,
    val desc: String
)


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ZgquuTheme {
        //MainGridItem(MainContent("AI翻译官", "精通全球各种自然语言，试试让我帮你翻译"))
    }
}

