package com.example.webserverhmi.features.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

//@Preview
//@Composable
//fun Preview()
//{
//    WebserverHMITheme {
//        Surface {
//            TextFieldWithString(
//                textFieldValue = "192.168.0.1",
//                forceNumber = false,
//                textFieldOnValueChange = {
//
//                }
//            )
//        }
//    }
//}

@Composable
fun TextFieldWithString(
    textFieldValue: String,
    isNumberKeyboard: Boolean,
    stringRes: Int,
    textFieldOnValueChange: (String) -> Unit)
{
    Surface {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(id = stringRes))

            if(!isNumberKeyboard) {
                OutlinedTextField(
                    singleLine = true,
                    value = textFieldValue,
                    label = {
                        Text(text = stringResource(id = stringRes))
                    },
                    onValueChange = { userInput ->
                        textFieldOnValueChange(userInput)
                    }
                )
            } else
            {
                OutlinedTextField(
                    singleLine = true,
                    value = textFieldValue,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    label = {
                        Text(text = stringResource(id = stringRes))
                    },
                    onValueChange = { userInput ->
                        textFieldOnValueChange(userInput)
                    }
                )
            }

        }
    }

}