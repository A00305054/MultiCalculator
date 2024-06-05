// src/commonMain/kotlin/Calculator.kt
package com.example

class Calculator {
    fun add(left: Int, right: Int): Int {
        return left + right
    }

    fun subtract(left: Int, right: Int): Int {
        return left - right
    }

    fun multiply(left: Int, right: Int): Int {
        return left * right
    }

    fun divide(left: Int, right: Int): Int? {
        return if (right != 0) {
            left / right
        } else {
            null
        }
    }
}


