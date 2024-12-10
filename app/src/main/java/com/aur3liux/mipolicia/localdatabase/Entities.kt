package com.aur3liux.naats.localdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Store.DB.TB_USER)
data class UserData(
    @PrimaryKey @ColumnInfo(name = Store.User.CURP) val curp: String,
    @ColumnInfo(name = Store.User.USER) val userName: String,
    @ColumnInfo(name = Store.User.TOKEN) val tokenAccess: String,
    @ColumnInfo(name = Store.User.NOMBRE) val nombre: String,
    @ColumnInfo(name = Store.User.PATERNO) val paterno: String,
    @ColumnInfo(name = Store.User.MATERNO) val materno: String,
    @ColumnInfo(name = Store.User.SEXO) val sexo: String,
    @ColumnInfo(name = Store.User.CORREO) val correo: String,
    @ColumnInfo(name = Store.User.TELEFONO) val telefono: String,
    @ColumnInfo(name = Store.User.CIUDAD) val ciudad: String,
    @ColumnInfo(name = Store.User.LOCALIDAD) val localidad: String,
    @ColumnInfo(name = Store.User.COLONIA) val colonia: String,
    @ColumnInfo(name = Store.User.CALLE) val calle: String,
    @ColumnInfo(name = Store.User.EDO_CIVIL) val edoCivil: String,
    @ColumnInfo(name = Store.User.OCUPACION) val ocupacion: String,
    @ColumnInfo(name = Store.User.F_NACIMIENTO) val fNacimiento: String)

@Entity(tableName = Store.DB.TB_SESION)
data class SesionData(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = Store.SESION.STATUS) val status: Int
)

@Entity(tableName = Store.DB.TB_TOKEN_PUSH)
data class TokenPushData(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = Store.TOKEN_PUSH_NOTIFICATION.TOKEN) val tokenPush: String
)

@Entity(tableName = Store.DB.TB_LOCATIONS)
data class LocationData(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = Store.Locations.LATITUD) val latitud: Double,
    @ColumnInfo(name = Store.Locations.LONGITUD) val longitud: Double
)

@Entity(tableName = Store.DB.TB_AVISOS)
data class AvisosData(
    @PrimaryKey @ColumnInfo(name = Store.AVISOS.ref) val ref: Int,
    @ColumnInfo(name = Store.AVISOS.folioAviso) val folio: Int,
    @ColumnInfo(name = Store.AVISOS.descripcion) val descripcion: String,
    @ColumnInfo(name = Store.AVISOS.tipo) val tipo: String,
    @ColumnInfo(name = Store.AVISOS.fecha) val fecha: String
)

@Entity(Store.DB.TB_MYPREDENUNCIAS)
data class MyPredenunciaData(
    @PrimaryKey @ColumnInfo(name = Store.PREDENUNCIA.folio) val folio: String,
    @ColumnInfo(name = Store.PREDENUNCIA.estatus) val estatus: String,
    @ColumnInfo(name = Store.PREDENUNCIA.delito) val delito: String,
    @ColumnInfo(name = Store.PREDENUNCIA.subDelito) val subDelito: String,
    @ColumnInfo(name = Store.PREDENUNCIA.modulo) val modulo: String,
    @ColumnInfo(name = Store.PREDENUNCIA.ciudad) val ciudad: String,
    @ColumnInfo(name = Store.PREDENUNCIA.descripcion) val descripcion: String,
    @ColumnInfo(name = Store.PREDENUNCIA.fechaCreacion) val fecha: String,
    @ColumnInfo(name = Store.PREDENUNCIA.horaCreacion) val hora: String,
    @ColumnInfo(name = Store.PREDENUNCIA.fechaLastModif) val fechaModif: String,
@ColumnInfo(name = Store.PREDENUNCIA.horaLastModif) val horaModif: String
)

@Entity(Store.DB.TB_PREDENUNCIA_TMP)
data class PredenunciaTmpData(
    @PrimaryKey @ColumnInfo(name = Store.PREDENUNCIA_TMP.indice) val id: Int,
    @ColumnInfo(name = Store.PREDENUNCIA_TMP.indexDelito) val indexDelito: Int,
    @ColumnInfo(name = Store.PREDENUNCIA_TMP.delito) val delito: String,
    @ColumnInfo(name = Store.PREDENUNCIA_TMP.subDelito) val subDelito: String,
    @ColumnInfo(name = Store.PREDENUNCIA_TMP.indexSubdelito) val indexSubdelito: Int,
    @ColumnInfo(name = Store.PREDENUNCIA_TMP.descripcion) val descripcion: String,
    @ColumnInfo(name = Store.PREDENUNCIA_TMP.latitudPred) val latitud: Double,
    @ColumnInfo(name = Store.PREDENUNCIA_TMP.longitudPred) val longitud: Double
)
