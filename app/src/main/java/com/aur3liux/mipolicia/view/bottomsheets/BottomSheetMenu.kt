package com.aur3liux.mipolicia.view.bottomsheets

import android.Manifest
import android.content.pm.PackageManager
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsCarFilled
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.LiveHelp
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.LocationCity
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.components.MenuCard
import com.aur3liux.mipolicia.components.MenuImg
import com.aur3liux.mipolicia.components.RoundedButton
import com.aur3liux.mipolicia.view.dialogs.AddEvienciaDialog
import com.aur3liux.mipolicia.view.dialogs.ConfirmDialog
import com.aur3liux.mipolicia.view.dialogs.SelectQuejaFelicitacionDialog
import com.aur3liux.mipolicia.view.subviews.createImageFile
import com.aur3liux.mipolicia.view.subviews.getUri
import com.google.maps.android.compose.Circle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetMenu(
    navC:NavController,
    onConfirmQuejaReporte: MutableState<Boolean>,
    onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = Modifier
            .fillMaxHeight(0.7f),
        onDismissRequest = { onDismiss() },
        containerColor = Color.Transparent,
        sheetState = modalBottomSheetState,
        dragHandle = {

        }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            Row(modifier = Modifier
                .padding(horizontal = 20.dp)
                .height(50.dp)
                .weight(0.2f)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier
                        .weight(0.8f)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    text = "Secretaría de Protección y Seguridad Ciudadana",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary)

                Icon(
                    modifier = Modifier
                        .weight(0.2f)
                        .size(30.dp)
                        .clickable { onDismiss() },
                    imageVector = Icons.Filled.Close,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary)

            } //Row

           // Spacer(modifier = Modifier.height(10.dp))
            //Primera fila de opciones
            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                //PRIMERA FILA
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    MenuCard(
                        menuOpc = MenuImg(
                            Icons.Filled.DirectionsCarFilled,
                            "Conduce sin alcohol"
                        ),
                        shape = CircleShape,
                        modifier = Modifier
                            .weight(0.33f)
                            .padding(8.dp),
                        colorBack = MaterialTheme.colorScheme.primary,
                        fSize = 12.sp,
                        w = 60.dp,
                        h = 60.dp,
                        colorTx = MaterialTheme.colorScheme.primary,
                        colorTint = MaterialTheme.colorScheme.background
                    ) {
                        onDismiss()

                    }

                    MenuCard(
                        menuOpc = MenuImg(
                            Icons.Filled.LocalPolice,
                            "Acompañamiento bancario"
                        ),
                        shape = CircleShape,
                        modifier = Modifier
                            .weight(0.33f)
                            .padding(8.dp),
                        colorBack = MaterialTheme.colorScheme.primary,
                        fSize = 12.sp,
                        w = 60.dp,
                        h = 60.dp,
                        colorTx = MaterialTheme.colorScheme.primary,
                        colorTint = MaterialTheme.colorScheme.background
                    ) {
                        onDismiss()
                        navC.navigate(Router.ACOMPANAMIENTO_BANCARIO_VIEW.route)
                    }
                } //Primera fila

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(modifier = Modifier.fillMaxWidth(0.8f))
                Spacer(modifier = Modifier.height(10.dp))

                //SEGUNDA FILA
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    MenuCard(
                        menuOpc = MenuImg(
                            Icons.Filled.LocationCity,
                            "Reporte ciudadano"
                        ),
                        shape = CircleShape,
                        modifier = Modifier
                            .weight(0.33f)
                            .padding(8.dp),
                        colorBack = MaterialTheme.colorScheme.primary,
                        fSize = 12.sp,
                        w = 60.dp,
                        h = 60.dp,
                        colorTx = MaterialTheme.colorScheme.primary,
                        colorTint = MaterialTheme.colorScheme.background
                    ) {
                        onDismiss()
                        navC.navigate(Router.REPORTE_CIUDADANO.route)
                    }

                    MenuCard(
                        menuOpc = MenuImg(
                            Icons.Filled.EmojiPeople,
                            "Quejas y felicitaciones"
                        ),
                        shape = CircleShape,
                        modifier = Modifier
                            .weight(0.33f)
                            .padding(8.dp),
                        colorBack = MaterialTheme.colorScheme.primary,
                        fSize = 12.sp,
                        w = 60.dp,
                        h = 60.dp,
                        colorTx = MaterialTheme.colorScheme.primary,
                        colorTint = MaterialTheme.colorScheme.background
                    ) {
                        onConfirmQuejaReporte.value = true
                        onDismiss()
                    }
                } //Segunda fila

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(modifier = Modifier.fillMaxWidth(0.8f))
                Spacer(modifier = Modifier.height(10.dp))

                //TERCERA FILA
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MenuCard(
                        menuOpc = MenuImg(
                            Icons.Filled.Gavel,
                            "Reglamento y Ley de vialidad"
                        ),
                        shape = CircleShape,
                        modifier = Modifier
                            .weight(0.33f)
                            .padding(8.dp),
                        colorBack = MaterialTheme.colorScheme.primary,
                        fSize = 12.sp,
                        w = 60.dp,
                        h = 60.dp,
                        colorTx = MaterialTheme.colorScheme.primary,
                        colorTint = MaterialTheme.colorScheme.background
                    ) {
                        onDismiss()
                        navC.navigate(Router.MARCOLEGAL_VIEW.route)
                    }


                    MenuCard(
                        menuOpc = MenuImg(
                            Icons.Filled.Person,
                            "Perfil del usuario"
                        ),
                        shape = CircleShape,
                        modifier = Modifier
                            .weight(0.33f)
                            .padding(8.dp),
                        colorBack = MaterialTheme.colorScheme.primary,
                        fSize = 12.sp,
                        w = 60.dp,
                        h = 60.dp,
                        colorTx = MaterialTheme.colorScheme.primary,
                        colorTint = MaterialTheme.colorScheme.background
                    ) {
                        onDismiss()
                        navC.navigate(Router.PERFIL_VIEW.route)
                    }
                }//Row tercera fila
            }
           // Spacer(modifier = Modifier.height(40.dp))
        }
    }
}