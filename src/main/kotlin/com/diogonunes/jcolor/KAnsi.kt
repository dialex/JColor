package com.diogonunes.jcolor

fun Command.colorize(): String = Ansi.colorize(this)

infix fun String.colorize(ansiCode: String): String = Ansi.colorize(this, ansiCode)
infix fun String.colorize(attributes: AnsiFormat): String = Ansi.colorize(this, attributes)
fun String.colorize(vararg attributes: Attribute): String = Ansi.colorize(this, *attributes)
infix fun String.colorize(attributes: Collection<Attribute>): String = Ansi.colorize(this, *attributes.toTypedArray())