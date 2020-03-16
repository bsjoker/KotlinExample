package ru.skillbranch.kotlinexample

import androidx.annotation.VisibleForTesting
import java.lang.IllegalArgumentException

object UserHolder {
    private val map = mutableMapOf<String, User>()

    fun registerUser(
        fullName: String,
        email: String,
        password: String
    ): User = User.makeUser(fullName, email = email, password = password)
        .also { user ->
            if (!map.containsKey(user.login)) {
                map[user.login] = user
            } else throw IllegalArgumentException("A user with this email already exists")
         }

    fun registerUserByPhone(
        fullName: String,
        rawPhone: String): User = User.makeUser(fullName, phone = rawPhone)
    .also { user ->
        if (!map.containsKey(rawPhone)) {
            map[rawPhone] = user
        } else throw IllegalArgumentException("A user with this email already exists")
    }

    fun  loginUser(login: String, password: String): String? =
        map[login.trim()]?.let {
            if (it.checkPassword(password)) it.userInfo
            else null
        }

    fun requestAccessCode(login: String){
        map[login.trim()]?.let {
            it.getNewAccessCode(it)
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun clearHolder(){
        map.clear()
    }
}