package com.aur3liux.mipolicia.localdatabase

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

data class Art(var articulo: String, var descripcion: AnnotatedString)

class Vial() {

    object peatones {
        val art13 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                append(
                    "Establece el derecho de preferencia de paso sobre el tránsito vehicular para garantizar la integridad física de los peatones."
                )
            }

            withStyle(style = SpanStyle(fontSize = 13.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                append("Especifica las obligaciones al cruzar un paso peatonal, incluyendo la verificación de que los vehículos se detengan.")
            }
        }

        val art15 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                append("Obligaciones de los peatones para transitar de manera segura, como respetar semáforos y cruzar por lugares adecuados.")
            }}

        val art32 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                append("Especifica las obligaciones al cruzar un paso peatonal, incluyendo la verificación de que los vehículos se detengan.")
            }
        }


        var contentArt = listOf<Art>(
            Art("Articulo 13", art13),
            Art("Artículo 15", art15),
            Art("Artículo 32", art32)
        )
    } //peatones

    object licencias {

        val art37 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                append(
                    "Establece las características de las licencias y permisos de conducir, así como las consecuencias de presentar documentos falsos."
                )
            }

            withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                append("ndica que los conductores de transporte público deben recibir capacitación y actualización permanente en cultura vial.")
            }
        }

        val art43 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                append("Detalla el proceso para obtener por primera vez la licencia de conducir, incluyendo la presentación de la solicitud y el pago de derechos.")
            }}

        val art38 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                append("Detalla el proceso para obtener por primera vez la licencia de conducir, incluyendo la presentación de la solicitud y el pago de derechos.")
            }}

        var contentArt = listOf<Art>(
            Art("Artículo 37", art37),
            Art("Artículo 43", art43),
            Art("Artículo 38", art38)
        )
    } //licencia

    object permisos {

    val art44 = buildAnnotatedString {

        withStyle(style = SpanStyle(fontSize = 13.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
            append("Establece que el permiso de conducir para menores tiene una vigencia de un año, con opción a renovación hasta alcanzar la mayoría de edad, y requiere la solicitud de un padre o tutor.")
        }}

    val art45 = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
            append("Establece que el permiso de conducir para menores tiene una vigencia de un año, con opción a renovación hasta alcanzar la mayoría de edad, y requiere la solicitud de un padre o tutor.")
        }}

    val art48 = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
            append("Indica que los permisos de conducir pueden ser cancelados o extinguidos si se incumplen las disposiciones legales o reglamentarias.")
        }}

    var contentArt = listOf<Art>(
        Art("Artículo 44", art44),
        Art("Artículo 45", art45),
        Art("Artículo 48", art48)
    )
}

    object cinturon {
        val art50 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 13.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                append("Establece la obligación para conductores y pasajeros de utilizar cinturones de seguridad, que deben cumplir con requisitos de seguridad y adaptarse a cada vehículo.")
            }
        }

        val art51 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                append("Detalla las responsabilidades de los conductores, incluyendo el uso de sillas porta-infantes para menores y la prohibición de abrir puertas en movimiento, lo que contribuye a la seguridad general en el vehículo.")
            }}


        var contentArt = listOf<Art>(
            Art("Artículo 50", art50),
            Art("Artículo 51", art51)
        )
    }

    object estacionar {
        val art25 = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append(
                    "Prohibe obstruir o utilizar los espacios destinados al estacionamiento de los vehículos de personas con discapacidad, así como los de sus\n" +
                            "rampas de acceso a las banquetas y vías peatonales"
                )
            }
        }
        val art157 = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 14.sp,
                    color = Color.Black,
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
                    color = Color.Black,
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
                    color = Color.Black,
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
                    color = Color.Black,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append(" Prohíbe estacionar en aceras, en doble fila, y en lugares no autorizados, así como en zonas de alta concentración de personas.")
            }
        }

        var contentArt = listOf<Art>(
            Art("Artículo 25", art25),
            Art("Artículo 157", art157),
            Art("Artículo 160", art160),
            Art("Artículo 158", art158)
        )
    }

    object moviles {

        val art30 = buildAnnotatedString {

            withStyle(style = SpanStyle(fontSize = 13.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                append("Prohíbe a los conductores utilizar teléfonos celulares y otros dispositivos que distraigan la atención durante la conducción, a menos que cuenten con dispositivos de manos libres.")
            }
        }

        val art67 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                append("Establece que no se pueden colocar pantallas o televisores en la parte interior delantera del vehículo, para evitar distracciones al conductor.")
            }}


        var contentArt = listOf<Art>(
            Art("Artículo 30", art30),
            Art("Artículo 67", art67),
        )
    }

    object velocidad {

        val art51 = buildAnnotatedString {

            withStyle(
                style = SpanStyle(
                    fontSize = 13.sp,
                    color = Color.Black,
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
                    color = Color.Black,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append("Obliga a los conductores a disminuir la velocidad ante indicaciones de la autoridad de tránsito y en zonas específicas como hospitales y asilos.")
            }
        }

        val art136 = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append("Permite a la autoridad de tránsito instalar dispositivos para controlar la velocidad y sancionar a quienes excedan los límites establecidos.")
            }
        }

        var contentArt = listOf<Art>(
            Art("Artículo 51", art51),
            Art("Artículo 78", art78),
            Art("Artículo 136", art136)
        )
    }

        object cascos {

            val art80 = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 13.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                    append("Establece la obligación de usar casco protector semi-integral para motocicletas tipo scooters de hasta 100 cc y casco integral para otros tipos de motocicletas, tanto para el conductor como para el acompañante.")
                }
            }

            val art81 = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                    append("Indica que los conductores de vehículos de motor deben respetar el derecho de los motociclistas a circular con seguridad, lo que incluye el uso adecuado de cascos.")
                }}


            var contentArt = listOf<Art>(
                Art("Artículo 80", art80),
                Art("Artículo 81", art81)
            )

        }

    object luces {

        val art65 = buildAnnotatedString {

            withStyle(style = SpanStyle(fontSize = 13.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                append("Establece que todo vehículo debe estar provisto de faros delanteros con luz alta y baja, y que la luz baja debe permitir la visibilidad de personas y objetos a una distancia mínima de 30 metros.")
            }
        }

        val art67 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                append("Prohíbe el uso de faros deslumbrantes que pongan en riesgo la seguridad de otros conductores y peatones.")
            }}

        val art68 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                append("Regula que los vehículos deben tener luces de freno visibles y que los vehículos escolares deben contar con luces amarillas y rojas intermitentes.")
            }}

        var contentArt = listOf<Art>(
            Art("Artículo 65", art65),
            Art("Artículo 67", art67),
            Art("Artículo 68", art68)
        )
    }

    object senales {

        val art148 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 13.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                append("Establece que las indicaciones de semáforos, campanas y barras en cruceros de ferrocarril son señales preventivas que deben ser obedecidas por conductores y peatones.")
            }
        }

        val art149 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                append("Clasifica las señales de vialidad en preventivas, restrictivas e informativas, y establece la obligación de respetar las señales restrictivas y preventivas.")
            }}

        val art150 = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Normal)) {
                append("Indica que en la vía pública se utilizarán señales visibles y claras que deben ser obedecidas para garantizar la seguridad vial.")
            }}

        var contentArt = listOf<Art>(
            Art("Artículo 148", art148),
            Art("Artículo 149", art149),
            Art("Artículo 150", art150)
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