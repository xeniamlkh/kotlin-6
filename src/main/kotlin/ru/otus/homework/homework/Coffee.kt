package ru.otus.homework.homework

/**
 * Базовый интерфейс кофейного напитка
 */
interface Coffee {
    /**
     * Цена кофейного напитка в копейках
     */
    fun cost(): Int

    /**
     * Описание кофейного напитка
     */
    fun description(): String
}

class SimpleCoffee : Coffee {
    override fun cost() = 200
    override fun description() = "Простой кофе"
}

class MilkDecorator(private val coffee: Coffee) : Coffee {
    override fun cost(): Int = coffee.cost() + 50
    override fun description(): String = "${coffee.description()}, молоко"
}

class SugarDecorator(private val coffee: Coffee) : Coffee {
    override fun cost(): Int = coffee.cost() + 20
    override fun description(): String = "${coffee.description()}, сахар"
}

class VanillaDecorator(private val coffee: Coffee) : Coffee {
    override fun cost(): Int = coffee.cost() + 70
    override fun description(): String = "${coffee.description()}, ваниль"
}