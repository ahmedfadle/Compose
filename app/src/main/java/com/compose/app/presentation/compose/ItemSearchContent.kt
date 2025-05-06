package com.compose.app.presentation.compose

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.app.R
import com.compose.app.ui.theme.LocalCornerRadius
import com.compose.app.ui.theme.LocalSmallSpaces
import com.fawry.fawryb2b.core.design_system.theme.LocalSizes


@Composable
fun SearchField(
    modifier: Modifier,
    _query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit
) {


    var query by remember { mutableStateOf("") }
    var isFocused by rememberSaveable { mutableStateOf(false) }
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
                query = _query,
                onQueryChange = {
                   onQueryChange.invoke(it)
                },
                focusRequester = focusRequester,
                onFocusChanged = { isFocused = it },
                onClearFocus = { isFocused = false },
                onClear = {
                    onClear.invoke()
                    query = ""
                    isFocused = false
                }
            )

            androidx.compose.animation.AnimatedVisibility(!isFocused && query.isEmpty()) {
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
            .height(LocalSizes.current.extraLarge)
            .clickable(onClick = onClick)
            .background(
                Color.LightGray,
                RoundedCornerShape(LocalCornerRadius.current.extraSmall)
            )
            .padding(horizontal = LocalSmallSpaces.current.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)

        Spacer(Modifier.width(LocalSmallSpaces.current.small))

        Text(stringResource(R.string.search), color = Color.Gray, fontSize = 12.sp)
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
        textStyle = TextStyle(fontSize = 12.sp),
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(LocalSizes.current.extraLarge)
            .focusRequester(focusRequester)
            .onFocusChanged { onFocusChanged(it.isFocused) },
        placeholder = { Text(stringResource(R.string.search), fontSize = 12.sp) },
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
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true)
@Composable
fun CitySearchScreenPreview() {
    SearchField(
        modifier = Modifier,
        _query ="query",
        onQueryChange = {  },
        onClear = {}
    )
}

@Preview(showBackground = true)
@Composable
fun UnfocusedSearchBarPreview() {
    UnfocusedSearchBar({  })
}


