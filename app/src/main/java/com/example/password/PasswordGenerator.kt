package com.example.password

class PasswordGenerator {
private val characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!#%$()=?"

    fun generatePassword(length: Int, specialWord: String = ""):String{
        val sb = StringBuilder(length)
        for(x:Int in 0 until length)
        {
            val random:Int=(characters.indices).random()
            sb.append(characters[random])
        }
        sb.insert((0 until length).random(),specialWord)

        return sb.toString()

    }
}
