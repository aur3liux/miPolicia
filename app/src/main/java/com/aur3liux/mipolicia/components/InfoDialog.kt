package com.aur3liux.naats.components


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
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
import com.aur3liux.naats.R
import com.aur3liux.naats.ToolBox
import com.aur3liux.naats.ui.theme.botonColor

@Composable
fun InfoDialog(
    title: String,
    info: AnnotatedString,
    context: Context,
    onConfirmation: () -> Unit,
    spaceBetweenElements: Dp = 18.dp) {

        Dialog(onDismissRequest = {
            onConfirmation()
        }) {
            Surface(
                modifier = Modifier.fillMaxWidth(0.92f),
                color = Color.Transparent // dialog background
            ) {
                ToolBox.soundEffect(context, R.raw.help)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()) {

                    // text and buttons
                    Column(
                        modifier = Modifier
                            .padding(top = 30.dp) // this is the empty space at the top
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFFBCA986),
                                shape = RoundedCornerShape(percent = 10)
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(height = 36.dp))

                        Text(
                            text = title,
                            fontSize = 24.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            fontWeight = FontWeight.Bold,
                            color = botonColor
                        )

                        Spacer(modifier = Modifier.height(height = spaceBetweenElements))

                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            textAlign = TextAlign.Justify,
                            text = info,
                            fontFamily = ToolBox.quatroSlabFont,
                            fontWeight = FontWeight.Light,
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            color = Color.Black
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
                        imageVector = Icons.Default.Info,
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier
                            .background(color = Color.White, shape = CircleShape)
                            .border(width = 2.dp, shape = CircleShape, color = MaterialTheme.colorScheme.surface)
                            .padding(all = 16.dp)
                            .align(alignment = Alignment.TopCenter)
                    )
                }
            }
        }

    Box(
        modifier = Modifier
            .clickable {
                onConfirmation()
            }
            .padding(horizontal = 16.dp, vertical = 6.dp)) {
        Icon(
            imageVector = Icons.Filled.Info,
            contentDescription = "",
            tint = Color.Black,
            modifier = Modifier
                .size(30.dp)
                .background(color = Color.White, shape = CircleShape)
                .border(width = 2.dp, shape = CircleShape, color = MaterialTheme.colorScheme.surface))
    }
}


@Composable
fun DialogButton(
    cornerRadiusPercent: Int = 26,
    buttonText: String,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier.width(200.dp)
            .background(
                color = botonColor,
                shape = RoundedCornerShape(percent = cornerRadiusPercent)
            )
            .clickable {
                onDismiss()
            }
            .padding(horizontal = 16.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buttonText,
            color = Color.White,
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}