package com.guoqiang.uu.utils

import android.util.Log
import com.guoqiang.uu.BuildConfig

/**
 * author: zgq
 * date: 2023/3/25 20:16
 * destcription:
 */

object LogUtil {
    private const val TAG = "LogUtil"

    fun v(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, msg)
        }
    }

    fun d(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg)
        }
    }

    fun i(msg: String) {
        Log.i(TAG, msg)
    }

    fun w(msg: String) {
        Log.w(TAG, msg)
    }

    fun e(msg: String) {
        Log.e(TAG, msg)
    }
}
