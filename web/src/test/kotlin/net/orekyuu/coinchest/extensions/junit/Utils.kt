package net.orekyuu.coinchest.extensions.junit

fun getValueIfBlankDefault(value: String, defaultValue: String): String {
    return if (value.isBlank()) {
        defaultValue
    } else {
        value
    }
}