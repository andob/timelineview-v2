package ro.dobrescuandrei.timelineviewv2.model

import android.content.res.Resources
import org.joda.time.DateTime
import ro.dobrescuandrei.timelineviewv2.TimelineViewDefaults
import ro.dobrescuandrei.timelineviewv2.utils.atBeginningOfDay
import ro.dobrescuandrei.timelineviewv2.utils.atEndOfDay

class YearlyDateTimeInterval
(
    referenceDateTime : DateTime = DateTime.now(TimelineViewDefaults.timezone)
) : DateTimeInterval<YearlyDateTimeInterval>
(
    fromDateTime = referenceDateTime
        .dayOfYear().withMinimumValue()
        .atBeginningOfDay(),

    toDateTime = referenceDateTime
        .dayOfYear().withMaximumValue()
        .atEndOfDay()
)
{
    override fun getPreviousDateTimeInterval() =
        YearlyDateTimeInterval(referenceDateTime = fromDateTime.withYear(fromDateTime.year-1))

    override fun getNextDateTimeInterval() =
        YearlyDateTimeInterval(referenceDateTime = fromDateTime.withYear(fromDateTime.year+1))

    override fun toString(resources : Resources) =
        fromDateTime.year.toString()
}
