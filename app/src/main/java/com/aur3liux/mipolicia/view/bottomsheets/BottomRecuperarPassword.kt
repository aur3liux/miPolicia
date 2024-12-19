package com.aur3liux.mipolicia.view.bottomsheets

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.ShareLocation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.components.MenuCard
import com.aur3liux.mipolicia.components.MenuImg
import com.aur3liux.mipolicia.components.RoundedButton
import com.aur3liux.mipolicia.components.TextFieldData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomRecuperarPassword(
    email: MutableState<String>,
    onGetNewPassword: () -> Unit,
    onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val onProccesing = remember{ mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val enabledInput = remember { mutableStateOf(true) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(0.6f),
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = {

        }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Row(modifier = Modifier
                .padding(horizontal = 20.dp)
                .height(50.dp)
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { onDismiss() },
                    imageVector = Icons.Filled.Close,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary)

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = "Recuperar contraseña",
                    fontSize = 15.sp,
                    letterSpacing = 0.2.sp,
                    fontFamily = ToolBox.gmxFontRegular,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )

            } //Row

            HorizontalDivider()
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = "Ingresa tu correo el correo electrónico con el que creaste tu cuenta y te enviaremos un enlace para restablecer tu contraseña. \n\nDespues de hacer esta solicitud revisa la bandeja de entrada del correo que ingresaste.",
                fontSize = 13.sp,
                textAlign = TextAlign.Justify,
                letterSpacing = 0.2.sp,
                fontFamily = ToolBox.gmxFontRegular,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
            )

            Spacer(modifier = Modifier.height(20.dp))

            //CORREO ELECTRONICO
            TextFieldData(
                modifier = Modifier.fillMaxWidth(0.9f),
                textFieldValue = email,
                textLabel = "correo electrónico",
                txColor = MaterialTheme.colorScheme.primary,
                maxChar = 80,
                enabled = enabledInput.value,
                textPlaceHolder = "correo@server.com",
                keyboardType = KeyboardType.Email,
                capitalization = KeyboardCapitalization.None,
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                    onDone = {
                        keyboardController!!.hide()
                    }
                ),
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(10.dp))

            RoundedButton(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 10.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                text = "Solicitar nueva contraseña",
                fSize = 13.sp,
                shape =   RoundedCornerShape(15.dp),
                textColor = Color.White,
                backColor = MaterialTheme.colorScheme.surface,
                estatus = onProccesing,
                onClick = {
                    if(email.value.isNullOrEmpty() || !ToolBox.isEmailValid(email.value))
                        Toast.makeText(context, "Correo electrónico no valido", Toast.LENGTH_SHORT).show()
                    else {
                        onGetNewPassword()
                        onDismiss()
                    }
                } //onClick
            )

            Spacer(modifier = Modifier.height(70.dp))
        }

    }
}