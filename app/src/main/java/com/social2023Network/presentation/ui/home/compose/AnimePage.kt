package com.social2023Network.presentation.ui.home.compose

import android.util.Log
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.social2023Network.data.remote.anime.AnimeEntity
import com.social2023Network.data.remote.anime.AnimeResponse
import com.social2023Network.presentation.ui.home.HomeViewModel
import com.social2023Network.presentation.ui.theme.colorBlue
import kotlinx.coroutines.launch

@Composable
fun AnimePage(viewModel: HomeViewModel) {
    val animeState by viewModel.mutableDataAnime.collectAsState(initial = AnimeResponse())
    val animeApiState by viewModel.apiState.collectAsState()
    val textField = remember {
        mutableStateOf("")
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "",
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
        )
        ApiStateView(apiState = animeApiState, onSuccessResult = @Composable {
            SearchTextField(
                searchQuery = textField.value,
                onSearchQueryChanged = { textField.value = it },
                viewModel
            )
            LazyColumn {
                items(animeState.data) { card ->
                    CardItem(card)
                }
            }
        })

    }
}

@Composable
private fun CardItem(card: AnimeEntity) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Column(modifier = Modifier.padding(bottom = 16.dp)) {
            AsyncImage(
                model = card.attributes?.coverImage?.original,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = card.attributes?.titles?.en.toString(),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .weight(1f), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = card.attributes?.description.toString(),
                    style = MaterialTheme.typography.body1,
                )
                Button(
                    onClick = { /* Handle button click */ },
                    modifier = Modifier.fillMaxWidth(0.3f)
                ) {
                    Text(text = "Read More")
                }
            }

        }
    }
}

@Composable
fun SearchTextField(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    viewModel: HomeViewModel
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleScope = lifecycleOwner.lifecycleScope
    TextField(
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        label = { Text(text = "Search") },
        placeholder = { Text(text = "Enter a search term") },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = {
            Log.e("Search", searchQuery)
            lifecycleScope.launch {
                viewModel.getData(filter = searchQuery)
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
        textStyle = TextStyle(
            fontSize = 16.sp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(10.dp)
    )
}


