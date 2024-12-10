package com.aur3liux.mipolicia.view.bottomviews

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.components.InfoDialog
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.ui.theme.lGradient2

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HelpView(navC: NavController) {
    val context = LocalContext.current
    val db = Room.databaseBuilder(
        context,
        AppDb::class.java,
        Store.DB.NAME
    )
        .allowMainThreadQueries()
        .build()

    val showDialogHelp = remember { mutableStateOf(false) }
    val title = remember { mutableStateOf("") }
    val msgHelp = remember { mutableStateOf("") }


    val info = buildAnnotatedString {
        append(msgHelp.value)
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

        Column {
            Image(
                modifier = Modifier
                    .background(lGradient2)
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                painter = painterResource(id = R.drawable.gobierno_todos_banner),
                contentDescription = "",
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween){
                Text(
                    modifier = Modifier
                        .height(50.dp)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    text = "Preguntas frecuentes",
                    fontSize = 17.sp,
                    fontFamily = ToolBox.quatroSlabFont,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    modifier = Modifier.size(60.dp),
                    painter = painterResource(id = R.drawable.logo_mp),
                    contentDescription = "",
                    contentScale = ContentScale.Fit
                )
            }


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.background),
                shape = RoundedCornerShape(10.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                val msgQueHago = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary, fontWeight = FontWeight.Bold)) {
                        append("¿Cómo enviar una predenuncia?\n")
                    }
                    append("En el mapa marque el lugar donde sucedió el evento para obtener la ubicación aproximada.\n" +
                            "Para enviar una predenuncia presione el botón ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary, fontWeight = FontWeight.Bold)) {
                        append("Predenuncia")
                    }
                    append(" y siga los pasos que la aplicación le vaya indicando.")
                }
                Text(modifier = Modifier.padding(horizontal = 20.dp),
                    text = msgQueHago,
                    textAlign = TextAlign.Justify,
                    fontFamily = ToolBox.quatroSlabFont,
                    color = MaterialTheme.colorScheme.primary,
                    lineHeight = 20.sp,
                    fontSize = 14.sp)

                Spacer(modifier = Modifier.height(10.dp))

                //QUE ES ESTO
                Row(modifier = Modifier
                    .clickable {
                        showDialogHelp.value = true
                        title.value = "Predenuncia"
                        msgHelp.value =
                            "Es la forma por medio de la cual el usuario hace del conocimiento al ministerio público de una noticia criminal; una vez concluido el trámite en línea deberá darle seguimiento, ante el ministerio público, mediante los datos que le iremos proporcionando."
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.padding(start = 10.dp),
                        imageVector = Icons.Filled.QuestionMark, contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "¿Que es una predenuncia?",
                        fontSize = 12.sp,
                        fontFamily = ToolBox.quatroSlabFont,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                } //Row nombre completo


                HorizontalDivider()

                //
                Row(modifier = Modifier
                    .clickable {
                        showDialogHelp.value = true
                        title.value = "Los siguientes:"
                        msgHelp.value =
                            "Ubicación exacta del lugar de los hechos el cual ubicará en el mapa y describir los hechos de manera clara."
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.padding(start = 10.dp),
                        imageVector = Icons.Filled.QuestionMark, contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "¿Qué datos necesito para iniciar una Predenuncia?",
                        fontSize = 12.sp,
                        fontFamily = ToolBox.quatroSlabFont,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                } //Row EMAIL

                HorizontalDivider()

                //QUE DEBO HACER
                Row(modifier = Modifier
                    .clickable {
                        showDialogHelp.value = true
                        title.value = "Ratificar"
                        msgHelp.value =
                            "Cuando usted concluya la predenuncia en línea recibe de inmediato un folio con el que deberá comparecer a la brevedad posible con el ministerio público y coadyuvar con la integración de su expediente."
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.padding(start = 10.dp),
                        imageVector = Icons.Filled.QuestionMark, contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "¿Qué debo hacer después de tramitar mi Predenuncia?",
                        fontSize = 12.sp,
                        fontFamily = ToolBox.quatroSlabFont,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                } //Row EMAIL

                Spacer(modifier = Modifier.height(10.dp))
            }//Card

        } //Column
    } //Column


    if(showDialogHelp.value){

        InfoDialog(
            title = title.value,
            info = info,
            context = context,
            onConfirmation = {
                showDialogHelp.value = false
            })
    }

}