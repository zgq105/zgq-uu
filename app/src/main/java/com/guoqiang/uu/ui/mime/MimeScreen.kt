package com.guoqiang.uu.ui.mime

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.guoqiang.uu.Greeting
import com.guoqiang.uu.R
import com.guoqiang.uu.navigation.UU_MIME_PERSON_INFO_ROUTE
import com.guoqiang.uu.ui.icon.Icon
import com.guoqiang.uu.ui.theme.PurpleGrey80
import com.guoqiang.uu.ui.theme.ZgquuTheme

/**
 * author: zgq
 * date: 2023/3/11 15:33
 * destcription:
 */
@Composable
fun MimeScreen(navController: NavController) {

    Column(
        Modifier
            .padding(16.dp, 12.dp)
    ) {
        PersonHeadArea(PersonInfo("UU12334444", "plus会员2")) {
            navController.navigate(UU_MIME_PERSON_INFO_ROUTE)
        }
        Spacer(Modifier.height(20.dp))
        PersonOrderStatusSection()
        Spacer(Modifier.height(8.dp))
        NormalFunctionSection()
        Spacer(Modifier.height(8.dp))
        OtherService()
    }
}


data class PersonInfo(val name: String, val userLevel: String)

@Composable
fun PersonHeadArea(personInfo: PersonInfo,onPersonInfoClick: () -> Unit) {
    Row (
        Modifier
            .fillMaxWidth()
            .clickable {
                onPersonInfoClick.invoke()
            }){
        Image(
            painter = painterResource(R.drawable.ic_person_icon),
            contentDescription = "",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .border(2.dp, Color.DarkGray, CircleShape)
        )
        Spacer(Modifier.width(8.dp))
        Column {
            Text(text = personInfo.name, style = MaterialTheme.typography.titleLarge)
            Row(
                Modifier
                    .clip(RoundedCornerShape(10))
                    .background(Color.Gray)
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = personInfo.userLevel, style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.width(8.dp))
                Image(Icons.Rounded.Email, null)
            }
        }

    }
}

@Composable
fun PersonOrderStatusSection() {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
    ) {
        Row(Modifier.background(PurpleGrey80)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                ImageTextVertical(Icons.Filled.Phone, "待支付", Modifier)
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                ImageTextVertical(Icons.Filled.Phone, "待接单", Modifier)
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                ImageTextVertical(Icons.Filled.Phone, "进行中", Modifier)
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                ImageTextVertical(Icons.Filled.Phone, "已完成", Modifier)
            }
        }
    }
}


@Composable
fun ImageTextVertical(imageVector: ImageVector, text: String, modifier: Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Image(imageVector, null)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun NormalFunctionSection() {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
    ) {
        Column(Modifier.background(PurpleGrey80)) {
            Row(Modifier.padding(16.dp, 8.dp)) {
                Text(
                    text = "常用功能",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Row {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    ImageTextVertical(Icons.Filled.MailOutline, "钱包", Modifier)
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    ImageTextVertical(Icons.Filled.Menu, "消息", Modifier)
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    ImageTextVertical(Icons.Filled.LocationOn, "客服", Modifier)
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    ImageTextVertical(Icons.Filled.Settings, "设置", Modifier)
                }
            }
        }

    }
}

data class ServiceItem(val imageVector: ImageVector, val text: String)

@Composable
fun OtherServiceGrid(serviceItems: List<ServiceItem>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 60.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        items(serviceItems) { item ->
            ImageTextVertical(
                imageVector = item.imageVector,
                text = item.text,
                Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun OtherService() {
    Card(
        modifier = Modifier
            .background(Color.White)
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
    ) {
        Column(Modifier.background(PurpleGrey80)) {
            Row(Modifier.padding(16.dp, 8.dp)) {
                Text(
                    text = "其他服务",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            val list = arrayListOf<ServiceItem>()
            list.add(ServiceItem(Icons.Outlined.Favorite, "口令优惠券"))
            list.add(ServiceItem(Icons.Outlined.Favorite, "意见反馈"))
            list.add(ServiceItem(Icons.Outlined.Favorite, "保险服务"))
            list.add(ServiceItem(Icons.Outlined.Favorite, "声请商户"))
            list.add(ServiceItem(Icons.Outlined.Favorite, "企业账号"))
            list.add(ServiceItem(Icons.Outlined.Favorite, "接单赚钱"))
            OtherServiceGrid(list)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PersonOrderStatusSectionPreview() {
    ZgquuTheme {
        PersonOrderStatusSection()
    }
}

@Preview(showBackground = true)
@Composable
fun NormalFunctionSectionPreview() {
    ZgquuTheme {
        NormalFunctionSection()
    }
}


