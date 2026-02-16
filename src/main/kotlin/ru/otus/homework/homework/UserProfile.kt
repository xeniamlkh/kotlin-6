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
            TODO("Implement `createWithLogging` function")
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

private class ProfileImplementation(fullNameParam: String, emailParam: String): UserProfile {

    private val delegate = NonEmptyStringDelegate(fullNameParam)

    override var fullName: String
        get() = delegate.getValue(this, ::fullName)
        set(fullNameParam) = delegate.setValue(this, ::fullName, fullNameParam)

    override var email: String by Delegates.vetoable(emailParam) { property, oldValue, newValue ->
        emailRegex.matches(newValue)
    }

}