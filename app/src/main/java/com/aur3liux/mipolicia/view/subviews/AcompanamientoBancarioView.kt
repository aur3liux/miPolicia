package com.aur3liux.mipolicia.view.subviews

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room.databaseBuilder
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.ui.theme.shapePrincipalColor
import com.aur3liux.mipolicia.ui.theme.textShapePrincipalColor
import com.aur3liux.mipolicia.view.dialogs.ErrorDialog
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.localdatabase.AppDb

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AcompanamientoBancarioView(navC: NavController) {
    val context = LocalContext.current
    val db = databaseBuilder(context,
        AppDb::class.java,
        Store.DB.NAME)
        .allowMainThreadQueries()
        .build()

    val locationDb = db.locationDao().getLocationData()
    val user = db.userDao().getUserData()

    val nombreSector = remember { mutableStateOf("") }
    val direccion = remember { mutableStateOf("") }
    val telefono = remember { mutableStateOf("") }

    val showSheetError = remember { mutableStateOf(false) }
    val messageError = remember { mutableStateOf("") }


    val info = buildAnnotatedString {
        append(messageError.value)
    }


    Scaffold(contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier.fillMaxSize(),
        containerColor = shapePrincipalColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Acompañamiento bancario",
                        fontSize = 15.sp,
                        letterSpacing = 0.3.sp,
                        fontFamily = ToolBox.gmxFontRegular,
                        color = textShapePrincipalColor,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = shapePrincipalColor),
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { navC.popBackStack() },
                        imageVector = Icons.Filled.Close,
                        contentDescription = "", tint = textShapePrincipalColor)})
        }) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                text = "Servicio gratuito de acompañamiento bancario, que ofrece la SPSC en apoyo a toda la ciudadanía que acuda a realizar cualquier transacción a sucursales bancarias o cajeros automáticos.",
                fontSize = 12.sp,
                textAlign = TextAlign.Justify,
                fontFamily = ToolBox.quatroSlabFont,
                color = MaterialTheme.colorScheme.primary,
                lineHeight = 17.sp,
                fontWeight = FontWeight.Medium
            )

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)) {

                Text(
                    text = "Para solicitar el acompañamiento es necesario:",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Justify,
                    fontFamily = ToolBox.quatroSlabFont,
                    color = MaterialTheme.colorScheme.primary,
                    lineHeight = 17.sp,
                    fontWeight = FontWeight.Medium
                )

                Row(modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dot),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary)
                    Text(
                        text = "Llamar al 911",
                        fontSize = 14.sp,
                        fontFamily = ToolBox.quatroSlabFont,
                        color = MaterialTheme.colorScheme.primary,
                        lineHeight = 15.sp,
                        textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.Normal
                    )
                }

                Row(modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dot), contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                    Text(
                        text = "Especificar al operador horario de transacción, punto de partida y destino.",
                        fontSize = 14.sp,
                        fontFamily = ToolBox.quatroSlabFont,
                        color = MaterialTheme.colorScheme.primary,
                        lineHeight = 15.sp,
                        textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.Normal
                    )
                }

                Row(modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dot), contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                    Text(
                        text = "Nombre y datos generales de cómo identificarlo",
                        fontSize = 14.sp,
                        fontFamily = ToolBox.quatroSlabFont,
                        color = MaterialTheme.colorScheme.primary,
                        lineHeight = 15.sp,
                        textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            HorizontalDivider()

            Text(
                modifier = Modifier.fillMaxWidth(0.9f),
                text = "Puede hacer la solicitud ahora mismo",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                fontFamily = ToolBox.quatroSlabFont,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            //BOTONES
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
               contentAlignment = Alignment.Center) {

                //Llamada
                Button(
                    modifier = Modifier
                        .width(120.dp)
                        .height(40.dp),
                    onClick = {
                        val u = Uri.parse("tel:${telefono.value}")
                        val i = Intent(Intent.ACTION_DIAL, u)
                        try {
                            context.startActivity(i)
                        } catch (s: SecurityException) {
                            Toast
                                .makeText(
                                    context,
                                    "No se pudo realizar la llamada",
                                    Toast.LENGTH_LONG
                                )
                                .show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 10.dp,
                        pressedElevation = 0.dp,
                        disabledElevation = 0.dp
                    ),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Icon(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(horizontal = 10.dp),
                        tint = Color(0xFF03A9F4),
                        imageVector = Icons.Filled.Call, contentDescription = ""
                    )
                }//Button
            } //Row

            Spacer(modifier = Modifier.height(40.dp))
        } //Column
    }//Scaffold


    if (showSheetError.value) {
        ErrorDialog(
            title = "Error en la consulta",
            info = info,
            context = context, onConfirmation = {
                showSheetError.value = false
            }
        )
    }

}