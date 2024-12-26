package com.aur3liux.mipolicia.view.cibernetica

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddIcCall
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room.databaseBuilder
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.localdatabase.AppDb

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PoliciaCiberneticaView(navC: NavController) {
    val context = LocalContext.current
    val db = databaseBuilder(context,
        AppDb::class.java,
        Store.DB.NAME)
        .allowMainThreadQueries()
        .build()

    val user = db.userDao().getUserData()
    val showDialogMap = remember { mutableStateOf(false) }
    val localUriHandler = LocalUriHandler.current

    Scaffold(contentWindowInsets = WindowInsets(0.dp),
        backgroundColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(title = { Text(text = "Policía cibernética", fontSize = 15.sp, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface),
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .clickable { navC.popBackStack() }
                            .size(20.dp),
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "", tint = Color.White)
                },
                actions = {
                    Card(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable { /*showDialogHelp.value = true */ },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.inverseSurface
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation =  10.dp,
                        )){
                        Box(modifier = Modifier
                            .size(40.dp),
                            contentAlignment = Alignment.Center){
                            Icon(
                                imageVector = Icons.Filled.AddIcCall,
                                contentDescription = "")
                        }//Box
                    }//card
                })
        }) {

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.4f),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.background),
                    shape = RoundedCornerShape(10.dp)
                ) {

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(10.dp)
                                .width(190.dp)
                                .height(80.dp),
                            painter = painterResource(id = R.drawable.estamos_cuidando),
                            contentDescription = ""
                        )
                    }

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .wrapContentHeight(align = Alignment.CenterVertically),
                        text = "Envíamos tu reporte",
                        fontSize = 17.sp,
                        fontFamily = ToolBox.quatroSlabFont,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    //Video de la policía cibernetica
                    Row(
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.padding(start = 10.dp),
                            imageVector = Icons.Default.Security, contentDescription = ""
                        )

                        ClickableText(
                            modifier = Modifier.padding(10.dp),
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = 12.sp,
                                        fontFamily = ToolBox.quatroSlabFont,
                                        fontWeight = FontWeight.Medium
                                    )
                                ) {
                                    append("Conoce a la policía cibernética")
                                }
                            }
                        ) {
                            localUriHandler.openUri("https://www.youtube.com/watch?app=desktop&v=-iWMrJ1uL10")
                        }
                    } //Row EMAIL

                    HorizontalDivider()

                    //Video CARDING
                    Row(
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.padding(start = 10.dp),
                            imageVector = Icons.Default.CreditCard, contentDescription = ""
                        )

                        ClickableText(
                            modifier = Modifier.padding(10.dp),
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = 12.sp,
                                        fontFamily = ToolBox.quatroSlabFont,
                                        fontWeight = FontWeight.Medium
                                    )
                                ) {
                                    append("¿Sabes lo que es el carding?")
                                }
                            }
                        ) {
                            localUriHandler.openUri("https://www.youtube.com/watch?v=tzDVjTmK7h0")
                        }
                    } //Row EMAIL

                    HorizontalDivider()
                    Row(
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.padding(start = 10.dp),
                            imageVector = Icons.Default.AttachMoney, contentDescription = ""
                        )

                        ClickableText(
                            modifier = Modifier.padding(10.dp),
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = 12.sp,
                                        fontFamily = ToolBox.quatroSlabFont,
                                        fontWeight = FontWeight.Medium
                                    )
                                ) {
                                    append("Aplicaciones de préstamos")
                                }
                            }
                        ) {
                            localUriHandler.openUri("https://www.youtube.com/watch?v=WdP5LVmre7U")
                        }
                    } //Row EMAIL

                    Spacer(modifier = Modifier.height(10.dp))
                }//Card
        } //Column

    }
}