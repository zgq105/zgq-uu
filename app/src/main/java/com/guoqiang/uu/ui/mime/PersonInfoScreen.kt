package com.guoqiang.uu.ui.mime

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guoqiang.uu.R
import com.guoqiang.uu.ui.icon.Icon
import com.guoqiang.uu.ui.theme.ZgquuTheme

/**
 * author: zgq
 * date: 2023/3/12 14:03
 * destcription:
 */


data class PersonInfoItem(val title: String, val desc: String, val isShowLine: Boolean = false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonInfoScreen(onBackClick: () -> Unit) {
    LazyColumn() {
        item {
            CenterAlignedTopAppBar(title = {
                Text(text = "个人信息")
            }, navigationIcon = {
                Image(imageVector = Icons.Filled.ArrowBack, null, modifier = Modifier.clickable {
                    onBackClick.invoke()
                })
            })
        }
        val personInfoItems = arrayListOf<PersonInfoItem>()
        personInfoItems.add(PersonInfoItem("头像", "", true))
        personInfoItems.add(PersonInfoItem("会员等级", "PLUS会员"))
        personInfoItems.add(PersonInfoItem("昵称", "UU", true))
        personInfoItems.add(PersonInfoItem("性别", "男", true))
        personInfoItems.add(PersonInfoItem("生日", "1992-01-01"))
        items(personInfoItems) {
            PersonInfoRowSection(it.title, it.desc, R.drawable.ic_navigate_next)
            if (it.isShowLine) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 0.dp, 0.dp)
                        .height(1.dp)
                        .background(Color(0xFFD5D1D1))
                )
            }
        }
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(Color(0xFFD5D1D1))
            )
        }
        val personInfoItems2 = arrayListOf<PersonInfoItem>()
        personInfoItems2.add(PersonInfoItem("手机号", "13444455555", true))
        personInfoItems2.add(PersonInfoItem("注册地", "广州市"))
        items(personInfoItems2) {
            PersonInfoRowSection(it.title, it.desc, R.drawable.ic_navigate_next)
            if (it.isShowLine) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 0.dp, 0.dp)
                        .height(1.dp)
                        .background(Color(0xFFD5D1D1))
                )
            }
        }
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(Color(0xFFD5D1D1))
            )
        }
        val personInfoItems3 = arrayListOf<PersonInfoItem>()
        personInfoItems3.add(PersonInfoItem("实名认证", "去认证", true))
        personInfoItems3.add(PersonInfoItem("行业", "去完善", true))
        personInfoItems3.add(PersonInfoItem("职业", "去完善"))
        items(personInfoItems3) {
            PersonInfoRowSection(it.title, it.desc, R.drawable.ic_navigate_next)
            if (it.isShowLine) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 0.dp, 0.dp)
                        .height(1.dp)
                        .background(Color(0xFFD5D1D1))
                )
            }
        }
    }
}

@Composable
fun PersonInfoRowSection(title: String, desc: String, @DrawableRes icon: Int) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 0.dp, 0.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = desc)
        Image(painterResource(icon), null)
    }
}

@Preview(showBackground = true)
@Composable
fun PersonInfoRowSectionPreview() {
    ZgquuTheme {
        PersonInfoRowSection("性别", "男性", R.drawable.ic_navigate_next)
    }
}

