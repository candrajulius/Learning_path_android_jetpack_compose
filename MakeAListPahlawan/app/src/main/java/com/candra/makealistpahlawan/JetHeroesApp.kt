package com.candra.makealistpahlawan

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.candra.makealistpahlawan.data.HeroRepository
import com.candra.makealistpahlawan.data.JetHeroesViewModel
import com.candra.makealistpahlawan.data.ViewModelFactory
import com.candra.makealistpahlawan.ui.SearchBar
import com.candra.makealistpahlawan.ui.theme.MakeAListPahlawanTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JetHeroesApp(
    modifier: Modifier = Modifier,
    viewModel: JetHeroesViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(HeroRepository())
    )
) {
    
    val groupHeroes by viewModel.groupedHeroes.collectAsState()
    val query by viewModel.query
    
    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf{ listState.firstVisibleItemIndex > 0}
        }

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item{
                SearchBar(query = query, onQueryChange = viewModel::search,
                modifier = Modifier.background(MaterialTheme.colors.primary))
            }
            groupHeroes.forEach { (initial, heroes) ->
                stickyHeader {
                    CharacterHeader(initial)
                }
                items(heroes, key = { it.id }) { hero ->
                    HeroListItem(
                        name = hero.name,
                        photoUrl = hero.photoUrl,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItemPlacement(tween(durationMillis = 100))
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(
                onClick = {
                    scope.launch {
                        listState.scrollToItem(index = 0)
                    }
                }
            )
        }
    }
}

@Composable
fun CharacterHeader(
    char: Char,
    modifier: Modifier = Modifier
){
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = modifier
    ){
        Text(
            text = char.toString(),
            fontWeight = FontWeight.Black,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .shadow(10.dp, shape = CircleShape)
            .clip(shape = CircleShape)
            .size(56.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = MaterialTheme.colors.primary
        )
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = stringResource(R.string.scroll_to_top),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview()
{
    MakeAListPahlawanTheme {
        JetHeroesApp()
    }
}

/*
Kesimpulan
shadow => Untuk memberikan efek bayangan
colors pada Button => untuk memberikan warna tombol dengan bantuan ButtonDefaults.buttonColors untuk
mendefinisikan dua hal, yakni

 backgroundColor => memberikan warna background tombol.
 contentColor => memberikan warna konten, dalam hal ini adalah icon

rememberCoroutineScope digunakan untuk menjalankan suspend function di dalam Composable Function
rememberLazyListState merupakan state dari Lazy List yang digunakan untuk mengontrol dan membaca
posisi item
firstVisibleItemIndex digunakan untuk mengetahui index item pertama yang terlihat di layar
Variabel showButton akan menyimpan state menggunakan derivdeStateOf ketika index item pertama yang
dilihat lebih dari 0 alias item pertama sudah tidak terlihat. Dengan derivedStateOf, nilai showButton hanya
akan diperbarui ketika di state di dalamnya berubah, sehingga proses composition menjadi lebih efektif

 Jika menggunakan State pada ViewModel, Anda tidak perlu mengubahnya lagi

 Karena sudah menggunakan key, Anda pun dapat menambahkan animasi ketika posisi item berubah dengan
 menambahkan modifier animateItemPlacement

 
 */