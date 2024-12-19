package com.aur3liux.mipolicia.localdatabase

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

class Articulos() {

    object leyVial {
        val art13 =
            "Los peatones tienen derecho de preferencia de paso sobre el tránsito " +
            "vehicular, para garantizar su integridad física cuando:\n" +

                    "\nI. En los pasos peatonales marcados con rayas diagonales o marimbas, la señal " +
                    "del semáforo así lo indique" +

                    "\nII. Los vehículos vayan a dar vuelta para entrar a otra vía y haya peatones " +
                    "cruzando ésta" +

                    "\nIII. Los vehículos deban circular sobre el acotamiento y en éste haya peatones " +
                    "transitando que no dispongan de zona peatonal" +

                    "\nIV. Los vehículos transiten frente a tropas en formación, comitivas organizadas o " +
                    " filas escolares" +

                    "\nV. El peatón transite por la banqueta y algún conductor deba cruzarla para entrar " +
                    " o salir de una cochera, estacionamiento o calle privada" +

                    "\nVI. De acuerdo con el ciclo del semáforo no alcancen a cruzar la vía."


        val art15 =
            "Los peatones están obligados a acatar las previsiones siguientes:\n" +

            "\nI. No transitar por la superficie de rodamiento de la vía pública destinada a la " +
            "circulación de vehículos, salvo para cruzarla cuando el ciclo del semáforo lo " +
            "indique. En caso de no existir semáforo, se cruzará la vía tomando las " +
            "precauciones necesarias y por las esquinas" +

            "\nII. Cruzar la superficie de rodamiento de la vía pública por las esquinas o zonas " +
            "marcadas para tal efecto" +

            "\nIII. Obedecer las indicaciones de los agentes, personal de apoyo vial, promotores " +
            "voluntarios de seguridad vial y las señales de los dispositivos de control al " +
            "transitar por la vía pública." +

            "\nIV. Utilizar los pasos peatonales para cruzar la vía pública dotada para ello." +

            "\nV. No circular diagonalmente en los cruceros." +

            "\nVI. No transitar por la parte delantera ni trasera de los vehículos estacionados" +

            "\nVII. No invadir intempestivamente la superficie de rodamiento." +

            "\nVIII No utilizar la vía pública para ejercer el comercio ambulante u ofrecer servicios, " +
           "ni practicar la mendicidad" +

            "\nIX. Abordar los vehículos de transporte público únicamente en los paraderos " +
            "establecidos para el ascenso y descenso."

    //Articulo 18 I
    val art18I =
                "Disminuir la velocidad de su vehículo y tomar las debidas precauciones cuando " +
                "encuentren un transporte escolar detenido en la vía pública, realizando " +
                "maniobras de ascenso y descenso de escolares;"


        val art18III =
                "Disminuir la velocidad a 20 kilómetros por hora en zonas escolares y extremar " +
                "precauciones, respetando los señalamientos instalados de control de tránsito " +
                "vehicular correspondientes; así como ceder el paso a los escolares y peatones, " +
                "haciendo su alto total"


        val art23 =
                "Los conductores de vehículos están obligados a disminuir la velocidad a la " +
                "mínima autorizada por las autoridades de tránsito que correspondan en zonas e " +
                "inmediaciones de hospitales, asilos o albergues y casas hogar, así como a extremar " +
                "precauciones, respetando los señalamientos correspondientes; y en su caso, a ceder el " +
                "paso a todo peatón, en particular a las personas con discapacidad o de la tercera edad, " +
                "efectuando su alto total."


        val art25 =
                "Queda prohibido obstruir o utilizar los espacios destinados al " +
                "estacionamiento de los vehículos de personas con discapacidad, así como los de sus " +
                "rampas de acceso a las banquetas y vías peatonales."


        val art31 =
            "Los conductores tienen las siguientes obligaciones respecto de los pasos peatonales:\n" +

            "\nI. Deberán disminuir su velocidad al acercarse a un paso peatonal" +

            "\nII. Deberán realizar el alto total al llegar al paso peatonal y cerciorarse de ambos " +
            "lados que no cruce ningún peatón" +

            "\nIII. En caso de que una persona esté cruzando el paso peatonal, deberán en todo " +
            "momento esperar hasta que atraviese completamente el mismo" +

            "\nIV. Al transitar un peatón, no deberán tocar el claxon o acelerar el vehículo." +

            "\nV. Invariablemente deberán de ceder el paso a las personas con discapacidad" +

