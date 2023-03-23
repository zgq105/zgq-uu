package com.guoqiang.uu.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.guoqiang.uu.Greeting
import com.guoqiang.uu.R
import com.guoqiang.uu.ui.icon.UUIcons
import com.guoqiang.uu.ui.theme.ZgquuTheme
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
fun HomeScreen() {
    LazyColumn {
        item {
            MainToolbar()
        }
        item {
            MainBanner()
        }

//        GlobalScope.launch {
//            withContext(Dispatchers.IO) {
//                ChatGptTest.testImages()
//            }
//        }
    }
}

@Composable
fun MainToolbar() {
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
                    },
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBanner() {
    Card(
        onClick = { }, modifier = Modifier
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
                                .clip(RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
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
                                .clip(RoundedCornerShape(6.dp, 6.dp, 6.dp, 6.dp))
                                .padding(2.dp)
                                .size(80.dp, 30.dp)
                                ,
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


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ZgquuTheme {
        MainBanner()
    }
}

