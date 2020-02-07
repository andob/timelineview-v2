package ro.dobrescuandrei.timelineviewv2.model

import android.content.Context
import android.content.res.Resources
import org.joda.time.DateTime
import ro.dobrescuandrei.timelineviewv2.base.BaseTimelineRecyclerViewAdapter

abstract class DateTimeInterval<SELF>
(
    val fromDateTime : DateTime,
    val toDateTime : DateTime
)
{
    abstract fun getPreviousDateTimeInterval() : SELF?
    abstract fun getNextDateTimeInterval() : SELF?
    abstract fun getShiftedDateTimeInterval(amount : Int) : SELF?

    abstract operator fun minus(another : DateTimeInterval<*>) : Int

    open fun toInfiniteDateTimeInterval() = InfiniteDateTimeInterval()
    open fun toYearlyDateTimeInterval() = YearlyDateTimeInterval(referenceDateTime = fromDateTime)
    open fun toMonthlyDateTimeInterval() = MonthlyDateTimeInterval(referenceDateTime = fromDateTime)
    open fun toWeeklyDateTimeInterval() = WeeklyDateTimeInterval(referenceDateTime = fromDateTime)
    open fun toDailyDateTimeInterval() = DailyDateTimeInterval(referenceDateTime = fromDateTime)
    open fun toCustomDateTimeInterval() = CustomDateTimeInterval(fromDateTime, toDateTime)

    fun toDateTimeInterval(type : Class<DateTimeInterval<*>>) : DateTimeInterval<*> = when(type)
    {
        InfiniteDateTimeInterval::class.java -> toInfiniteDateTimeInterval()
        YearlyDateTimeInterval::class.java -> toYearlyDateTimeInterval()
        MonthlyDateTimeInterval::class.java -> toMonthlyDateTimeInterval()
        WeeklyDateTimeInterval::class.java -> toWeeklyDateTimeInterval()
        DailyDateTimeInterval::class.java -> toDailyDateTimeInterval()
        CustomDateTimeInterval::class.java -> toCustomDateTimeInterval()
        else -> throw ClassCastException()
    }

    abstract fun toRecyclerViewAdapter(context : Context) : BaseTimelineRecyclerViewAdapter<*>

    override fun toString() = "${this::class.java.simpleName} $fromDateTime - $toDateTime"
    abstract fun toString(resources : Resources) : String

    override fun equals(other : Any?) =
        (other as? DateTimeInterval<*>)?.fromDateTime==fromDateTime&&
        (other as? DateTimeInterval<*>)?.toDateTime==toDateTime

    override fun hashCode() = fromDateTime.millis.toInt()
}
