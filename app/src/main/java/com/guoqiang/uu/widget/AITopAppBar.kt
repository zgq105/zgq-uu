package com.guoqiang.uu.widget

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.guoqiang.uu.R

/**
 * author: zgq
 * date: 2023/3/25 14:29
 * destcription:
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AICenterAlignedTopAppBar(
    title: String,
    backClick: () -> Unit,
    showAction: Boolean = false,
    actionClick: (() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        modifier = Modifier,
        navigationIcon = {
            Icon(
                imageVector = Icons.Filled.ArrowBack, null,
                Modifier
                    .clickable {
                        backClick.invoke()
                    },
            )
        },
        actions = {
            if (showAction) {
                Icon(
                     painterResource(R.drawable.round_more_horiz_24),
                    null,
                    Modifier
                        .clickable {
                            actionClick?.invoke()
                        },
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

