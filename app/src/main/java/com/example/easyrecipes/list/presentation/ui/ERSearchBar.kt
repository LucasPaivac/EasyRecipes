package com.example.easyrecipes.list.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ERSearchBar(
    query: String,
    placeHolder: String,
    onValueChange: (String) -> Unit,
    onSearchClicked: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    ElevatedCard(
        modifier = Modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
        shape = RoundedCornerShape(22.dp)
    ){
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .focusRequester(focusRequester = focusRequester)
            ,
            value = query,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    focusRequester.freeFocus()
                    focusManager.clearFocus()
                    onSearchClicked.invoke()
                }
            ),
            placeholder = { Text(placeHolder, fontSize = 16.sp) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
            },
            shape = RoundedCornerShape(22.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ERSearchBarPreview(modifier: Modifier = Modifier) {
    ERSearchBar(
        query = "",
        placeHolder = "Buscar receitas...",
        onValueChange = {},
        onSearchClicked = {}
    )
}