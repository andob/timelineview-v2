package ro.dobrescuandrei.timelineviewv2.utils

import org.joda.time.DateTime
import java.text.SimpleDateFormat

fun DateTime.atBeginningOfDay() = this
    .hourOfDay().withMinimumValue()
    .minuteOfHour().withMinimumValue()
    .secondOfMinute().withMinimumValue()
    .millisOfSecond().withMinimumValue()!!

fun DateTime.atEndOfDay() = this
    .hourOfDay().withMaximumValue()
    .minuteOfHour().withMaximumValue()
    .secondOfMinute().withMaximumValue()
    .millisOfSecond().withMaximumValue()!!

fun SimpleDateFormat.formatJodaDateTime(dateTime : DateTime) =
    format(java.util.Date(dateTime.millis))

fun min(x : DateTime, y : DateTime) = if (x<y) x else y
fun max(x : DateTime, y : DateTime) = if (x>y) x else y
