package com.guoqiang.uu.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.guoqiang.uu.Greeting
import com.guoqiang.uu.ui.icon.UUIcons

/**
 * author: zgq
 * date: 2023/3/12 10:48
 * destcription:
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Column {
        CenterAlignedTopAppBar(
            actions = {
                IconButton(onClick = {} ) {
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
    }
}