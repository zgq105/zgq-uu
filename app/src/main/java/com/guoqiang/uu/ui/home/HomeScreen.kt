package com.guoqiang.uu.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.guoqiang.uu.R
import com.guoqiang.uu.navigation.UU_CHAT_AI_DRAWING_ROUTE
import com.guoqiang.uu.navigation.UU_CHAT_AI_MAJORDOMO_ROUTE
import com.guoqiang.uu.ui.theme.*
import com.guoqiang.uu.utils.LogUtil
import com.guoqiang.uu.viewmodel.UserViewModel

/**
 * author: zgq
 * date: 2023/3/12 10:48
 * destcription:
 */
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val userViewModel: UserViewModel = hiltViewModel()

    LazyColumn {
        item {
            MainToolbar(settingClick = {
                userViewModel.getAll()
            })
        }
        item {
            MainBanner {
                navController.navigate(UU_CHAT_AI_MAJORDOMO_ROUTE)
            }
        }
        item {
            val contentItems = arrayListOf(
                MainContent("AI翻译官", "精通全球各种自然语言，试试让我帮你翻译"),
                MainContent("Al作家", "我会文案/检讨书/演讲稿等，说出你的要求吧"),
                MainContent("AI厨师", "试试问问我美味佳肴是如何做出来的吧"),
                MainContent("Al诗人", "试试让我按照您的要求帮您创作独一无二的诗")
            )
            val colors = arrayListOf(
                Color(0xFF22ACEB),
                Color(0xFFEC5B8C),
                Color(0xFFF0C33B),
                Color(0xFFF39E20)
            )
            FlowRow(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                val configuration = LocalConfiguration.current
                val width = (configuration.screenWidthDp / 2).dp - 4.dp - 16.dp
                val mainGridItemModifier = Modifier
                    .width(width)
                    .clip(RoundedCornerShape(4.dp))
                    .padding(bottom = 8.dp)
                for (index in contentItems.indices) {
                    MainGridItem(contentItems[index], colors[index], mainGridItemModifier)
                }
            }
        }
        item {
            MainBottomBanner{
                navController.navigate(UU_CHAT_AI_DRAWING_ROUTE)
            }
        }
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
                            onClick = {
                                onclick.invoke()
                            },
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
                            onClick = {
                                onclick.invoke()
                            },
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
fun MainBottomBanner(onClick: () -> Unit) {
    Card(
        onClick = {
            onClick.invoke()
        }, modifier = Modifier
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
                            onClick = { onClick.invoke() },
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

            Row(Modifier.padding(start = 16.dp, top = 10.dp)) {
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
fun MainGridItem(item: MainContent, color: Color, modifier: Modifier) {
    Card(
        onClick = {},
        modifier = modifier
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

