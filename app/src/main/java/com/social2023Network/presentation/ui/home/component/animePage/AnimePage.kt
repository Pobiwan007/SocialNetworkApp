package com.social2023Network.presentation.ui.home.component.animePage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import com.social2023Network.presentation.ui.theme.colorBlue
import kotlinx.coroutines.launch

@Composable
fun AnimePage(viewModel: HomeViewModel) {

    val animeState by viewModel.mutableDataAnime.collectAsState(initial = AnimeResponse())
    val animeApiState by viewModel.apiState.collectAsState()
    val textField = remember {
        mutableStateOf("")
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleScope = lifecycleOwner.lifecycleScope

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        colorBlue,
                        Color.White
                    )
                )
            )
    ) {
        ApiStateView(
            apiState = animeApiState,
            onSuccessResult = @Composable {
                Column(modifier = Modifier.fillMaxSize()) {

                    AnimeSearchField(
                        textField = textField,
                        onSearch = { viewModel.getDataAnime(filter = textField.value) },
                        lifecycleScope
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(animeState.data) { card ->
                            AnimePost(card, viewModel)
                        }
                    }
                }
            },
            onErrorResult = { lifecycleScope.launch { viewModel.getDataAnime() } }
        )
    }
}

@Composable
fun AnimeSearchField(
    textField: MutableState<String>,
    onSearch: suspend () -> Unit,
    lifecycleScope: LifecycleCoroutineScope,
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
        textStyle = TextStyle(fontSize = 16.sp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp)
    )
}




