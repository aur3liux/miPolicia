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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.components.DialogButton

@Composable
fun ArticleDialog(
    title: String,
    info: AnnotatedString,
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
                Box() {

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
                        //N
                        Text(
                            text = title,
                            fontSize = 19.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(height = spaceBetweenElements))
                        //Articulo completo
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            textAlign = TextAlign.Justify,
                            text = info,
                            fontFamily = ToolBox.quatroSlabFont,
                            fontWeight = FontWeight.Light,
                            fontSize = 14.sp,
                            lineHeight = 18.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(height = spaceBetweenElements))

                        DialogButton(
                            buttonText = "Ok") {
                            onConfirmation()
                        }

                        Spacer(modifier = Modifier.height(height = spaceBetweenElements * 2))
                    }

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
                            .align(alignment = Alignment.TopCenter)
                    )
                }
            }
        }
}

