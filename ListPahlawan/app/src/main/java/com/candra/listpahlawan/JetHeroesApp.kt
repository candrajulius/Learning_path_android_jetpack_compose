package com.candra.listpahlawan

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.candra.listpahlawan.components.CharacterHeader
import com.candra.listpahlawan.components.HeroListItem
import com.candra.listpahlawan.components.SearchBar
import com.candra.listpahlawan.repository.HeroRepository
import com.candra.listpahlawan.ui.theme.ListPahlawanTheme
import com.candra.listpahlawan.viewmodel.JetHeroesViewModel
import com.candra.listpahlawan.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JetHeroesApp(
    modifier: Modifier = Modifier,
    viewModel: JetHeroesViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(HeroRepository())
    )
){

    val groupedHeroes by viewModel.groupHeroes.collectAsState()
    val query by viewModel.query

    Box(modifier = modifier){
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }
        val context = LocalContext.current

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {

            item {
                SearchBar(query = query, onQueryChange = {viewModel.search(it,context)},
                modifier = Modifier.background(MaterialTheme.colors.primary))
            }

            groupedHeroes.forEach { (initial, heroes) ->
                stickyHeader {
                    CharacterHeader(char = initial)
                }

                items(heroes, key = { it.id }) { hero ->
                    HeroListItem(
                        name = hero.name, photoUrl = hero.photoUrl,
                        modifier = Modifier.fillMaxWidth().animateItemPlacement(
                            tween(durationMillis = 100)
                        )
                    )
                }
            }
        }
        AnimatedVisibility(visible = showButton, enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(), modifier = Modifier
                .padding(bottom = 40.dp, end = 30.dp)
                .align(Alignment.BottomEnd))
        {
            ScrollToTopButton(onClick = {
                scope.launch {
                    listState.animateScrollToItem(index = 0)
                }
            })
        }
    }
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
)
{
    Button(onClick = onClick,modifier = modifier
        .shadow(elevation = 10.dp, shape = CircleShape)
        .clip(shape = CircleShape)
        .size(56.dp),
    colors = ButtonDefaults.buttonColors(
        backgroundColor = Color.White, contentColor = MaterialTheme.colors.primaryVariant
    )) {
        Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = null )
    }
}

@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview(){
    ListPahlawanTheme {
        JetHeroesApp()
    }
}
