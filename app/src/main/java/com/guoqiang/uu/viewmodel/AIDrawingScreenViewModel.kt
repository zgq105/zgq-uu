package com.guoqiang.uu.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * author: zgq
 * date: 2023/4/8 15:02
 * destcription:
 */
class AIDrawingScreenViewModel : ViewModel() {
    var imageUrlsLiveData = MutableLiveData<List<String>>()
}