            "\nVI. El peatón tiene el derecho de paso desde el momento de intentar cruzar o al " +
            "estar cruzando el paso peatonal."


        val art32 =
                "Las personas tienen las siguientes obligaciones al cruzar el paso peatonal:\n" +

                "\nI. Antes de cruzar deberán cerciorarse que los conductores disminuyan su " +
                "velocidad al acercarse al paso peatonal" +

                "\nII. Al cruzarlo deberán cerciorarse que los conductores hayan hecho su alto total" +

                "\nIII. Al transitar el paso peatonal lo deberán de hacer en forma rápida" +

                "\nIV. Al cruzar el paso peatonal, deberán de llegar al otro extremo de la calle y nunca " +
                "fuera del mismo." +

                "\nV. os peatones están obligados a no cruzar el paso peatonal cuando un vehículo " +
                "de emergencia con sirena o torreta activada se acerque al mismo, ya que tienen " +
                "preferencia de paso."


        val art35 =
                "Para conducir vehículos en el Estado se requiere licencia o permiso " +
                "vigente, expedidos por la Secretaría, o en su caso, expedidos por las Entidades Federativas " +
                "o Dependencias Federales, que autorice la conducción específica del vehículo de que se " +
                "trata, independientemente del lugar en que se haya expedido la placa de matrícula del " +
                "vehículo y de conformidad con la clasificación a que se refiere el artículo 33 de la Ley."

        val art36 =
                "Las licencias de conducir expedidas por la Secretaría tendrán vigencia por " +
                        "tres años y serán de los tipos siguientes:\n" +

                "\nI. AUTOMOVILISTA, que autoriza para conducir automóviles y camionetas cuya " +
                        "capacidad sea hasta de dos (2) toneladas de carga o quince (15) pasajeros, " +
                        "siempre que tales vehículos no estén destinados a la prestación de un servicio " +
                        "público de transporte" +

                "\nII. MOTOCICLISTA, que autoriza a conductores de vehículos de propulsión motriz, de " +
                        "dos, tres y hasta cuatro ruedas, sin importar su capacidad, tamaño o cilindrada" +

                "\nIII. CHOFER, que autoriza la conducción de vehículos con capacidad mayor a dos " +
                        "(2) toneladas de carga, destinados a la prestación del servicio de transporte " +
                        "mediante salario y que manejan a las órdenes de un patrón un vehículo de " +
                        "servicio particular" +

                "\nIV. CHOFER DE TRANSPORTE PÚBLICO; que es otorgada por la autoridad de tránsito que " +
                        "corresponda únicamente a personas autorizadas para conducir vehículos del " +
                        "servicio público de transporte en el Estado en cualquiera de sus modalidades, " +
                        "que cuentan con la certificación/tarjetón expedida por el Instituto y están " +
                        "inscritas en el Registro Público de Transporte, en los términos de la Ley y " +
                        "Reglamento de la materia."


        val art44 =
                "Los menores de edad pueden conducir los vehículos automotores que " +
                        "requieran licencia tipo automovilista, mediante permisos especiales de conducir, conforme\n" +
                        "a las condiciones siguientes" +

                "\nI. Sólo serán válidos en un horario comprendido entre las 06:00 y las 22:00 horas" +

                "\nII. Queda prohibido conducir en manifestaciones, caravanas, procesiones, " +
                        "exhibiciones deportivas y demás tipos de concentraciones humanas" +


                "\nIII. De igual forma, está estrictamente prohibido que conduzcan cualquier vehículo " +
                        "de transporte público, mercantil o privado de pasajeros o de carga en cualquiera " +
                        "de sus modalidades" +

                "La vigencia de los permisos especiales de conducir autorizados a menores se extinguirá al\n" +
                "cumplir éstos la mayoría de edad, o al incurrir en cualquier infracción a la ley y su\n" +
                "reglamento"


        val art45 =
                "El permiso de conducir tendrá vigencia de un año, con opción a renovación " +
                "hasta cumplir la mayoría de edad. Para obtener permiso de conducir, se requiere solicitud " +
                "del padre, madre o tutor, con declaración bajo protesta de decir verdad que los datos " +
                "manifestados son correctos, y que asumen la responsabilidad legal del menor, debiéndose " +
                "acompañar de:" +

                "\nI. Comprobante de pago de derechos" +

                "\nII. Identificación oficial del padre, madre o tutor" +

                "\nIII. Acta de nacimiento del menor que acredite la edad cumplida de 16 años y una\n" +
                "credencial de identidad" +

