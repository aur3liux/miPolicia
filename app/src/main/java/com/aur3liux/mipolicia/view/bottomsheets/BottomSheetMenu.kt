package com.aur3liux.mipolicia.view.bottomsheets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BluetoothDrive
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.RuleFolder
import androidx.compose.material.icons.filled.ShareLocation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.components.MenuCard
import com.aur3liux.mipolicia.components.MenuImg
import com.aur3liux.mipolicia.ui.theme.botonColor
import com.aur3liux.mipolicia.components.RoundedButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetMenu(
    navC:NavController,
    onCloseSesion: () -> Unit,
    onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(0.5f),
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = {

        }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inverseSurface),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Row(modifier = Modifier
                .padding(horizontal = 20.dp)
                .height(50.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Mi policía",
                    fontSize = 15.sp,
                    letterSpacing = 0.2.sp,
                    fontFamily = ToolBox.gmxFontRegular,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.width(20.dp))

                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { onDismiss() },
                    imageVector = Icons.Filled.Close,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary)

            } //Row

            HorizontalDivider()
            Spacer(modifier = Modifier.height(30.dp))

            //Primera fila de opciones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MenuCard(
                    menuOpc = MenuImg(
                        Icons.Filled.Person,
                        "Mi perfil"
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(8.dp),
                    colorBack = MaterialTheme.colorScheme.inverseSurface,
                    fSize = 14.sp,
                    w = 150.dp,
                    h = 80.dp,
                    colorTx = MaterialTheme.colorScheme.primary
                ) {
                    onDismiss()
                    navC.navigate(Router.PERFIL_VIEW.route)
                }


                MenuCard(
                        menuOpc = MenuImg(
                            Icons.Filled.Policy,
                            "Policía cibernética"
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .padding(8.dp),
                        colorBack = MaterialTheme.colorScheme.inverseSurface,
                        fSize = 14.sp,
                        w = 150.dp,
                        h = 80.dp,
                        colorTx = MaterialTheme.colorScheme.primary
                    ) {
                        onDismiss()
                        navC.navigate(Router.POLICIA_CIBERNETICA.route)
                    }
            }//Row primera fila

            //Segunda fila de opciones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                MenuCard(
                    menuOpc = MenuImg(
                        Icons.Filled.ShareLocation,
                        "Mi sector"
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(8.dp),
                    colorBack = MaterialTheme.colorScheme.inverseSurface,
                    fSize = 14.sp,
                    w = 150.dp,
                    h = 80.dp,
                    colorTx = MaterialTheme.colorScheme.primary
                ) {

                }
                MenuCard(
                    menuOpc = MenuImg(
                        Icons.Filled.EmojiPeople,
                        "Quejas y felicitaciones"
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(8.dp),
                    colorBack = MaterialTheme.colorScheme.inverseSurface,
                    fSize = 14.sp,
                    w = 150.dp,
                    h = 80.dp,
                    colorTx = MaterialTheme.colorScheme.primary
                ) {

                }
            }//Row segunda fila


            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}