package com.aur3liux.naats.view.subviews

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.DirectionsCarFilled
import androidx.compose.material.icons.filled.Handyman
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
                route = Router.PANEL_SEARCH.route
            ),
            BottomNavigationItem(
                label = "Opcion 1",
                icon = Icons.Filled.People,
                route = Router.BUSCA_PERSONA.route
            ),
            BottomNavigationItem(
                label = "Opcion 2",
                icon = Icons.Filled.DirectionsCarFilled,
                route = Router.BUSCA_VEHICULO.route
            ),
            BottomNavigationItem(
                label = "Opcion 3",
                icon = Icons.Filled.DateRange,
                route = Router.BUSCA_EVENTO.route
            ),
        )
    }
}
