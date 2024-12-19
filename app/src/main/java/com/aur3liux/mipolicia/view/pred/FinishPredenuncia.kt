package com.aur3liux.mipolicia.view.pred

/****
 * ESTA PANTALLA ES LA ULTIMA AL ENVIAR LA PREDENUNCIA, SU FUNCION ES
 * AVISARLE AL USUARIO QUE SU PREDENUNCIA HA SIDO ENVIADA Y QUE UN
 * MP SE PONDRÁ EN CONTACTO PARA DARLE MAS INDICACIONES
 ****/

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.components.RoundedButton
import com.aur3liux.mipolicia.ui.theme.botonColor
import com.aur3liux.mipolicia.ui.theme.lGradient2

@Composable
fun FinishPredenuncia(navc: NavController, folio: String) {
    val context = LocalContext.current

    val onProccesing = remember{ mutableStateOf(false) }

    val msgAyuda = buildAnnotatedString {
        append("Puede darle seguimiento a sus casos en la sección ")
        withStyle(style = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)) {
            append("Expediente")
        }
        append(" del menú en la pantalla principal.")
    }

    Column( modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

        Image(
            modifier = Modifier
                .background(lGradient2)
                .fillMaxWidth()
                .padding(top = 10.dp),
            painter = painterResource(id = R.drawable.gobierno_todos_banner),
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )

        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(40.dp))
            //
            Text(
                modifier = Modifier
                    .height(50.dp)
                    .padding(start = 20.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                text = "Tu predenuncia ha sido enviada",
                fontSize = 17.sp,
                fontFamily = ToolBox.quatroSlabFont,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                shape = RoundedCornerShape(10.dp)) {
               // Column(horizontalAlignment = Alignment.CenterHorizontally) {


                    Spacer(modifier = Modifier.height(20.dp))
                    //
                    Text(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                        text = "Hemos recibido su predenuncia, Con su número de folio debe presentarse a ratificar su denuncia lo más pronto posible.",
                        fontSize = 13.sp,
                        textAlign = TextAlign.Justify,
                        lineHeight = 20.sp,
                        fontFamily = ToolBox.quatroSlabFont,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Normal
                    )


                    //FOLIO
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "Folio de predenuncia:",
                            fontSize = 15.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = folio,
                            fontSize = 15.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Normal
                        )
                    } //Folio

                    HorizontalDivider()

                    //Lugar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "Lugar de atención:",
                            fontSize = 15.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "Campeche",
                            fontSize = 15.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Normal
                        )
                    } //Folio

                    Spacer(modifier = Modifier.height(20.dp))
                    //
                    Text(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                        text = msgAyuda,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Justify,
                        lineHeight = 20.sp,
                        fontFamily = ToolBox.quatroSlabFont,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Normal
                    )

                    RoundedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp, vertical = 20.dp)
                            .height(40.dp),
                        text = "Cerrar",
                        fSize = 17.sp,
                        shape =   RoundedCornerShape(15.dp),
                        textColor = Color.White,
                        backColor = botonColor,
                        estatus = onProccesing,
                        onClick = {
                            navc.navigate(Router.HOME.route) {
                                popUpTo(navc.graph.id) { inclusive = true }
                            }
                        } //onClick
                    )
              //  }//Column
            }//Card
        } //Column
    } //Column
}
