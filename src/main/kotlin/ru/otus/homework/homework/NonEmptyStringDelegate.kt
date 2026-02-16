package ru.otus.homework.homework

import kotlin.reflect.KProperty

/**
 * Delegate that allows to set non-empty string value
 */
class NonEmptyStringDelegate(nameParam: String = "") {
    private var _name: String = nameParam

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return _name
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, newValue: String) {
        if (newValue.isNotBlank()) {
            _name = newValue
        }
    }
}