                "\nIV. Escrito bajo protesta de decir verdad del padre, madre o tutor de que el menor\n" +
                "es apto para conducir" +

                "\nV. Acreditación del curso de manejo, impartido por la Secretaría" +

                "\nVI. Acreditación de la Evaluación Médica Integral, que incluye examen " +
                "psicométrico, de consumo o ingesta de alcohol, médico general, visual, y " +
                "auditivo" +

                "\nVII. En el caso de extranjeros, además de acreditar su legal estancia en el país del " +
                "menor y del padre, madre o tutor, mediante la presentación de los documentos " +
                "migratorios expedidos por la autoridad competente, deberán acreditar su " +
                "domicilio/residencia en el territorio del Estado y cumplir con estos requisitos y " +
                "demás disposiciones de la Ley y del presente Reglamento, teniendo como " +
                "vigencia el tiempo en el que le esté autorizada su estancia legal en el país; no " +
                "se otorgará a extranjeros con calidad de turista"

        val art48 =
                "Se extinguirán o cancelarán las licencias o permisos de conducir en los " +
               "términos establecidos por los artículos 36 y 37 de la Ley y en este Reglamento. En caso de " +
               "que el solicitante de licencia de conducir o permiso presente documentos falsos o alterados " +
               "o suplante una identidad, se dará vista al Ministerio Público para que proceda conforme a " +
               "derecho y se cancelará la licencia o permiso correspondiente"

        val art50 =
                "Los conductores y pasajeros de vehículos de todo tipo, están obligados a " +
                "utilizar los cinturones de seguridad, los cuales deben de estar fabricados de fibra trenzada " +
                "u otro que cubra los requerimientos de seguridad los cuales deberán de adaptarse a los " +
                "requisitos de cada unidad, pudiendo variar su longitud de un modelo a otro, así como sus " +
                "enganches y sistemas automáticos de tensionado, de manera que se ajusten al cuerpo en " +
                "caso de accidente."


        val art51 =
                "Los conductores están obligados a respetar los límites de velocidad\n" +
                "establecidos para las vías:\n" +

                "\nI. En las vías primarias circularán a la velocidad que se indique mediante los " +
                "señalamientos respectivos. Cuando la vía pública carezca de señalamiento, la " +
               "velocidad máxima será de 30 kilómetros por hora" +

                "\nII. En las vías secundarias la velocidad máxima será de 20 kilómetros por hora. En " +
                 "zonas escolares, peatonales, de hospitales, de asilos, de albergues y casas " +
                 "hogar, la velocidad máxima será la misma" +

                "\nIII. Queda prohibido utilizar las vías públicas para realizar competencias " +
                "vehiculares de alta velocidad o arrancones."

        val art64 =
           "En materia de polarizado, sólo está permitida la película de protección solar " +
           "filtro grado 1 (35% de luz visible transmitida, 52% de transmisión solar y 38% del total de " +
           "energía rechazada) en los cristales y aletas laterales y en el medallón trasero. En el " +
           "panorámico delantero, sólo podrá estar polarizada su parte superior, en una franja de ancho " +
           "no mayor al de las viseras que el vehículo traiga de fábrica, con filtro grado 1 o filtro grado " +
           "2 (18% de luz visible transmitida, 49% de transmisión solar y 38% del total de la energía " +
           "rechazada). La tonalidad o color de la película sólo podrá ser humo, gris o verde, queda " +
           "prohibida cualquier otra tonalidad o color. Lo anterior no aplica a los vehículos cuyos " +
           "cristales vengan entintados de fábrica.\n" +
           "\nLos vehículos de transporte público y de las escuelas de manejo no podrán tener " +
            "polarizados sus cristales, aletas, medallones ni panorámicos. \n" +
           "\nNo se permitirá la circulación de los vehículos que lleven estrellado o roto el parabrisas."

        val art65_FI =
            "Todo vehículo de motor debe estar provisto de:\n" +
            "I. Faros delanteros conectados a un distribuidor de luz alta y luz baja, colocado " +
            "de tal manera que permita al conductor accionar el dispositivo para disminuir la " +
            "altura e intensidad de la luz. La luz baja deberá permitir la visibilidad de " +
            "personas y objetos a una distancia no menor de treinta metros hacia el frente. " +
            "La luz alta permitirá una fácil visión de personas y objetos a una distancia no " +
            "menor de cien metros hacia el frente. En el tablero del vehículo deberá contarse " +
            "con un indicador automático de cambio de luces. La ubicación de estos deberá " +
            "adecuarse a las normas previstas para el tipo de vehículo"

