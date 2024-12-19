package com.aur3liux.mipolicia.localdatabase

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

//data class Art(var articulo: String, var descripcion: AnnotatedString, var content: String)
data class Art(var articulo: String, var descripcion: AnnotatedString, var content: String)

class Vial() {

    object peatones {
        val art13 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp,  fontWeight = FontWeight.Normal)) {
                append(
                    "Establece bajo que condiciones el peatón tiene preferencia de paso sobre el tránsito vehicular."
                )
            }
        }

        val art15 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp,  fontWeight = FontWeight.Normal)) {
                append("Obligaciones de los peatones para transitar de manera segura.")
            }}

        val art31 = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append("Regula las obligaciones de los conductores respecto a los pasos peatonales, incluyendo la reducción de velocidad y el alto total.")
            }
        }
        val art32 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal)) {
                append("Obligaciones al cruzar un paso peatonal, incluyendo la verificación de que los vehículos se detengan.")
            }
        }


        var contentArt = listOf<Art>(
            Art("Articulo 13", art13, Articulos.leyVial.art13),
            Art("Artículo 15", art15, Articulos.leyVial.art15),
            Art("Artículo 31", art31, Articulos.leyVial.art31),
            Art("Artículo 32", art32, Articulos.leyVial.art32)
        )
    } //peatones

    object licencias {
        val art35 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal)) {
                append(
                    "Es obligatorio poseer una licencia o permiso vigente expedido por la secretaría correspondiente para poder conducir un vehículo."
                )
            }
        }

        val art36 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp,fontWeight = FontWeight.Normal)) {
                append(
                    "Vigencia y tipos de licencias así como los vehículos autorizados para cada una de ellas."
                )
            }
        }

        val art48 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal)) {
                append("Indica que las licencias pueden ser cancelados si se incumplen las disposiciones legales o reglamentarias.")
            }}

        val art72 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal)) {
                append(
                    "Los conductores no pueden circular sin la licencia, y si ésta se encuentra vencida, suspendida o cancelada, también se consideran las infracciones y/o sanciones."
                )
            }
        }

        var contentArt = listOf<Art>(
            Art("Artículo 35", art35, Articulos.leyVial.art35),
            Art("Artículo 36", art36, Articulos.leyVial.art36),
            Art("Artículo 48", permisos.art48, Articulos.leyVial.art48),
            Art("Artículo 72", art72, Articulos.leyVial.art72)
        )
    } //licencia

    object permisos {

    val art44 = buildAnnotatedString {

        withStyle(style = SpanStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal)) {
            append("Regulariza que los menores de edad pueden obtener permisos especiales para conducir bajo ciertas condiciones,")
        }}

    val art45 = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal)) {
            append("Establece que el permiso de conducir para menores tiene una vigencia de un año, con opción a renovación hasta alcanzar la mayoría de edad, y requiere la solicitud de un padre o tutor.")
        }}

    val art48 = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal)) {
            append("Indica que los permisos de conducir pueden ser cancelados o extinguidos si se incumplen las disposiciones legales o reglamentarias.")
        }}

    var contentArt = listOf<Art>(
        Art("Artículo 44", art44, Articulos.leyVial.art44),
        Art("Artículo 45", art45, Articulos.leyVial.art45),
        Art("Artículo 48", art48, Articulos.leyVial.art48)
    )
}

    object cinturon {
        val art50 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal)) {
                append("Establece la obligación para conductores y pasajeros de utilizar cinturones de seguridad, que deben cumplir con requisitos de seguridad y adaptarse a cada vehículo.")
            }
        }

        val art71V = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal)) {
                append("Uso de sillas porta-infantes para menores.")
            }}


        var contentArt = listOf<Art>(
            Art("Artículo 50", art50, Articulos.leyVial.art50),
            Art("Artículo 71 Fracc. V", art71V, Articulos.leyVial.art71V)
        )
    }

    object estacionar {
        val art25 = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append(
                    "Prohibe obstruir o utilizar los espacios destinados al estacionamiento de los vehículos de personas con discapacidad, así como los de sus\n" +
                    "rampas de acceso a las banquetas y vías peatonales"
                )
            }
        }

        val art73 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal)) {
                append("Regula las condiciones que deben considerarse para estacionarse en la vía pública.")
            }}

        val art157 = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append(
                    "Establece que los vehículos deben estacionarse en el sentido de la circulación, en lugares permitidos por señales de tránsito, y detalla las normas para estacionar en calles de un solo sentido y avenidas."
                )
            }
        }

        val art160 = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append("Indica que en zonas de intenso tránsito, el estacionamiento debe ser en cordón (paralelo a la acera) y dentro de las zonas marcadas.")
            }
        }

        val art162 = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append("Limita el tiempo máximo de estacionamiento en zonas de intenso tránsito entre las 8:00 a.m. y 9:00 p.m., fijado por la autoridad de tránsito.")
            }
        }

        val art158 = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append(" Prohíbe estacionar en aceras, en doble fila, y en lugares no autorizados, así como en zonas de alta concentración de personas.")
            }
        }

        var contentArt = listOf<Art>(
            Art("Artículo 25", art25, Articulos.leyVial.art25),
            Art("Artículo 73", art73, Articulos.leyVial.art73),
            Art("Artículo 157", art157, Articulos.leyVial.art157),
            Art("Artículo 158", art158, Articulos.leyVial.art158),
            Art("Artículo 160", art160, Articulos.leyVial.art160)
        )
    }

    object moviles {

        val art71VII = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal)) {
                append("Dispositivos electrónicos")
            }
        }

        val art71VIII = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal)) {
                append("Teléfonos celulares")
            }
        }

        val art71IX = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal)) {
                append("Televisores o pantallas")
            }
        }

        var contentArt = listOf<Art>(
            Art("Artículo 71 Fracc. VII", art71VII, Articulos.leyVial.art71VII),
            Art("Artículo 71 Fracc. VIII", art71VIII, Articulos.leyVial.art71VIII),
            Art("Artículo 71 Fracc. IX", art71IX, Articulos.leyVial.art71IX),
        )
    }

    object velocidad {
        val art18I = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append("Disminuir velocidad cuando encuentre un transporte escolar.")
            }
        }

        val art18III = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append("Disminuir velocidad en zonas escolares")
            }
        }

        val art23 = buildAnnotatedString {

            withStyle(
                style = SpanStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append("Disminuir la velocidad en zona de hospitales")
            }
        }
        val art51 = buildAnnotatedString {

            withStyle(
                style = SpanStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append("Establece los límites de velocidad en vías primarias (30 km/h sin señalización) y secundarias (20 km/h), así como en zonas escolares y peatonales.")
            }
        }

        val art78 = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append("Obliga a los conductores a disminuir la velocidad ante indicaciones de la autoridad de tránsito y en zonas específicas como hospitales y asilos.")
            }
        }


        var contentArt = listOf<Art>(
            Art("Artículo 18 Fracc. I", art18I, Articulos.leyVial.art18I),
            Art("Artículo 18 Fracc. III", art18III, Articulos.leyVial.art18III),
            Art("Artículo 23", art23, Articulos.leyVial.art23),
            Art("Artículo 51", art51, Articulos.leyVial.art51),
            Art("Artículo 78", art78, Articulos.leyVial.art78)
        )
    }

        object motociclistas {

            val art80A_FI = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal)) {
                    append("Numero de pasajeros permitido en el vehículo")
                }
            }

            val art80A_FIII = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal)) {
                    append("Circular por la extrema derecha y la manera de rebasar")
                }
            }

            val art80A_FIV = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal)) {
                    append("Utilizar un solo carril")
                }
            }

            val art80A_FV = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal)) {
                    append("Conducir de noche con luces encendidas")
                }
            }

            val art80A_FVI = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal)) {
                    append("Uso de cascos")
                }
            }

            val art80B_FII = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal)) {
                    append("No transitar sobre baquetas y áreas de peatones")
                }
            }

            val art80B_FV = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal)) {
                    append("No llevar cargas que dificulten la visibilidad y constituyan un peligro")
                }
            }

            val art81 = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 14.sp,fontWeight = FontWeight.Normal)) {
                    append("Los conductores de vehículos deben respetar el derecho de los motociclistas a usar un carril.")
                }}

            val art82 = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 14.sp,fontWeight = FontWeight.Normal)) {
                    append("Prohibición de transitar en carriles centrales")
                }}

            val art85 = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 14.sp,fontWeight = FontWeight.Normal)) {
                    append("Las bicicletas daptadas serán consideradas dentro de la categoría de motocicletas.")
                }}


            var contentArt = listOf<Art>(
                Art("Artículo 80 A Fracc. I", art80A_FI, Articulos.leyVial.art80A_FI),
                Art("Artículo 80 A Fracc. III", art80A_FIII, Articulos.leyVial.art80A_FIII),
                Art("Artículo 80 A Fracc. IV", art80A_FIV, Articulos.leyVial.art80A_FIV),
                Art("Artículo 80 A Fracc. V", art80A_FV, Articulos.leyVial.art80A_FV),
                Art("Artículo 80 A Fracc. VI", art80A_FVI, Articulos.leyVial.art80A_FVI),
                Art("Artículo 80 B Fracc. II", art80B_FII, Articulos.leyVial.art80B_FII),
                Art("Artículo 80 B Fracc. V", art80B_FV, Articulos.leyVial.art80B_FV),
                Art("Artículo 81", art81, Articulos.leyVial.art81),
                Art("Artículo 82", art82, Articulos.leyVial.art82),
                Art("Artículo 85", art85, Articulos.leyVial.art85)
            )

        }

    object luces {

        val art65_FI = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal)) {
                append("Todo vehículo debe estar provisto de faros delanteros con luz alta y baja, y que la luz baja debe permitir la visibilidad a cierta distancia")
            }
        }

        val art65_FII = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal)) {
                append("Luces indicadoras de frenos en la parte trasera del vehículo.")
            }
        }

        val art65_FVII = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal)) {
                append("Luces que iluminen las placas")
            }
        }

        val art65_FVIII = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal)) {
                append("Luces de reversa")
            }
        }

        val art67 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal)) {
                append("Prohíbe matices, colores y luces bajo ciertas condiciones")
            }}


        var contentArt = listOf<Art>(
            Art("Artículo 65 Franccion I", art65_FI, Articulos.leyVial.art65_FI),
            Art("Artículo 65 Franccion II", art65_FII, Articulos.leyVial.art65_FII),
            Art("Artículo 65 Franccion VII", art65_FVII, Articulos.leyVial.art65_FVII),
            Art("Artículo 65 Franccion VIII", art65_FVIII, Articulos.leyVial.art65_FVIII),
            Art("Artículo 67", art67, Articulos.leyVial.art67)
        )
    }

    object polarizado {

        val art64 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal)) {
                append("Regula el nivel de polarizado en los cristales de los vehículos")
            }
        }

        var contentArt = listOf<Art>(
            Art("Artículo 64", art64, Articulos.leyVial.art64)
        )

    }

        /*    object licencias {

            val art37 = buildAnnotatedString {

                withStyle(style = SpanStyle(fontSize = 13.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                    append("")
                }
            }

            val art43 = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                    append("")
                }}

            val art38 = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                    append(" ")
                }}

            var contentArt = listOf<Art>(
                Art("Artículo 37",),
                Art("Artículo 43", ),
                Art("Artículo 38", )
            )

        }*/
}