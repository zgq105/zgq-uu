package com.guoqiang.uu.ui.chat

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.*
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
import com.guoqiang.uu.R
import com.guoqiang.uu.navigation.UU_MIME_PERSON_INFO_ROUTE
import com.guoqiang.uu.ui.icon.Icon
import com.guoqiang.uu.ui.theme.PurpleGrey80
import com.guoqiang.uu.ui.theme.ZgquuTheme
import com.guoqiang.uu.widget.AICenterAlignedTopAppBar

/**
 * author: zgq
 * date: 2023/3/11 15:33
 * destcription:
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIMajordomoScreen(navController: NavController) {
    LazyColumn() {
        item {
            AICenterAlignedTopAppBar("AI管家", {
                navController.popBackStack()
            }, true, {

            })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NormalFunctionSectionPreview() {
    ZgquuTheme {

    }
}


