package com.guoqiang.uu.ui.mime

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guoqiang.uu.ui.theme.ZgquuTheme

/**
 * author: zgq
 * date: 2023/3/12 20:21
 * destcription:
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifiedInfo(onBackClick: () -> Unit) {
    LazyColumn {
        item {
            CenterAlignedTopAppBar(title = {
                Text(text = "")
            }, navigationIcon = {
                Image(imageVector = Icons.Filled.ArrowBack, null, modifier = Modifier.clickable {
                    onBackClick.invoke()
                })
            })
        }
        item {
            Text(
                text = "实名认证",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        item {
            Text(
                text = "1.共建安全物流平台",
                modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 8.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
        item {
            Text(
                text = "2.提升账号安全，切实保障您的利益",
                modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
        item {
            Spacer(
                modifier = Modifier
                    .height(30.dp)
                    .fillMaxWidth()
            )
        }
        item {
            TextInputSection("姓名", "请输入姓名")
        }
        item {
            TextInputSection("身份证号", "请输入本人身份证号")
        }
        item {
            TextInputSection("手机号", "请输入本人手机号")
        }
        item {
            VerificationCodeInputSection("", "请输入验证码")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificationCodeInputSection(label: String, hit: String) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(16.dp, 16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            var value by remember { mutableStateOf("") }
            TextField(value, modifier = Modifier.weight(1f), onValueChange = fun(inputText) {
                value = inputText
            }, label = {
                Text(text = label)
            }, placeholder = {
                Text(text = hit)
            })
            Spacer(Modifier.width(8.dp))
            OutlinedButton(onClick = {
                //Toast.makeText(LocalContext.current,"")
            }) {
                Text(text = "发送验证码")
            }
        }
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .padding(16.dp, 0.dp, 0.dp, 0.dp)
                .fillMaxWidth()
                .background(Color(0xFFEDEEF0))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputSection(label: String, hit: String) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(16.dp, 16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            var value by remember { mutableStateOf("") }
            TextField(value, modifier = Modifier.fillMaxWidth(), onValueChange = fun(inputText) {
                value = inputText
            }, label = {
                Text(text = label)
            }, placeholder = {
                Text(text = hit)
            })
        }
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .padding(16.dp, 0.dp, 0.dp, 0.dp)
                .fillMaxWidth()
                .background(Color(0xFFEDEEF0))
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TextInputSectionPreview() {
    ZgquuTheme {
        TextInputSection("性别", "请输入关键字")
    }
}