package ua.vitolex.dayscounter.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.vitolex.dayscounter.R
import ua.vitolex.dayscounter.domain.model.Event
import ua.vitolex.dayscounter.ui.theme.MyBlack
import ua.vitolex.dayscounter.ui.theme.MyRed
import ua.vitolex.dayscounter.ui.theme.MyWhite
import ua.vitolex.dayscounter.ui.theme.exo
import ua.vitolex.dayscounter.util.scaledSp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteDialog(
    openDialog: MutableState<Boolean>,
    action: () -> Unit,
    eventToDelete: MutableState<List<Event>>,
) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            modifier= Modifier.shadow(20.dp, shape = RoundedCornerShape(4.dp), ambientColor = Color.Gray,
                spotColor= Color.Gray),
        ) {
            Box(
                modifier = Modifier
                    .background(MyWhite, RoundedCornerShape(4.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.delete_event),
                        color = MyRed,
                        fontFamily = exo,
                        fontSize = 20.scaledSp(),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Divider(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        thickness = 2.dp,
                        color = Color.Gray
                    )
                    Text(
                        text = stringResource(R.string.are_you_sure_to_delete),
                        color = MyBlack,
                        fontFamily = exo,
                        fontSize = 18.scaledSp(),
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                    Row() {
                        ElevatedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MyRed,
                            ),
                            onClick = {
                                action.invoke()
                                openDialog.value = false
                                eventToDelete.value = mutableListOf()
                            },
                            shape = RoundedCornerShape(4.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                8.dp
                            ),
                        ) {
                            Text(
                                text = stringResource(id = ua.vitolex.dayscounter.R.string.yes),
                                color = MyWhite,
                                fontFamily = exo,
                                fontSize = 18.scaledSp(),
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        ElevatedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                            ),
                            onClick = {
                                openDialog.value = false
                                eventToDelete.value = mutableListOf()
                            },
                            shape = RoundedCornerShape(4.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                8.dp
                            ),
                        ) {
                            Text(
                                text = stringResource(id = ua.vitolex.dayscounter.R.string.no),
                                color = MyRed,
                                fontFamily = exo,
                                fontSize = 18.scaledSp(),
                            )
                        }
                    }
                }
            }

        }
    }
}