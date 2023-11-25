package ua.vitolex.dayscounter.presentation.components

import android.text.TextUtils
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.vitolex.dayscounter.ui.theme.MyBlack
import ua.vitolex.dayscounter.ui.theme.MyGray
import ua.vitolex.dayscounter.ui.theme.MyLightGray
import ua.vitolex.dayscounter.ui.theme.MyRed
import ua.vitolex.dayscounter.ui.theme.exo
import ua.vitolex.dayscounter.util.scaledSp

@Composable
fun CustomTextField(
    onValueChanged: (String) -> Unit,
    error: String = "",
    isError: Boolean = false,
    value: String,
) {
    val focusManager = LocalFocusManager.current
    Column() {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = value,
            onValueChange = {
                onValueChanged(it)
            },
            placeholder = {
                Text(
                    "...",
                    fontSize = 22.scaledSp(),
                    fontFamily = exo,
                    fontWeight = FontWeight.Light,
                    color = Color.DarkGray
                )
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.moveFocus(FocusDirection.Down)
//                    kc?.hide()
                }
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MyLightGray,
                unfocusedContainerColor = MyLightGray,
                errorIndicatorColor = MyRed,
                errorContainerColor = MyLightGray,
                errorCursorColor = MyRed,
                focusedIndicatorColor = MyGray,
                unfocusedIndicatorColor = MyGray,
                cursorColor = MyGray,
            ),
            textStyle = TextStyle(
                fontSize = 22.scaledSp(),
                fontFamily = exo,
                fontWeight = FontWeight.Normal,
                color = MyBlack
            ),
            isError = isError
        )
        if (!TextUtils.isEmpty(error) && isError) {
            ShowErrorText(error)
        } else {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ShowErrorText(text: String) {
    Text(
        text = text,
        color = MyRed,
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .padding(start = 5.dp),
        fontSize = 14.scaledSp(),
        fontFamily = exo,
        textAlign = TextAlign.Start
    )
}