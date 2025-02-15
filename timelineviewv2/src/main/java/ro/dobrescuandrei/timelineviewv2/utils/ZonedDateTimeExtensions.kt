package ro.dobrescuandrei.timelineviewv2.utils

import java.time.LocalTime
import java.time.ZonedDateTime

fun ZonedDateTime.atBeginningOfDay() = with(LocalTime.MIN)!!
fun ZonedDateTime.atEndOfDay() = with(LocalTime.MAX)!!

fun min(x : ZonedDateTime, y : ZonedDateTime) = if (x < y) x else y
fun max(x : ZonedDateTime, y : ZonedDateTime) = if (x > y) x else y
