package com.aur3liux.naats.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import com.aur3liux.naats.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AvisoPrivacidad() {
    var isLoading = remember { mutableStateOf(false) }
    var currentLoadingPage = remember { mutableStateOf<Int?>(null) }
    var pageCount = remember { mutableStateOf<Int?>(null) }

    Box {
        PdfViewer(
            modifier = Modifier.fillMaxSize(),
            pdfResId = R.raw.aviso_de_privacidad,
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
    }
}