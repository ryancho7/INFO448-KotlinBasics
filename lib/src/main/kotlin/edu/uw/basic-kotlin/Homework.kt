package edu.uw.basickotlin

// write a "whenFn" that takes an arg of type "Any" and returns a String
fun whenFn(arg: Any): String {
    return when (arg) {
        "Hello" -> "world"
        is String -> "Say what?"
        0 -> "zero"
        1 -> "one"
        in 2..10 -> "low number"
        is Int -> "a number"
        else -> "I don't understand"
    }
}

// write an "add" function that takes two Ints, returns an Int, and adds the values
fun add(lhs: Int, rhs: Int): Int {
    return lhs + rhs
}

// write a "sub" function that takes two Ints, returns an Int, and subtracts the values
fun sub(lhs: Int, rhs: Int): Int {
    return lhs - rhs
}

// write a "mathOp" function that takes two Ints and a function (that takes two Ints and returns an Int), returns an Int, and applies the passed-in-function to the arguments
fun mathOp(lhs: Int, rhs: Int, op: (Int, Int) -> Int): Int {
    return op(lhs, rhs)
}

// write a class "Person" with first name, last name and age
class Person(val firstName: String, val lastName: String, val age: Int) {
    val debugString: String = "[Person firstName:$firstName lastName:$lastName age:$age]"
//    val debugString: String
//        get() = "[Person firstName:$firstName lastName:$lastName age:$age]"
}

// write a class "Money"
 class Money(val amount: Int, val currency: String) {
     init {
         require(amount >= 0) {"Amount must be greater than or equal to 0"}
         // setOf allows for constant lookup time compared to listOf which is linear
         require(currency in setOf("USD", "EUR", "CAN", "GBP")) {"Invalid currency origin"}
     }

    fun convert(targetCurrency: String): Money {
        val convertedAmount: Int = when (currency) {
            "USD" -> when (targetCurrency) {
                "GBP" -> (amount * 0.5).toInt() // 10 USD to 5 GBP
                "EUR" -> (amount * 1.5).toInt() // 10 USD to 15 EUR
                "CAN" -> (amount * 1.25).toInt() // 12 USD to 15 CAN
                else -> this.amount // USD to USD
            }
            "GBP" -> when (targetCurrency) {
                "USD" -> (amount * 2).toInt() // 5 GBP to 10 USD
                "EUR" -> (amount * 3).toInt() // 1 GBP to 3 EUR
                "CAN" -> (amount * 2.5).toInt() // 5 GBP to 12.5 CAN (assuming 5 GBP to CAN)
                else -> this.amount // GBP to GBP
            }
            "EUR" -> when (targetCurrency) {
                "USD" -> (amount * (2.0 / 3)).toInt() // 3 EUR to 2 USD
                "GBP" -> (amount / 3).toInt() // 15 EUR to 5 GBP
                "CAN" -> (amount * (5.0 / 15)).toInt() // 15 EUR to 5 CAN
                else -> this.amount // EUR to EUR
            }
            "CAN" -> when (targetCurrency) {
                "USD" -> (amount * (12.0 / 15)).toInt() // 15 CAN to 12 USD
                "GBP" -> (amount * (5.0 / 12)).toInt() // 15 CAN to 5 GBP
                "EUR" -> (amount * (15.0 / 12)).toInt() // 15 CAN to 15 EUR
                else -> this.amount // CAN to CAN
            }
            else -> this.amount // this should never be reached but is still included
        }
        return Money(convertedAmount, targetCurrency)
    }
    operator fun plus(money: Money): Money {
        // convert it to the currency of the current money class
        val newMoney = money.convert(this.currency)
        return Money(this.amount + newMoney.amount, this.currency)
    }
 }