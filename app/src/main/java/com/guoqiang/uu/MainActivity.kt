package com.guoqiang.uu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.trace
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.guoqiang.uu.navigation.TopLevelDestination
import com.guoqiang.uu.navigation.UUNavHost
import com.guoqiang.uu.navigation.navigateToForHome
import com.guoqiang.uu.navigation.navigateToForMime
import com.guoqiang.uu.ui.icon.Icon
import com.guoqiang.uu.ui.icon.UUIcons
import com.guoqiang.uu.ui.theme.ZgquuTheme
import com.guoqiang.uu.viewmodel.MainActivityUiState
import com.guoqiang.uu.viewmodel.MainActivityViewModel
import com.guoqiang.uu.widget.UUNavigationBar
import com.guoqiang.uu.widget.UUNavigationBarItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalData.context = this.applicationContext
        val splashScreen = installSplashScreen()

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        lifecycleScope.launchWhenStarted {
            delay(2000)
            uiState = MainActivityUiState.Success
        }
        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                MainActivityUiState.Loading -> true
                is MainActivityUiState.Success -> false
            }
        }
        setContent {
            ZgquuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UUApp()
                }
            }
        }
    }
}

val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false

fun navigateToTopLevelDestination(
    navController: NavController,
    topLevelDestination: TopLevelDestination
) {
    trace("Navigation: ${topLevelDestination.name}") {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.UU_HOME -> navController.navigateToForHome(topLevelNavOptions)
            TopLevelDestination.UU_MIME -> navController.navigateToForMime(topLevelNavOptions)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun UUApp() {
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()

    val currentDestination = navController
        .currentBackStackEntryAsState().value?.destination

    Scaffold(modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            UUBottomBar(topLevelDestinations, navController, currentDestination, Modifier)
        }
    ) { padding ->
        Row(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            Column(Modifier.fillMaxSize()) {
                UUNavHost(navController = navController)
            }
        }

    }

}

@Composable
private fun UUBottomBar(
    destinations: List<TopLevelDestination>,
    navController: NavController,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    UUNavigationBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            UUNavigationBarItem(
                selected = selected,
                onClick = { navigateToTopLevelDestination(navController, destination) },
                icon = {
                    val icon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }
                    when (icon) {
                        is Icon.ImageVectorIcon -> Icon(
                            imageVector = icon.imageVector,
                            contentDescription = null,
                        )

                        is Icon.DrawableResourceIcon -> Icon(
                            painter = painterResource(id = icon.id),
                            contentDescription = null,
                        )
                    }
                },
                label = { Text(stringResource(destination.iconTextId)) },
            )
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ZgquuTheme {
        Greeting("Android")
    }
}