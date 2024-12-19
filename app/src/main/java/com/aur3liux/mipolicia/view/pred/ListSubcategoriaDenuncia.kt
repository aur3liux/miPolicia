package com.aur3liux.mipolicia.view.pred

/****
 * ESTA PANTALLA ES LA SEGUNDA PANTALLA, NO SIEMPRE APARECERÁ
 * YA QUE DEPENDE SI EL DELITO SELECCIONADO PREVIAMENTE
 * ENCAJA EN LOS QUE TIENEN SUBCATEGORIAS.
 ****/

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.components.InfoDialog
import com.aur3liux.mipolicia.components.RoundedButton
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.localdatabase.PredenunciaTmpData
import com.aur3liux.mipolicia.ui.theme.botonColor
import com.aur3liux.mipolicia.ui.theme.lGradient1

data class SubDelitoInfo(val id:Int, val subdelito: String)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListSubcategoriaDenuncia(navc: NavController, indexSubcategoria: Int) {

    val showDialogHelp = remember { mutableStateOf(false) }
    var mutableList: MutableList<SubDelitoInfo> = mutableListOf()
    val onProccesing = remember { mutableStateOf(false) }

    val context = LocalContext.current

    val db = Room.databaseBuilder(context, AppDb::class.java, Store.DB.NAME)
        .allowMainThreadQueries()
        .build()

    val currentPredTmp = db.predenunciaTmpDao().getPredenunciaData()

    val msgQueEsEsto = buildAnnotatedString {
        append("Una subcategoría ayudaría a organizar de mejor manera su predenuncia, sin embargo recuerde que posteriormente un ministerio público le ayudará a revisar su información para integrar la carpeta de investigación de manera correcta.")
    }

    when (indexSubcategoria) {
        8 -> { //d prop ajena
            mutableList.add(SubDelitoInfo(1,"Doloso"))
            mutableList.add(SubDelitoInfo(2,"Culposos"))
        }
        17 -> { //lesiones
            mutableList.add(SubDelitoInfo(3,"Dolosas"))
            mutableList.add(SubDelitoInfo(4,"Culposas"))
        }
        18 -> {//robo
            mutableList.add(SubDelitoInfo(5,"A casa habitación"))
            mutableList.add(SubDelitoInfo(6,"A comercio"))
            mutableList.add(SubDelitoInfo(7,"A institución educativa"))
            mutableList.add(SubDelitoInfo(8,"A traunseunte"))
            mutableList.add(SubDelitoInfo(9,"Partes de vehículo"))
            mutableList.add(SubDelitoInfo(10,"Vehículo"))
        }
        19 -> {//violacion
            mutableList.add(SubDelitoInfo(11,"Simple"))
            mutableList.add(SubDelitoInfo(1,"Equiparada"))
        }
    }//when

    Scaffold(contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            TopAppBar(title = {
                Text(text = "Subcategoría",
                    fontSize = 15.sp,
                    fontFamily = ToolBox.montseFont,
                    fontWeight = FontWeight.Normal,
                    color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = lGradient1),
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .clickable { navc.popBackStack() }
                            .size(20.dp),
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "", tint = Color.White)
                },
                actions = {
                    Card(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable { showDialogHelp.value = true },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation =  10.dp,
                        )){
                        Box(modifier = Modifier
                            .size(40.dp),
                            contentAlignment = Alignment.Center){
                            Icon(
                                imageVector = Icons.Filled.QuestionMark,
                                contentDescription = "")
                        }//Box
                    }//card
                })
        }) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                text = "Puede escoger una subcategoría, si no está seguro puede omitir este paso y continuar con el envío.",
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Justify,
                fontFamily = ToolBox.quatroSlabFont,
                lineHeight = 20.sp,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(20.dp))

            Surface(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(0.95f),
                shape = RoundedCornerShape(20.dp)
            ) {
                LazyColumn(modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(top = 20.dp)
                    .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
                    .fillMaxWidth(),
                    contentPadding = innerPadding,
                    verticalArrangement = Arrangement.spacedBy(10.dp)){

                    itemsIndexed(mutableList){ pos, subcategoria ->

                        Row(modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                            .clickable {
                                //ACTUALIZAMOS LOS DATOS DE LA PREDENUNCIA HASTA EL PRIMER PASO
                                db
                                    .predenunciaTmpDao()
                                    .updatePredenunciaTmp(
                                        PredenunciaTmpData(
                                            0,
                                            currentPredTmp.indexDelito,
                                            currentPredTmp.delito,
                                            subcategoria.subdelito,
                                            subcategoria.id,
                                            currentPredTmp.descripcion,
                                            currentPredTmp.latitud,
                                            currentPredTmp.longitud
                                        )
                                    )
                                navc.navigate(Router.PREDENUNCIA.route)
                            },
                            verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                modifier = Modifier
                                    .weight(0.9f)
                                    .padding(start = 20.dp),
                                fontSize = 17.sp,
                                text = subcategoria.subdelito,
                                fontFamily = ToolBox.quatroSlabFont,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 15.sp,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Icon(
                                modifier = Modifier
                                    .size(20.dp)
                                    .weight(0.1f)
                                    .padding(end = 10.dp),
                                imageVector = Icons.Filled.ArrowForwardIos,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary)
                        } //Row
                        if(pos < mutableList.size - 1)
                            HorizontalDivider(modifier = Modifier.height(1.dp), color = Color.Black)
                    }//itemIndexed
                }//LazyColumn
            } //surface
            Spacer(modifier = Modifier.height(10.dp))
            RoundedButton(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 10.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                text = "Omitir",
                fSize = 15.sp,
                shape =   RoundedCornerShape(15.dp),
                textColor = Color.White,
                backColor = botonColor,
                estatus = onProccesing,
                onClick = {
                    navc.navigate(Router.PREDENUNCIA.route)
                } //onClick
            )

            Spacer(modifier = Modifier.height(20.dp))
        }//Column
    }//scaffold

    if(showDialogHelp.value){
        InfoDialog(
            title = "Subcategoría",
            info = msgQueEsEsto,
            context = context,
            onConfirmation = {
                showDialogHelp.value = false
            })
    }
}