        val art65_FII =
            "Todo vehículo de motor debe estar provisto de:\n" +
                    "II. Indicadores de frenos en la parte trasera"

        val art65_FVII =
            "Todo vehículo de motor debe estar provisto de:\n" +
                    "VII. Luz que ilumine la placa de circulación posterior"

        val art65_FVIII =
            "Todo vehículo de motor debe estar provisto de:\n" +
                    "VIII. Luz de marcha atrás o reversa"

        val art67 =
            "Se prohíbe utilizar en vehículos particulares:\n" +
            "\nI. Colores en matices o tonalidad, o en combinaciones de colores, iguales a los " +
                    "del transporte público de pasajeros matriculados en el Estado, vehículos de " +
                    "emergencia o patrullas" +

            "\nII.  Faros delanteros de color distinto al blanco o ámbar" +

            "\nIII. Burbujas o torretas" +
            "\nIV. Dispositivos similares a los utilizados por vehículos policiales o de emergencia" +
            "\nV. Faros deslumbrantes que pongan en riesgo la seguridad de conductores o " +
                    "peatones" +
            "\nVI. Anuncios publicitarios no autorizados"

        val art71VII =
                "\nXVII. No utilizar dispositivos electrónicos o mecánicos que distraigan la atención del " +
                        "conductor"


        val art71VIII =
                "\nXVIII. No utilizar teléfonos celulares, ni demás objetos o bienes que imposibiliten la " +
                        "conducción del vehículo a menos que cuenten con dispositivos de manos libres"


        val art71V =
                "\nV. Los menores de cinco años deben de viajar en el asiento trasero, utilizando para " +
                "tal efecto sillas especiales porta-infantes, las cuales deberán sujetarse a dicho " +
                "asiento en la forma que indique el correspondiente instructivo del fabricante"


        val art71IX =
                "\nIX. No se podrá colocar televisores o pantallas para reproducir imágenes en la parte " +
                 "interior delantera del vehículo"


        val art72 =
                "Los conductores de vehículos no podrán:\n" +

                "\nI. Circular sin licencia o permiso; o sí estos se encuentran vencidos, o han sido " +
                "suspendidos o cancelados; o cuando aquellos documentos no lo autoricen a " +
                "manejar ese vehículo" +

                "\nII. Circular sin placas vigentes; o estando vigentes no porten una o ambas placas " +
                "de circulación; la placa no coincida con la tarjeta de circulación, ni con la base " +
                "de datos del registro de tránsito" +

                "\nIII. Circular ocultando las placas de vehículos o bien portándolas alteradas o " +
               "modificadas" +

                "\nIV. Consumir bebidas alcohólicas en el interior del vehículo estando en circulación, " +
                "estacionado en la vía pública o en estacionamientos públicos" +

                "\nV. Circular en estado de ebriedad o bajo el influjo de estupefacientes."

        val art73 =
                "Para detenerse o estacionarse en la vía pública se deben observar las " +
                "siguientes reglas:\n" +

                "\nI. En zonas urbanas, las ruedas contiguas a la acera quedarán a una distancia\n" +
                "máxima de la misma que no exceda de 20 centímetros" +

                "\nII. Los conductores que por caso fortuito o de fuerza mayor detengan sus " +
                "vehículos en la superficie de rodamiento de una carretera local o en una vía de " +
                "circulación continua, procurarán ocupar el mínimo de dicha superficie y dejarán " +
                "una distancia de visibilidad suficiente en ambos sentidos, debiendo de " +
                "inmediato colocar los dispositivos de advertencia reglamentarios" +

                "\nIII. Si la vía es de dos sentidos de circulación deberá colocar sus dispositivos a 100 " +
                "metros hacia adelante de la orilla exterior del otro carril. En zona urbana deben " +
                "colocar su dispositivo 20 metros atrás del vehículo inhabilitado" +

                "\nIV. Los autobuses de transporte público de pasajeros, no podrán estacionarse en\n" +
                "la vía pública fuera de su horario de servicio" +

                "\nV. Los vehículos de carga sólo pueden estacionarse en los lugares autorizados"


        val art78 =
             "Los conductores de vehículos están obligados a disminuir su velocidad y " +
             "detener su marcha, ante las indicaciones de la autoridad de tránsito o cuando se estén " +
             "practicando revisiones a vehículos del servicio público o particular, a fin de comprobar si " +
             "cuentan con el equipo y documentación reglamentarios."

