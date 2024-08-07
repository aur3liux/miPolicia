package com.aur3liux.naats.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun TextFieldData(
    modifier: Modifier,
    textFieldValue: MutableState<String>,
    textLabel: String,
    txColor: Color = Color.Black,
    maxChar: Int,
    enabled: Boolean = true,
    textPlaceHolder: String = "",
    capitalization: KeyboardCapitalization, //= KeyboardCapitalization.None,
    keyboardType: KeyboardType,
    keyboardActions: KeyboardActions,
    imeAction: ImeAction,
    trailingIcon: @Composable() (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
){
    OutlinedTextField(
        //
        modifier = modifier,
        value = textFieldValue.value,
        onValueChange = {
            if(it.length <= maxChar)
                textFieldValue.value = it
            else {}
        },
        label = {
            Text(text = textLabel,
                style = MaterialTheme.typography.bodyMedium, fontStyle = FontStyle.Italic
            )
        },
        placeholder = { Text(text = textPlaceHolder) },
        enabled = enabled,
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center),
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            capitalization = capitalization,
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = txColor, //color de texto
            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
            focusedPlaceholderColor = Color.Gray
        )
    )
}