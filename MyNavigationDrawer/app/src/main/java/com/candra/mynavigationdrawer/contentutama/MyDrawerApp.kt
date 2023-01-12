package com.candra.mynavigationdrawer.contentutama

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.candra.mynavigationdrawer.data.DataMenuItem.items
import com.candra.mynavigationdrawer.R
import com.candra.mynavigationdrawer.rememberMyNavDrawerState
import com.candra.mynavigationdrawer.ui.theme.MyNavigationDrawerTheme

@Composable
fun MyDrawerApp()
{
    val appState = rememberMyNavDrawerState()

    Scaffold(
        scaffoldState = appState.scaffoldState,
        topBar = {
            MyTopBar(
                onMenuClick = appState::onMenuClick
            )
        },
        drawerContent = {
            MyDrawerContent(
               onItemSelected = appState::onItemSelected,
                onBackPressed = appState::onBackPress,
            )
        },
        drawerGesturesEnabled = appState.scaffoldState.drawerState.isOpen
    ){paddingValues ->  
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(id = R.string.hello_world))
        }
    }
}

@Composable
fun MyDrawerContent(
    modifier: Modifier = Modifier,
    onItemSelected: (title: String) -> Unit,
    onBackPressed: () -> Unit
){
    Column(
        modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier
            .height(190.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary))
        {
           Column(
               modifier = Modifier.padding(top = 50.dp, start = 8.dp, bottom = 10.dp),
               horizontalAlignment = Alignment.CenterHorizontally,
           ) {
               Image(
                   painter = painterResource(id = R.drawable.ic_launcher_background),
                   contentDescription = null,
                   Modifier
                       .size(80.dp)
                       .padding(bottom = 10.dp))
               Text(
                   text = stringResource(id = R.string.name_developer),
                   style = MaterialTheme.typography.subtitle1.copy(
                       fontWeight = FontWeight.Bold
                   )
               )
           }
        }
        for (item in items){
            Row(
                modifier = Modifier
                    .clickable { onItemSelected(item.title) }
                    .padding(vertical = 12.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(imageVector = item.icon, contentDescription = item.title, tint = Color.DarkGray)
                Spacer(modifier = Modifier.width(32.dp))
                Text(text = item.title, style = MaterialTheme.typography.subtitle2)
            }
        }
        BackPressHandler{
            // Do Something
            onBackPressed()

        }
        Divider()
    }
}

@Composable
fun BackPressHandler(
    enabled: Boolean = true,
    onBackPressed: () -> Unit
){
    val currentBackPressed by rememberUpdatedState(newValue = onBackPressed)
    val backCallback = remember{
        object: OnBackPressedCallback(enabled){
            override fun handleOnBackPressed() {
                currentBackPressed()
            }
        }
    }
    SideEffect {
        backCallback.isEnabled = enabled
    }

    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current){
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher

    val lifecycleOwner  = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner,backDispatcher){
        backDispatcher.addCallback(lifecycleOwner,backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}

@Composable
fun MyTopBar(onMenuClick: () -> Unit){
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                onMenuClick()
            }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = stringResource(id = R.string.menu) )
            }
        },
        title = { Text(text = stringResource(id = R.string.app_name))}
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview()
{
    MyNavigationDrawerTheme {
        MyDrawerApp()
    }
}
/*
Kemudian, untuk menampilkan navigation drawer, cukup gunakan slot drawerContent yagn sudah disediakan
Scaffold
 */