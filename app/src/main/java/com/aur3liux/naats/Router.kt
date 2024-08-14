package com.aur3liux.naats


sealed class Router(val route: String) {
    object HOME: Router("Home")
    object LOAD_VIEW: Router("LoadView")
    object LOGIN: Router("LoginView")
    object ACCESO_REGISTRO: Router("AccesoRegistroView")
    object CLOSE_SESSION: Router("CloseSessionConfirm")
    object CREAR_DENUNCIA_VIEW: Router("CrearDenunciaView")
    object SHOW_RESULT: Router("ShowResults")
    object EXPEDIENTE_VIEW: Router("ExpedienteView")
    object PERFIL_VIEW: Router("PerfilView")
    object BUSCA_EVENTO: Router("BuscaEvento")
    object AVISO_PRIVACIDAD: Router("AvisoPrivacidad")
}