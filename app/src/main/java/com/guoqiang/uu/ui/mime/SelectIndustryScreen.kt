package com.guoqiang.uu.ui.mime

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.guoqiang.uu.R
import com.guoqiang.uu.navigation.UU_MIME_SELECT_INDUSTRY
import com.guoqiang.uu.ui.icon.Icon
import com.guoqiang.uu.ui.theme.ZgquuTheme

/**
 * author: zgq
 * date: 2023/3/12 14:03
 * destcription:
 */


private data class SelectIndustryItem(val text: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectIndustryScreen(onBackClick: () -> Unit) {
    LazyColumn {
        item {
            CenterAlignedTopAppBar(title = {
                Text(text = "选择行业")
            }, navigationIcon = {
                Image(imageVector = Icons.Filled.ArrowBack, null, modifier = Modifier.clickable {
                    onBackClick.invoke()
                })
            })
        }
        val selectIndustryItems = arrayListOf<SelectIndustryItem>()
        val selectedTexted = "金融"
        selectIndustryItems.add(SelectIndustryItem("IT/互联网"))
        selectIndustryItems.add(SelectIndustryItem("电子/通信"))
        selectIndustryItems.add(SelectIndustryItem("金融"))
        selectIndustryItems.add(SelectIndustryItem("交通/贸易/物流"))
        selectIndustryItems.add(SelectIndustryItem("消费品"))
        selectIndustryItems.add(SelectIndustryItem("机械/制造"))
        selectIndustryItems.add(SelectIndustryItem("能源/矿产环保"))
        selectIndustryItems.add(SelectIndustryItem("制药/医疗"))
        selectIndustryItems.add(SelectIndustryItem("其他"))
        items(selectIndustryItems) {
            SelectIndustryRowSection(it.text, selectedTexted == it.text, onClick = {
                onBackClick.invoke()
            })
        }

    }
}

@Composable
private fun SelectIndustryRowSection(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(16.dp, 16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title)
            Spacer(modifier = Modifier.weight(1f))
            if (isSelected) {
                Image(Icons.Filled.CheckCircle, null)
            }
        }
        Spacer(modifier = Modifier
            .height(1.dp)
            .padding(16.dp, 0.dp, 0.dp, 0.dp)
            .fillMaxWidth()
            .background(Color(0xFFEDEEF0)))
    }

}


@Preview(showBackground = true)
@Composable
private fun SelectIndustryRowSectionPreview() {
    ZgquuTheme {
        SelectIndustryRowSection("性别", true){}
    }
}



