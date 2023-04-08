package com.guoqiang.uu

import android.util.Log
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
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.api.moderation.ModerationRequest
import com.aallam.openai.client.OpenAI
import okio.source

/**
 * author: zgq
 * date: 2023/3/23 00:05
 * destcription:
 */
object ChatGptHelper {
    /**
     * 判断输入是否违规
     */
    @OptIn(BetaOpenAI::class)
    suspend fun testModeration() {
        val openAI = OpenAI(API_KEY)
        val moderation = openAI.moderations(
            request = ModerationRequest(
                input = arrayListOf("I want to kill them")
            )
        )
        Log.d("zgq", "moderation,model:" + moderation.model + ",results:" + moderation.results)
    }

    /**
     * 文件 todo zgq
     */
    @OptIn(BetaOpenAI::class)
    suspend fun testFile() {
        val openAI = OpenAI(API_KEY)
        val files = openAI.files()
        Log.d("zgq", "files:" + files.size)


        val context = GlobalData.context ?: return
        val imageFileSource =
            context.resources.openRawResource(com.guoqiang.uu.R.raw.test1).source()

        //上传文件
        val file = openAI.file(
            request = FileUpload(
                file = FileSource("test1.png", imageFileSource),
                purpose = Purpose("fine-tune")
            )
        )
        Log.d("zgq", "file.id:" + file.id)
        files.forEach {
            Log.d("zgq", "file,id:${it.id},filename:${it.filename}")
        }
    }

    /**
     * 音频转英文文本
     */
    @OptIn(BetaOpenAI::class)
    suspend fun testAudioTranslation() {
        val openAI = OpenAI(API_KEY)
        val context = GlobalData.context ?: return
        val audioSource = context.resources.openRawResource(com.guoqiang.uu.R.raw.audio).source()
        val request = TranslationRequest(
            audio = FileSource(name = "audio.m4a", source = audioSource),
            model = ModelId("whisper-1"),
        )
        val transcription = openAI.translation(request)
        Log.d("zgq", "audio-english:${transcription.text}")
    }

    /**
     * 音频转文本
     */
    @OptIn(BetaOpenAI::class)
    suspend fun testAudio() {
        val openAI = OpenAI(API_KEY)
        val context = GlobalData.context ?: return
        val audioSource = context.resources.openRawResource(com.guoqiang.uu.R.raw.audio).source()
        val request = TranscriptionRequest(
            audio = FileSource(name = "audio.m4a", source = audioSource),
            model = ModelId("whisper-1"),
        )
        val transcription = openAI.transcription(request)
        Log.d("zgq", "audio-text:${transcription.text}")
    }

    /**
     * 图片变形
     */
    @OptIn(BetaOpenAI::class)
    suspend fun testImageVariation() {
        val openAI = OpenAI(API_KEY)
        val context = GlobalData.context ?: return
        val imageFileSource =
            context.resources.openRawResource(com.guoqiang.uu.R.raw.test1).source()
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
    suspend fun testEditImages() {
        val openAI = OpenAI(API_KEY)
        val context = GlobalData.context ?: return
        val imageFileSource =
            context.resources.openRawResource(com.guoqiang.uu.R.raw.test1).source()
        val maskFileSource = context.resources.openRawResource(com.guoqiang.uu.R.raw.test2).source()
        val images = openAI.imageURL(
            edit = ImageEdit(
                image = FileSource("test1.png", imageFileSource),
                mask = FileSource("test2.png", maskFileSource),
                prompt = "a sunlit indoor lounge area with a pool containing a flamingo",
                n = 1,
                size = ImageSize.is512x512
            )
        )
        images.forEach {
            Log.d("zgq", "image-url:${it.url}")
        }
    }

    /**
     * 根据文字描述生成图片
     */
    @OptIn(BetaOpenAI::class)
    suspend fun createImages(prompt: String, imageSize: ImageSize): List<String> {
        val images = arrayListOf<String>()
        try {
            val openAI = OpenAI(API_KEY)
            val imageURL = openAI.imageURL(
                ImageCreation(
                    prompt,
                    4, imageSize
                )
            )
            imageURL.forEach {
                Log.d("zgq", "image-url:${it.url}")
                images.add(it.url)
            }
        } catch (e: Exception) {
            Log.d("zgq", e.message.toString())
        }
        return images
    }

    /**
     * 文本纠错
     */
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

    /**
     * 聊天
     */
    @OptIn(BetaOpenAI::class)
    suspend fun chatAIMajordomo(input: String): List<String> {
        val result = arrayListOf<String>()
        try {
            val openAI = OpenAI(API_KEY)
            val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId("gpt-3.5-turbo"),
                messages = listOf(
                    ChatMessage(
                        role = ChatRole.User,
                        content = input
                    )
                )
            )
            val completion = openAI.chatCompletion(chatCompletionRequest)

            completion.choices.forEach {
                Log.d("zgq", "choice-name:${it.message?.name}")
                Log.d("zgq", "choice-content:${it.message?.content}")
                it.message?.content?.let { it1 -> result.add(it1) }
            }
        } catch (e: Exception) {
            Log.e("zgq", e.message.toString())
        }
        return result
    }

    const val API_KEY = "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"

    /**
     * 根据提示，输出文本选项
     */
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
}