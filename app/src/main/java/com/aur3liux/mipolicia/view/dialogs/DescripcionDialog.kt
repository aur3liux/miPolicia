package com.aur3liux.mipolicia.view.dialogs


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.components.DialogButton

@Composable
fun DescripcionDialog(
    descripcion: MutableState<String>,
    context: Context,
    onConfirmation: () -> Unit,
    spaceBetweenElements: Dp = 18.dp) {

        Dialog(onDismissRequest = {
            onConfirmation()
        }) {
            Surface(
                color = Color.Transparent // dialog background
            ) {
                ToolBox.soundEffect(context, R.raw.flash)

                    // text and buttons
                    Column(
                        modifier = Modifier
                            .padding(top = 30.dp) // this is the empty space at the top
                            .verticalScroll(state = rememberScrollState())
                            .background(
                                color = MaterialTheme.colorScheme.background,
                                shape = RoundedCornerShape(percent = 10)
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(height = 36.dp))
                        //DESCRIPCION
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 5.dp),
                            text = "Relata brevemente los hechos ${descripcion.value.length}/400",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .padding(horizontal = 30.dp),
                            shape = RoundedCornerShape(5.dp),
                            value = descripcion.value,
                            maxLines = 105,
                            singleLine = false,
                            onValueChange = {
                                if (it.length <= 400)
                                    descripcion.value = it
                            },
                            textStyle = TextStyle(
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            ),
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                keyboardType = KeyboardType.Text
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                focusedContainerColor = MaterialTheme.colorScheme.background,
                                focusedTextColor = MaterialTheme.colorScheme.surfaceVariant, //color de texto
                                unfocusedTextColor = MaterialTheme.colorScheme.surfaceVariant,
                                focusedPlaceholderColor = Color.Gray
                            )
                        )

                        Spacer(modifier = Modifier.height(height = spaceBetweenElements))

                        DialogButton(
                            buttonText = "Ok") {
                            onConfirmation()
                        }
                        Spacer(modifier = Modifier.height(height = spaceBetweenElements * 2))
                    }

                Box(modifier = Modifier
                    .fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd) {

                    //
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier
                            .background(color = Color.White, shape = CircleShape)
                            .border(
                                width = 2.dp,
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.surface
                            )
                            .clickable { onConfirmation() }
                            .padding(all = 16.dp)
                    )
                }
            }
        }
}

