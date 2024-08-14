package com.aur3liux.naats.view.subviews

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.ui.graphics.vector.ImageVector
import com.aur3liux.naats.Router

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = "") {

    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Inicio",
                icon = Icons.Filled.Home,
                route = Router.CREAR_DENUNCIA_VIEW.route
            ),
            BottomNavigationItem(
                label = "Mi expediente",
                icon = Icons.Filled.Folder,
                route = Router.EXPEDIENTE_VIEW.route
            ),
            BottomNavigationItem(
                label = "Perfil",
                icon = Icons.Filled.People,
                route = Router.PERFIL_VIEW.route
            )
        )
    }
}
