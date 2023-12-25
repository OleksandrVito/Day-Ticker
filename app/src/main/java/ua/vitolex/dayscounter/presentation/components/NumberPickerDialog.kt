package ua.vitolex.dayscounter.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import ua.vitolex.dayscounter.R
import ua.vitolex.dayscounter.domain.model.Event
import ua.vitolex.dayscounter.ui.theme.MyBlack
import ua.vitolex.dayscounter.ui.theme.MyRed
import ua.vitolex.dayscounter.ui.theme.MyWhite
import ua.vitolex.dayscounter.ui.theme.exo
import ua.vitolex.dayscounter.util.scaledSp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberPickerDialog(
    openDialog: MutableState<Boolean>,
    values: List<String>,
    valuesPickerState: PickerState,
    units: List<String>,
    unitsPickerState: PickerState,
    action: () -> Unit,
) {

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            modifier= Modifier.shadow(20.dp, shape = RoundedCornerShape(4.dp), ambientColor = Color.Gray,
                spotColor= Color.Gray),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .background(MyWhite, RoundedCornerShape(4.dp)).padding(top = 10.dp, start = 14.dp, end =14.dp, bottom= 6.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Picker(
                        state = valuesPickerState,
                        items = values,
                        visibleItemsCount = 3,
                        modifier = Modifier.weight(0.3f),
                        textModifier = Modifier.padding(8.dp),
                        textStyle = TextStyle(fontSize = 20.scaledSp(), fontFamily = exo, color = MyBlack)
                    )
                    Picker(
                        state = unitsPickerState,
                        items = units,
                        visibleItemsCount = 3,
                        modifier = Modifier.weight(0.7f),
                        textModifier = Modifier.padding(8.dp),
                        textStyle = TextStyle(fontSize = 20.scaledSp(), fontFamily = exo, color = MyBlack)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.Right) {
                    TextButton(onClick = { openDialog.value = false }) {
                        Text(text = stringResource(R.string.cancel),  color = MyRed, fontFamily = exo, fontSize = 16.scaledSp())
                    }
                    TextButton(onClick = {
                        action.invoke()
                        openDialog.value = false
                    }) {
                        Text(text = stringResource(R.string.ok),  color = MyRed, fontFamily = exo, fontSize = 16.scaledSp())
                    }
                }

            }
        }
    }
}