        val art80A_FI =
              "Los conductores de bicicletas, bicicletas adaptadas, triciclos, bicimotos, " +
             "triciclos automotores, cuatrimotos, motonetas y motocicletas tienen las siguientes obligaciones:\n\n" +
             "I. Sólo podrán ser acompañados por el número de personas para el que exista " +
             "asiento disponible y que se señale en la tarjeta de circulación"

        val art80A_FIII =
             "Los conductores de bicicletas, bicicletas adaptadas, triciclos, bicimotos, " +
             "triciclos automotores, cuatrimotos, motonetas y motocicletas tienen las siguientes obligaciones:\n\n" +
             "III. Circular por el carril de la derecha y al rebasar un vehículo de motor deberá " +
             "utilizar el carril izquierdo"

        val art80A_FIV =
              "Los conductores de bicicletas, bicicletas adaptadas, triciclos, bicimotos, " +
              "triciclos automotores, cuatrimotos, motonetas y motocicletas tienen las siguientes obligaciones:\n\n" +
              "IV. Utilizar un sólo carril de circulación"

        val art80A_FV =
             "Los conductores de bicicletas, bicicletas adaptadas, triciclos, bicimotos, " +
             "triciclos automotores, cuatrimotos, motonetas y motocicletas tienen las siguientes obligaciones:\n\n" +
             "V. Circular en la noche con las luces encendidas"

        val art80A_FVI =
             "Los conductores de bicicletas, bicicletas adaptadas, triciclos, bicimotos, " +
             "triciclos automotores, cuatrimotos, motonetas y motocicletas tienen las siguientes obligaciones:\n\n" +
             "VI. Usar casco protector semi-integral para motocicletas tipo scooters que no pasen " +
            "de 100 cúbicos y casco integral para las demás tipos de motocicletas, al igual " +
             "que su acompañante"

        val art80B_FII =
            "Los conductores de bicicletas, bicicletas adaptadas, triciclos, bicimotos, " +
            "triciclos automotores, cuatrimotos, motonetas y motocicletas tienen las siguientes prohibiciones:\n\n" +
            "II. Transitar sobre las banquetas y áreas reservadas al uso exclusivo de peatones, " +
                    "con excepción de las bicicletas y motocicletas de Seguridad Pública cuando " +
                    "éstas cumplan funciones de vigilancia"

        val art80B_FV =
            "Los conductores de bicicletas, bicicletas adaptadas, triciclos, bicimotos, " +
            "triciclos automotores, cuatrimotos, motonetas y motocicletas tienen las siguientes prohibiciones:\n\n" +
            "V. Llevar carga que dificulte su visibilidad, equilibrio, adecuada operación y " +
            "constituya un peligro para sí u otros usuarios de la vía pública"


        val art81 =
            "Los conductores de vehículos de motor de cuatro o más ruedas deben " +
            "respetar el derecho que tienen los vehículos de dos o tres ruedas para usar un carril de " +
            "circulación"

        val art82 =
            "Queda prohibido a los conductores de bicicletas, bicimotos, triciclos " +
            "automotores, cuatrimotos, motonetas y motocicletas, transitar por los carriles centrales o " +
            "interiores de las vías primarias que cuenten con dichos carriles, y en donde así lo indique " +
            "el señalamiento de las vías de acceso controladas."

        val art85 =
            "Las bicicletas que utilicen motor para su propulsión serán consideradas " +
            "dentro de la categoría de motocicletas. Por lo tanto, no pueden circular en las ciclopistas"

        val art157 =
                "Para estacionar un vehículo en la vía pública deben observarse las " +
                "siguientes reglas:\n" +

                "\nI. El vehículo quedará orientado en el sentido de la circulación, excepto cuando " +
                "esté ordenado el estacionarse en batería" +

                "\nII. El vehículo sólo podrá estacionarse en los sitios permitidos por los " +
                "señalamientos de tránsito; en ausencia de éstos, en calles de un sólo sentido " +
                "siempre deberán estacionarse del lado izquierdo y en las avenidas lo harán del " +
                "lado derecho" +

                "\nIII. En las zonas rurales el vehículo deberá ser estacionado fuera de la superficie " +
                "de rodamiento de las vías pavimentadas" +

