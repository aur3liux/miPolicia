package com.aur3liux.mipolicia.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.ui.theme.cronosColor

@Composable
fun RoundedButton(modifier: Modifier = Modifier,
                  text: String,
                  fSize: TextUnit = 17.sp,
                  estatus: MutableState<Boolean> = mutableStateOf(false),
                  backColor: Color = MaterialTheme.colorScheme.surface,
                  textColor: Color = Color(0xFFF5AC3E),
                  shape: Shape,
                  onClick: () -> Unit){

    if(estatus.value){
        CircularProgressIndicator(
            modifier = Modifier
                .size(60.dp)
                .background(Color.Transparent),
            color = cronosColor,
            strokeWidth = 4.dp
        )
    }else{
        Button(
            modifier = modifier,
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = backColor),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            shape = shape,
        ) {
            Text(
                text = text,
                fontStyle = FontStyle.Italic,
                fontSize = fSize,
                fontFamily = ToolBox.montseFont,
                fontWeight = FontWeight.Normal,
                color = textColor
            )
        }//Button
    }
}