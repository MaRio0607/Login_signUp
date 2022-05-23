package mx.tec.loginfire

fun main() {

    println("Hello, world!")

    println("Happy Birthday!")
    println("Jhansi")
    println("You are 25!")

    println("no line break")
    println("")
    println("with line \n break")

    print("Happy Birthday!\n")
    print("Jhansi\n")
    print("You are 25!\n")

    // Create a birthday message
    /*This is another comment*/
    //Age in days
    val age = 24
    val days = 24 * 365
    val name = "Rovert"
    val border = "`-._,-'"
    val timesToRepeat = 4
    printBorder(border, timesToRepeat)
    println("Happy Birthday, ${name}!")
    printBorder(border, timesToRepeat)

    // Let's print a cake!
    println("   ,,,,,   ")
    println("   |||||   ")
    println(" =========")
    println("@@@@@@@@@@@")
    println("{~@~@~@~@~}")
    println("@@@@@@@@@@@")

    // This prints an empty line.
    println("")

    println("You are already ${age}!")
    println("${age} is the very best age to celebrate!")

    println("You are already ${days} days old, ${name}!")
    println("${days} days old is the very best age to celebrate!")

    val layers = 5
    printCakeCandles(age)
    printCakeTop(age)
    printCakeBottom(age, layers)
}

fun printBorder(border: String, timesToRepeat: Int) {
    repeat(timesToRepeat) {
        print(border)
    }
    println()
}

fun printCakeCandles(age: Int) {
    print (" ")
    repeat(age) {
        print(",")
    }
    println() // Print an empty line

    print(" ") // Print the inset of the candles on the cake
    repeat(age) {
        print("|")
    }
    println()
}

fun printCakeTop(age: Int) {
    repeat(age + 2) {
        print("=")
    }
    println()
}

fun printCakeBottom(age: Int, layers: Int) {
    repeat(layers) {
        repeat(age + 2) {
            print("@")
        }
        println()
    }
}