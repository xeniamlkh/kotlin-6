@file:Suppress("RemoveRedundantQualifierName")

package ru.otus.homework.homework

import kotlin.properties.Delegates

/**
 * Профиль пользователя
 */
interface UserProfile {
    /**
     * Полное имя
     * Не должно принимать пустые строки
     */
    var fullName: String

    /**
     * Email.
     * Не должен принимать пустые и некорректные строки
     */
    var email: String

    /**
     * Профиль с логированием
     */
    interface Logging : UserProfile, WithLogging

    companion object {
        /**
         * Создает профиль пользователя
         */
        fun create(fullName: String, email: String): UserProfile {
            require(fullName.isNotBlank()) { "Full name should not be empty" }
            require(email.isNotBlank() && emailRegex.matches(email)) { "Invalid email" }
            return ProfileImplementation(fullName, email)
        }

        /**
         * Creates user profile with logging
         */
        fun createWithLogging(fullName: String, email: String): UserProfile.Logging {
            val baseProfile = create(fullName = fullName, email = email)
            val logs = mutableListOf<String>()

            return object : UserProfile.Logging, UserProfile by baseProfile {
                override fun getLog(): List<String> = logs

                override var fullName: String
                    get() = baseProfile.fullName
                    set(value) {
                        val old = baseProfile.fullName
                        baseProfile.fullName = value
                        if (old != baseProfile.fullName) {
                            logs += "Changing `fullName` from '$old' to '${baseProfile.fullName}'"
                        }
                    }

                override var email: String
                    get() = baseProfile.email
                    set(value) {
                        val old = baseProfile.email
                        baseProfile.email = value
                        if (old != baseProfile.email) {
                            logs += "Changing `email` from '$old' to '${baseProfile.email}'"
                        }
                    }
            }

        }
    }
}

/**
 * Проверка емейла на корректность
 */
private val emailRegex = Regex("^[A-Za-z](.*)([@])(.+)(\\.)(.+)")

/**
 * Реализация простого [UserProfile].
 */
//private class ProfileImplementation(override var fullName: String, override var email: String): UserProfile

private class ProfileImplementation(fullNameParam: String, emailParam: String) : UserProfile {

    private val delegate = NonEmptyStringDelegate(fullNameParam)

    override var fullName: String
        get() = delegate.getValue(this, ::fullName)
        set(fullNameParam) = delegate.setValue(this, ::fullName, fullNameParam)

    override var email: String by Delegates.vetoable(emailParam) { property, oldValue, newValue ->
        emailRegex.matches(newValue)
    }

}