package com.aur3liux.mipolicia.view

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.ui.theme.lGradient1

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ReglamentoTransito(navC: NavController) {
    var isLoading = remember { mutableStateOf(false) }
    var currentLoadingPage = remember { mutableStateOf<Int?>(null) }
    var pageCount = remember { mutableStateOf<Int?>(null) }

    Scaffold(contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            TopAppBar(title = { Text(text = "Reglamento de Tránsito", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = lGradient1),
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .clickable { navC.popBackStack() }
                            .size(30.dp),
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "", tint = Color.White)
                })
        }) {
            Box {
                PdfViewer(
                    modifier = Modifier.fillMaxSize(),
                    pdfResId = R.raw.reglamento_transito,
                    loadingListener = { loading, currentPage, maxPage ->
                        isLoading.value = loading
                        if (currentPage != null) currentLoadingPage.value = currentPage
                        if (maxPage != null) pageCount.value = maxPage
                    }
                )
                if (isLoading.value) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        LinearProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 30.dp),
                            progress = if (currentLoadingPage.value == null || pageCount.value == null) 0f
                            else currentLoadingPage.value!!.toFloat() / pageCount.value!!.toFloat()
                        )
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 5.dp)
                                .padding(horizontal = 30.dp),
                            text = "${currentLoadingPage.value ?: "-"} paginas de un total de ${pageCount.value ?: "-"} "
                        )
                    }
                }
            }//Box
    }//Scaffold
}