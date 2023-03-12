package com.social2023Network.presentation.ui.home.component.animePage

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.social2023Network.domain.model.anime.AnimeResponse
import com.social2023Network.presentation.ui.home.HomeViewModel
import com.social2023Network.presentation.ui.home.component.ApiStateView
import com.social2023Network.presentation.ui.home.component.util.Background
import kotlinx.coroutines.launch

@Composable
fun AnimePage(viewModel: HomeViewModel) {

    val animeState by viewModel.mutableDataAnime.collectAsState(initial = AnimeResponse())
    val animeApiState by viewModel.apiState.collectAsState()
    val textField = rememberSaveable {
        mutableStateOf("")
    }
    var isSearchFieldExpanded by remember { mutableStateOf(true) }
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val lazyListState = rememberLazyListState()

    Background {
        ApiStateView(
            apiState = animeApiState,
            onSuccessResult = @Composable {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .heightIn(max = if (!isSearchFieldExpanded) 120.dp else 0.dp)
                            .animateContentSize()
                            .clip(RoundedCornerShape(10.dp))
                            .padding(top = 10.dp, bottom = 5.dp),
                    ) {
                        AnimeSearchField(
                            textField = textField,
                            onSearch = { viewModel.getDataAnime(filter = textField.value) },
                            lifecycleScope,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 13.dp),
                        )
                    }
                    LazyColumn(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp)), state = lazyListState
                    ) {
                        items(animeState.data) { card ->
                            AnimePost(card, viewModel)
                        }
                    }
                }
            },
            onErrorResult = { lifecycleScope.launch { viewModel.getDataAnime() } }
        )
        LaunchedEffect(lazyListState.isScrollInProgress) {
            isSearchFieldExpanded = lazyListState.isScrollInProgress
        }
    }
}

@Composable
fun AnimeSearchField(
    textField: MutableState<String>,
    onSearch: suspend () -> Unit,
    lifecycleScope: LifecycleCoroutineScope,
    modifier: Modifier
) {

    TextField(
        value = textField.value,
        onValueChange = { textField.value = it },
        label = { Text(text = "Search") },
        placeholder = { Text(text = "Enter a search term") },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = {
            lifecycleScope.launch {
                onSearch()
            }
        }),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = modifier,
        textStyle = TextStyle(fontSize = 16.sp),
        shape = RoundedCornerShape(20.dp)
    )
}




