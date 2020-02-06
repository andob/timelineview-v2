package ro.dobrescuandrei.timelineviewv2.model

import android.content.res.Resources
import org.joda.time.DateTime

abstract class DateTimeInterval<SELF>
(
    val fromDateTime : DateTime,
    val toDateTime : DateTime
)
{
    abstract fun getPreviousDateTimeInterval() : SELF?
    abstract fun getNextDateTimeInterval() : SELF?

    fun toInfiniteDateTimeInterval() = InfiniteDateTimeInterval()
    fun toYearlyDateTimeInterval() = YearlyDateTimeInterval(referenceDateTime = fromDateTime)
    fun toMonthlyDateTimeInterval() = MonthlyDateTimeInterval(referenceDateTime = fromDateTime)
    fun toWeeklyDateTimeInterval() = WeeklyDateTimeInterval(referenceDateTime = fromDateTime)
    fun toDailyDateTimeInterval() = DailyDateTimeInterval(referenceDateTime = fromDateTime)
    fun toCustomDateTimeInterval() = CustomDateTimeInterval(fromDateTime, toDateTime)

    override fun toString() = "${this::class.java.simpleName} $fromDateTime - $toDateTime"
    abstract fun toString(resources : Resources) : String
}
