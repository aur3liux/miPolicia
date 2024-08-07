package com.aur3liux.naats.view.subviews

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aur3liux.naats.R
import com.aur3liux.naats.Store
import com.aur3liux.naats.ui.theme.botonColor

@Composable
fun NavigationDrawer(
    backColor: Color,
    userName: String,
    showHelp: MutableState<Boolean>,
    confirmCloseSession: MutableState<Boolean>,
    changeState: ()-> Unit) {

    ModalDrawerSheet(modifier = Modifier.width(300.dp),
        drawerContainerColor = backColor
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start) {

            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center ){
                Image(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(10.dp),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "")
            }


            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = Store.APP.name,
                    textAlign = TextAlign.Start,
                    fontSize = 23.sp,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )

                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 10.dp)
                        .clickable {
                            changeState()
                        },
                    imageVector = Icons.Filled.Close,
                    contentDescription = "")
            }


            //Nombre del usuario
            Row(modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {
                Icon(modifier = Modifier.padding(start = 10.dp),
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = Icons.Filled.PersonOutline, contentDescription = ""
                )
                Text(
                    text = userName,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Black
                )
            }//

            Divider(modifier = Modifier.height(2.dp), color = botonColor)

           Box(modifier = Modifier.weight(0.5f),
               contentAlignment = Alignment.BottomCenter) {
               Column {
                   //Ayuda
                   OutlinedButton(
                       modifier = Modifier
                           .padding(20.dp, 0.dp, 20.dp, 10.dp)
                           .fillMaxWidth(),
                       colors = ButtonDefaults.buttonColors(
                           containerColor = MaterialTheme.colorScheme.surface
                       ),
                       onClick = {
                           showHelp.value = true
                           changeState()
                       },
                       content = {
                           Text(text = "Ayuda",
                               textAlign = TextAlign.Start,
                               fontSize = 20.sp,
                               fontStyle = FontStyle.Italic,
                               color = MaterialTheme.colorScheme.primary,
                               fontWeight = FontWeight.Bold)
                       })
                   //Cerrar sesion
                   OutlinedButton(
                       modifier = Modifier
                           .padding(20.dp, 0.dp, 20.dp, 20.dp)
                           .fillMaxWidth(),
                       colors = ButtonDefaults.buttonColors(
                           containerColor = MaterialTheme.colorScheme.inverseSurface
                       ),
                       onClick = {
                           changeState()
                           confirmCloseSession.value = true
                       },
                       content = {
                           Text(text = "Cerrar sesión",
                               textAlign = TextAlign.Start,
                               fontSize = 20.sp,
                               fontStyle = FontStyle.Italic,
                               color = Color.White,
                               fontWeight = FontWeight.Bold)
                       })
               } //column
           }
        } //Columns
    }//ModaldrawerSheet
}//