                "\nIV. En caso de que el vehículo quede estacionado en una pendiente en situación " +
                "de bajada, las ruedas delanteras quedarán dirigidas hacia la guarnición de la " +
                "vía; cuando la posición del vehículo sea de subida, las ruedas se dirigirán hacia " +
                "la superficie de rodamiento; en cualquier caso siempre se aplicará el freno de " +
                "estacionamiento y se colocarán cuñas apropiadas entre el piso y la parte " +
                "delantera, en bajada, o trasera, en subida, de las ruedas posteriores" +

                "\nV. Cuando el conductor se retire del vehículo estacionado deberá dejar apagado " +
                "el motor" +

                "\nVI. Las motocicletas y bicicletas sólo podrán estacionarse en las esquinas " +
                "delimitadas por la autoridad vial en sentido perpendicular a la acera (en batería)" +

                "\nVII. En las zonas no delimitadas por la autoridad vial, las motocicletas y bicicletas " +
                "podrán estacionarse libremente, siempre en batería; dentro de la extensión que " +
                "abarque la franja de la acera pintada de blanco" +

                "\nVIII. Los vehículos con placas distintivas para personas con discapacidad podrán " +
                 "estacionarse en las zonas reservadas para ese tipo de placas por la autoridad " +
                  "vial."


        val art158 =
                "Queda prohibido estacionar vehículos:\n" +

                "\nI. Sobre aceras/banquetas, camellones, andadores, isletas y otras vías " +
                "reservadas para el tránsito de peatones, salvo lo previsto en otras disposiciones " +
                "aplicables" +

                "\nII. En doble o más filas" +

                "\nIII. En un tramo menor a 1 metro de la entrada de una cochera o garaje, y en un " +
                "tramo menor de 5 metros a cada lado del eje de entrada en la acera opuesta a " +
                "ella" +

                "\nIV. En un tramo menor a 5 metros de la entrada de una estación de bomberos y " +
                "vehículos de emergencia, y en un tramo de 25 metros a cada lado del eje de " +
                "entrada en la acera opuesta a ella" +

                "\nV. En zonas de ascenso y descenso de pasajeros y bahías del servicio público de " +
                "transporte colectivo de pasajeros" +

                "\nVI. En carriles de alta velocidad" +

                "\nVII. En las áreas de cruce de peatones;" +

                "\nVIII. En las zonas autorizadas para cargar y descargar" +

                "\nIX. En lugares donde se obstruya la visibilidad de los señalamientos de tránsito o " +
                "la visibilidad de los conductores" +

                "\nX. En zonas o vías públicas prohibidas, identificadas con la señalización " +
                "respectiva" +

                "\nXI. En el lugar destinado para el estacionamiento de vehículos que transporten " +
                "personas con discapacidad" +

                "\nXII. En sentido contrario a la circulación" +

                "\nXIII. En los carriles exclusivos para transporte de pasajeros de autobuses de servicio " +
                "público" +

                "\nXIV. Frente a establecimientos bancarios" +

                "\nXV. Frente a rampas especiales de acceso a las banquetas destinadas para " +
                "discapacitados, u ocupando u obstruyendo los espacios destinados al " +
                "estacionamiento de sus vehículos" +

                "\nXVI. Frente a la entrada y salida de ambulancias en los hospitales" +

                "\nXVII. Frente a los hidrantes para uso de los bomberos" +

                "\nXVIII. Frente a las entradas o salidas de las vías de acceso controlado" +

                "\nXIX. Fuera de los espacios permitidos y señalados para ello, invadiendo u " +
                "obstruyendo otro" +

                "\nXX. Concretamente, motocicletas, en forma paralela a la acera (en cordón)" +

                "\nXXI. Sobre puentes o estructuras elevadas de una vía" +

                "\nXXII. A menos de diez metros del riel más cercano de cualquier cruce ferroviario" +

                "\nXXIII. A menos de cincuenta metros de un vehículo estacionado en sentido opuesto, " +
                "en carreteras de dos carriles y con doble sentido de circulación;" +

                "\nXXIV. A menos de cien metros de una curva o cima" +

                "\nXXV. A menos de quince metros de un cruzamiento de calles" +

                "\nXXVI. A menos de quince metros de la entrada de escuelas, oficinas públicas, salas " +
                        "de espectáculos y demás sitios de alta concentración de personas"


        val art160 =
                "En las zonas de intenso tránsito y cuando sea factible, sólo se permitirá " +
                "el estacionamiento de vehículos en cordón (en paralelo a la acera), y los vehículos deberán " +
                "quedar dentro de las zonas marcadas para tal efecto"


    } //Ley Vial
}