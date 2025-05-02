package com.compose.app.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.app.presentation.CitiesIntent
import com.compose.app.presentation.viewmodel.CitiesViewModel


@Composable
fun CitySearchScreen(modifier: Modifier,viewModel: CitiesViewModel) {
    val state by viewModel.citiesState.collectAsState()


    var query by remember { mutableStateOf("") }
    var isFocused by rememberSaveable   { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(isFocused) {
        if (isFocused) focusRequester.requestFocus()
        else focusManager.clearFocus()
    }


    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = modifier
                .background(color = Color.White)
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            FocusedSearchBar(
                query = state.query,
                onQueryChange = { if (it.trim().isBlank()) viewModel.onEvent(CitiesIntent.ClearQuery) else viewModel.onEvent(CitiesIntent.EnterQuery(it)) },
                focusRequester = focusRequester,
                onFocusChanged = { isFocused = it },
                onClearFocus = { isFocused = false },
                onClear = {
                    viewModel.onEvent(CitiesIntent.ClearQuery)
                    query = ""
                    isFocused = false
                }
            )

            if (!isFocused && state.query.isEmpty()) {
                UnfocusedSearchBar {
                    isFocused = true
                    focusRequester.requestFocus()
                }
            }
        }

    }
}

@Composable
private fun UnfocusedSearchBar(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .clickable(onClick = onClick)
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
        Spacer(Modifier.width(8.dp))
        Text("Search...", color = Color.Gray)
    }
}

@Composable
private fun FocusedSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    focusRequester: FocusRequester,
    onFocusChanged: (Boolean) -> Unit,
    onClearFocus: () -> Unit,
    onClear: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged { onFocusChanged(it.isFocused) },
        placeholder = { Text("Search") },
        leadingIcon = { Icon(Icons.Default.Search, null, tint = Color.Black) },
        trailingIcon = {
            Icon(imageVector = Icons.Default.Clear,contentDescription= null, modifier = Modifier.clickable {
                onClearFocus()
                focusManager.clearFocus()
                onClear()

            }, tint = Color.Black)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onSearch = { /* Handle search */ }
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true)
@Composable
fun CitySearchScreenPreview() {
    CitySearchScreen(Modifier, TODO())
}


