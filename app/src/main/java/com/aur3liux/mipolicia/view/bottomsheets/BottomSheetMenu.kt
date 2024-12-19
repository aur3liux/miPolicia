package com.aur3liux.mipolicia.view.bottomsheets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.ShareLocation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.components.MenuCard
import com.aur3liux.mipolicia.components.MenuImg
import com.aur3liux.mipolicia.components.RoundedButton
import com.google.maps.android.compose.Circle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetMenu(
    navC:NavController,
    onCloseSesion: () -> Unit,
    onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = Modifier
            .fillMaxHeight(0.5f),
        onDismissRequest = { onDismiss() },
        containerColor = Color.Transparent,
        sheetState = modalBottomSheetState,
        dragHandle = {

        }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
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
                        Icons.Filled.Report,
                        "Reporte ciudadano"
                    ),
                    shape = CircleShape,
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(8.dp),
                    colorBack = MaterialTheme.colorScheme.inverseSurface,
                    fSize = 12.sp,
                    w = 60.dp,
                    h = 60.dp,
                    colorTx = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    onDismiss()
                    navC.navigate(Router.REPORTE_CIUDADANO.route)
                }

                MenuCard(
                    menuOpc = MenuImg(
                        Icons.Filled.Policy,
                        "Policía cibernética"
                    ),
                    shape = CircleShape,
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(8.dp),
                    colorBack = MaterialTheme.colorScheme.inverseSurface,
                    fSize = 12.sp,
                    w = 60.dp,
                    h = 60.dp,
                    colorTx = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    onDismiss()
                    navC.navigate(Router.POLICIA_CIBERNETICA.route)
                }

                MenuCard(
                    menuOpc = MenuImg(
                        Icons.Filled.ShareLocation,
                        "Consulta tu sector"
                    ),
                    shape = CircleShape,
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(8.dp),
                    colorBack = MaterialTheme.colorScheme.inverseSurface,
                    fSize = 12.sp,
                    w = 60.dp,
                    h = 60.dp,
                    colorTx = MaterialTheme.colorScheme.surfaceVariant
                ) {

                }
            }//Row primera fila

            Spacer(modifier = Modifier.height(20.dp))

            //Segunda fila de opciones
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                MenuCard(
                    menuOpc = MenuImg(
                        Icons.Filled.EmojiPeople,
                        "Quejas y felicitaciones"
                    ),
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(8.dp),
                    colorBack = MaterialTheme.colorScheme.inverseSurface,
                    fSize = 12.sp,
                    w = 60.dp,
                    h = 60.dp,
                    colorTx = MaterialTheme.colorScheme.surfaceVariant
                ) {

                }

                MenuCard(
                    menuOpc = MenuImg(
                        Icons.Filled.Person,
                        "Datos del usuario"
                    ),
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(8.dp),
                    colorBack = MaterialTheme.colorScheme.inverseSurface,
                    fSize = 12.sp,
                    w = 60.dp,
                    h = 60.dp,
                    colorTx = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    onDismiss()
                    navC.navigate(Router.PERFIL_VIEW.route)
                }
            }//Row segunda fila

            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}