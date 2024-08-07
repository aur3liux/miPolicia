package com.aur3liux.naats


sealed class Router(val route: String) {
    object HOME: Router("Home")
    object LOAD_VIEW: Router("LoadView")
    object LOGIN: Router("LoginView")
    object ACCESO_REGISTRO: Router("AccesoRegistroView")
    object CLOSE_SESSION: Router("CloseSessionConfirm")
    object PANEL_SEARCH: Router("PanelSearch")
    object SHOW_RESULT: Router("ShowResults")
    object BUSCA_PERSONA: Router("BuscaPersona")
    object BUSCA_VEHICULO: Router("BuscaVehicuki")
    object BUSCA_EVENTO: Router("BuscaEvento")
    object AVISO_PRIVACIDAD: Router("AvisoPrivacidad")
}