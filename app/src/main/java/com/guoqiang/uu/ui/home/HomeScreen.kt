package com.guoqiang.uu.ui.home

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.completion.CompletionRequest
import com.aallam.openai.api.completion.TextCompletion
import com.aallam.openai.api.edits.EditsRequest
import com.aallam.openai.api.file.FileSource
import com.aallam.openai.api.image.ImageCreation
import com.aallam.openai.api.image.ImageEdit
import com.aallam.openai.api.image.ImageSize
import com.aallam.openai.api.image.ImageVariation
import com.aallam.openai.api.model.Model
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.guoqiang.uu.GlobalData
import com.guoqiang.uu.Greeting
import com.guoqiang.uu.ui.icon.UUIcons
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
    Column {
        CenterAlignedTopAppBar(
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = UUIcons.Settings,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            },
            title = {
                Text(text = "UU")
            })

        Greeting("HOME")

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                //testCompletion()
//                testChat()
                //testEdits()
//                testImages()
//                testEditImages()
//                testImageVariation()
            }
        }
    }
}


@OptIn(BetaOpenAI::class)
suspend fun testImageVariation(){
    val openAI = OpenAI(API_KEY)
    val context = GlobalData.context ?: return
    val imageFileSource = context.resources.openRawResource(com.guoqiang.uu.R.raw.test1).source()
    val images = openAI.imageURL(
        variation = ImageVariation(
            image = FileSource(name = "image.png", source = imageFileSource),
            n = 1,
            size = ImageSize.is512x512
        )
    )
    images.forEach {
        Log.d("zgq", "image-url:${it.url}")
    }
}

//todo
@OptIn(BetaOpenAI::class)
suspend fun testEditImages(){
    val openAI = OpenAI(API_KEY)
    val context = GlobalData.context ?: return
    val imageFileSource = context.resources.openRawResource(com.guoqiang.uu.R.raw.test1).source()
    val maskFileSource = context.resources.openRawResource(com.guoqiang.uu.R.raw.test2).source()
    val images = openAI.imageURL(
        edit = ImageEdit(
            image = FileSource("image.png",imageFileSource),
            mask = FileSource("mask.png",maskFileSource),
            prompt = "a sunlit indoor lounge area with a pool containing a flamingo",
            n = 1,
            size = ImageSize.is512x512
        )
    )
    images.forEach {
        Log.d("zgq", "image-url:${it.url}")
    }
}

@OptIn(BetaOpenAI::class)
suspend fun testImages() {
    val openAI = OpenAI(API_KEY)
    val imageURL = openAI.imageURL(
        ImageCreation(
            "一个杯子，年轻化元素，设计一个产品",
            2, ImageSize.is512x512
        )
    )
    imageURL.forEach {
        Log.d("zgq", "image-url:${it.url}")
    }
}

suspend fun testEdits() {
    val openAI = OpenAI(API_KEY)
    val edit = openAI.edit(
        request = EditsRequest(
            model = ModelId("text-davinci-edit-001"),
            input = "中华人民功和国",
            instruction = "修改错别字"
        )
    )
    edit.choices.forEach {
        Log.d("zgq", "choice-text:${it.text}")
        Log.d("zgq", "choice-finishReason:${it.finishReason}")
    }
}

@OptIn(BetaOpenAI::class)
suspend fun testChat() {
    val openAI = OpenAI(API_KEY)
    val chatCompletionRequest = ChatCompletionRequest(
        model = ModelId("gpt-3.5-turbo"),
        messages = listOf(
            ChatMessage(
                role = ChatRole.User,
                content = "曾国藩是谁"
            )
        )
    )
    val completion = openAI.chatCompletion(chatCompletionRequest)
    completion.choices.forEach {
        Log.d("zgq", "choice-name:${it.message?.name}")
        Log.d("zgq", "choice-content:${it.message?.content}")
    }
}

const val API_KEY = "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"

suspend fun testCompletion() {
    val openAI = OpenAI(API_KEY)
    val models = openAI.models()
    models.forEach {
        Log.d("zgq", "model:${it.id.id}")
    }

    val completionRequest = CompletionRequest(
        model = ModelId("text-ada-001"),
        prompt = "where is china",
        echo = true
    )
    val completion: TextCompletion = openAI.completion(completionRequest)
    completion.choices.forEach {
        Log.d("zgq", "choice:${it.text}")
    }

}