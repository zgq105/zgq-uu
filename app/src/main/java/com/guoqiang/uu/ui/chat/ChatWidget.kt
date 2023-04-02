package com.guoqiang.uu.ui.chat

import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.guoqiang.uu.R
import kotlinx.parcelize.Parcelize

/**
 * author: zgq
 * date: 2023/3/26 17:22
 * destcription:
 */

@Composable
fun RobotIcon(text: String, modifier: Modifier = Modifier.size(48.dp), iconClick: () -> Unit = {}) {
    Box(modifier = modifier
        .clip(CircleShape)
        .clickable { iconClick.invoke() }
        .background(MaterialTheme.colorScheme.tertiaryContainer),
        contentAlignment = Alignment.Center) {
        Text(
            text = text,
            modifier = Modifier,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun RobotMessageItem(
    robotIconText: String,
    text: String,
    isEditMode: Boolean = false,
    textClick: () -> Unit,
    iconClick: () -> Unit
) {
    Row {
        Spacer(modifier = Modifier.width(8.dp))
        Column() {
            RobotIcon(robotIconText, iconClick = {
                iconClick.invoke()
            })
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column() {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFCFD5C9))
                    .clickable { textClick.invoke() }) {
                    Text(
                        text = text,
                        modifier = Modifier.padding(8.dp),
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
            }
            if (isEditMode) {
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Spacer(modifier = Modifier.width(20.dp))
                    Box(
                        Modifier
                            .clickable {

                            }
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onPrimary)

                    ) {
                        Image(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null,
                            Modifier.padding(6.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        Modifier
                            .clickable {

                            }
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onPrimary)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_copy_all_24),
                            contentDescription = null,
                            Modifier.padding(6.dp),
                        )
                    }

                }
            }

        }
    }
}

@Composable
fun UserMessageItem(
    text: String,
    isEditMode: Boolean = false,
    textClick: () -> Unit,
    iconClick: () -> Unit = {}
) {
    Column() {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(20.dp))
            Box(modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFA7ECF5))
                .clickable { textClick.invoke() }) {
                Text(
                    text = text,
                    modifier = Modifier.padding(8.dp),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
            Icon(painter = painterResource(id = R.drawable.ic_person_48),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        iconClick.invoke()
                    })
            Spacer(modifier = Modifier.width(8.dp))
        }

        if (isEditMode) {
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Spacer(modifier = Modifier.width(20.dp))
                Box(
                    Modifier
                        .clickable {

                        }
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onPrimary)

                ) {
                    Image(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = null,
                        Modifier.padding(6.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    Modifier
                        .clickable {

                        }
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onPrimary)) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_copy_all_24),
                        contentDescription = null,
                        Modifier.padding(6.dp),
                    )
                }

            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserChatInput(sendClick: (String) -> Unit) {
    //var text by remember { mutableStateOf("") } //只在内存中，页面销毁重建数据丢失
    var text by rememberSaveable {
        mutableStateOf("")
    } //页面销毁重建数据不会丢失

    Row(
        Modifier.clip(RoundedCornerShape(12.dp)), verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        TextField(value = text, onValueChange = fun(value) {
            text = value
        }, modifier = Modifier.weight(1f), placeholder = {
            Text(text = "请输入")
        }, singleLine = true)
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Send,
                contentDescription = null,
                modifier = Modifier.clickable {
                    sendClick.invoke(text)
                    text = ""
                },
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowMoreDialog(onDismissRequest: () -> Unit) {
    ModalBottomSheet(onDismissRequest = { onDismissRequest.invoke() }) {
        Column {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFDFE1E2))
                    .height(1.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clickable { },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "拍照"
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFDFE1E2))
                    .height(1.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clickable { },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "从相册中选择"
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFDFE1E2))
                    .height(6.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clickable { },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "取消"
                )
            }
        }
    }
}