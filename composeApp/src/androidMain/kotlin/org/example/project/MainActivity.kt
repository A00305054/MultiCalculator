package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalcView()
        }
    }
}

@Composable
fun CalcView() {
    val displayText = remember { mutableStateOf("0") }

    var leftNumber by rememberSaveable { mutableStateOf(0) }
    var rightNumber by rememberSaveable { mutableStateOf(0) }
    var operation by rememberSaveable { mutableStateOf("") }
    var complete by rememberSaveable { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val displayText = remember { mutableStateOf("0") }
        if (complete && operation != "") {
            // 1. Create a mutable variable named answer and assign a value of 0.
            var answer = 0
            // 2. Create a when statement and use the operator variable.
            when (operation) {
                "+" -> answer = leftNumber + rightNumber
                "-" -> answer = leftNumber - rightNumber
                "*" -> answer = leftNumber * rightNumber
                "/" -> if (rightNumber != 0) {
                    answer = leftNumber / rightNumber
                } else {
                }
            }
            displayText.value = answer.toString()
        } else if (operation != "" && !complete) {
            displayText.value = rightNumber.toString()
        } else {
            displayText.value = leftNumber.toString()
        }

        fun numberPress(btnNum: Int) {
            if (complete) {
                leftNumber = 0
                rightNumber = 0
                operation = ""
                complete = false
            }

            if (operation != "" && !complete) {
                rightNumber = rightNumber * 10 + btnNum
            } else if (operation == "" && !complete) {
                leftNumber = leftNumber * 10 + btnNum
            }
        }

        fun operationPress(op: String) {
            if (!complete) {
                operation = op
            }
        }

        fun equalsPress() {
            complete = true
        }
        CalcDisplay(display = displayText)

        Row(modifier = Modifier.padding(top = 16.dp)) {
            Column {
                for (i in 7 downTo 1 step 3) {
                    CalcRow(onPress = { numberPress(it) }, startNum = i, numButtons = 3)
                }
                Row {
                    CalcNumericButton(number = 0, onPress = { numberPress(it) })
                    CalcEqualsButton(onPress = { equalsPress() }, operation = operation)
                }
            }
            Column(modifier = Modifier.padding(start = 16.dp)) {
                val operations = listOf("+", "-", "*", "/")
                operations.forEach { operation ->
                    CalcOperationButton(operation = operation, onPress = { operationPress(it) })
                }
            }
        }
    }

}
@Composable
fun CalcRow(onPress: (number: Int) -> Unit, startNum: Int, numButtons: Int) {
    val endNum = startNum + numButtons
    Row(modifier = Modifier.padding(0.dp)) {
        for (num in startNum until endNum) {
            CalcNumericButton(number = num, onPress = onPress)
        }
    }
}
@Composable
fun CalcDisplay(display: MutableState<String>) {
    Text(
        text = display.value,
        fontSize = 32.sp,
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(5.dp)
    )
}
@Composable
fun CalcNumericButton(number: Int, onPress: (number: Int) -> Unit) {
    Button(
        onClick = { onPress(number)  },
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text = number.toString())
    }
}
@Composable
fun CalcOperationButton(operation: String, onPress: (operation: String) -> Unit) {
    Button(
        onClick = { onPress(operation) },
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text = operation)
    }
}
@Composable
fun CalcEqualsButton(onPress: ()-> Unit, operation: String) {
    Button(
        onClick = { onPress() },
        modifier = Modifier.padding(4.dp)

    ) {
        Text(text = "=")
    }
}
