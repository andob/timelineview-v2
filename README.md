## timelineview-v2

### DateTime interval picker view for Android

<img src="https://raw.githubusercontent.com/andob/timelineview-v2/master/DEMO.gif"/>

With this library, app users can pick DateTime intervals. The library uses the Java 8 Date/Time API (min SDK 26 / Android Oreo).

### Setup

Import it via:

```
repositories {
    maven { url "https://maven.andob.info/repository/open_source" }
}
```

```
dependencies {
    implementation 'ro.andob.timelineview:timelineview-v2:2.5.0'
}
```

Use it:

```xml
<ro.dobrescuandrei.timelineviewv2.TimelineView
	android:layout_width="match_parent"
	android:layout_height="50dp"
	android:id="@+id/timelineView"/>
```

```kotlin
timelineView.dateTimeInterval = DailyDateTimeInterval.today()
timelineView.setOnDateTimeIntervalChangedListener { dateTimeInterval ->
	//todo loadData(dateTimeInterval)
}
```

### TimelineView API

**1. "DateTimeInterval" models**

There are multiple model classes representing date/time intervals:

- ``DailyDateTimeInterval`` represents the interval from a day at 00:00 until today at 23:59
- ``WeeklyDateTimeInterval`` represents the interval from the day at start of week at 00:00 until day at end of week at 23:59
- ``MonthlyDateTimeInterval`` represents the interval from the day at start of month at 00:00 until day at end of month at 23:59
- ``YearlyDateTimeInterval`` represents the interval from the day at start of year at 00:00 until day at end of year at 23:59
- ``CustomDateTimeInterval`` represents an arbitrary interval
- ``InfiniteDateTimeInterval`` represents the interval from the beginning of the Unix Era (1st January 1970) until far in the future

The base class of them is:

```kotlin
abstract class DateTimeInterval
(
    val fromDateTime : ZonedDateTime,
    val toDateTime : ZonedDateTime
) : Serializable
```

Some examples:

```kotlin
val today = DailyDateTimeInterval.today()
val yesterday = today.getPreviousDateTimeInterval()
val tomorrow = today.getNextDateTimeInterval()
val thisMonth = MonthlyDateTimeInterval.around(today.fromDateTime)
val previousMonth = thisMonth.getPreviousDateTimeInterval()
val twentyTwenty = YearlyDateTimeInterval.around(LocalDate.of(2020, 1, 1))
```

**2. The ``dateTimeInterval`` property**

Use this property to get or set the selected DateTime interval. Each time this property is set, the UI updates and the DateTime interval changed event is triggered. Examples:

```kotlin
//set selected interval = today. Given that today is 22.02.2020,
//dateTimeInterval = [22.02.2020 00:00:00 -> 22.02.2020 23:59:59]
timelineView.dateTimeInterval = DailyDateTimeInterval.today()

//set selected interval = yesterday = [21.02.2020 00:00:00 -> 21.02.2020 23:59:59]
timelineView.dateTimeInterval = DailyDateTimeInterval.today().getPreviousDateTimeInterval()

//set selected interval = tomorrow = [23.02.2020 00:00:00 -> 23.02.2020 23:59:59]
timelineView.dateTimeInterval = DailyDateTimeInterval.today().getNextDateTimeInterval()

//set selected interval = current month = [01.02.2020 00:00:00 -> 29.02.2020 23:59:59]
timelineView.dateTimeInterval = MonthlyDateTimeInterval.aroundToday()

//set selected interval = next month = [01.03.2020 00:00:00 -> 31.03.2020 23:59:59]
timelineView.dateTimeInterval = MonthlyDateTimeInterval.aroundToday().getNextDateTimeInterval()

//set selected interval = january 2019 = [01.01.2019 00:00:00 -> 31.01.2019 23:59:59]
timelineView.dateTimeInterval = MonthlyDateTimeInterval(
    referenceDateTime = DateTime(2019, 1, 25, 8, 45))

//set selected interval = current year = [01.01.2020 00:00:00 -> 31.12.2020 23:59:59]
timelineView.dateTimeInterval = YearlyDateTimeInterval.aroundToday()

//set selected interval = [01.01.1970 00:00:00 -> 01.01.4000 00:00:00.000]
timelineView.dateTimeInterval = InfiniteDateTimeInterval()

//set selected interval = [25.01.2019 8:45 -> 31.01.2019 19:00]
timelineView.dateTimeInterval = CustomDateTimeInterval(
    fromDateTime = DateTime(2019, 1, 25, 8, 45),
    toDateTime = DateTime(2019, 1, 31, 19, 0))

//get the interval
val startDateTime = timelineView.dateTimeInterval.fromDateTime //Joda DateTime
val endtDateTime = timelineView.dateTimeInterval.toDateTime //Joda DateTime
```

**3. Subscribing to date time interval change events.**

This event is triggered each time the user selects an interval from the UI and each time the ``dateTimeInterval`` property is set programatically.

```kotlin
timelineView.setOnDateTimeIntervalChangedListener { dateTimeInterval ->
    //todo loadData(dateTimeInterval)
}
```

**4. Setting default timezone**

The default timezone on all dates is UTC, but it can be changed:

```kotlin
DateTimeInterval.defaultTimezone = ZoneId.of("Europe/Bucharest")
```

**5. XML attributes**

```
<attr name="selected_cell_text_color" format="color"/>
<attr name="selected_cell_text_size" format="dimension"/>
<attr name="selected_cell_background_color" format="color"/>
<attr name="selected_cell_indicator_color" format="color"/>
<attr name="selected_cell_indicator_width" format="dimension"/>
<attr name="unselected_cell_text_color" format="color"/>
<attr name="unselected_cell_text_size" format="dimension"/>
<attr name="unselected_cell_background_color" format="color"/>
<attr name="up_icon" format="reference"/>
<attr name="down_icon" format="reference"/>
<attr name="calendar_icon" format="reference"/>
<attr name="left_buttons_container_background" format="reference"/>
<attr name="right_buttons_container_background" format="reference"/>
<attr name="is_daily_date_time_interval_supported" format="boolean"/>
<attr name="is_weekly_date_time_interval_supported" format="boolean"/>
<attr name="is_monthly_date_time_interval_supported" format="boolean"/>
<attr name="is_yearly_date_time_interval_supported" format="boolean"/>
<attr name="is_infinite_date_time_interval_supported" format="boolean"/>
<attr name="is_custom_date_time_interval_supported" format="boolean"/>
<attr name="is_date_time_interval_type_changer_dialog_supported" format="boolean"/>
<attr name="disable_clicking_on_past_intervals" format="boolean"/>
<attr name="disable_clicking_on_future_intervals" format="boolean"/>
```

### Why is it named "v2"?

This is a remake of a in-house library I started in 2017, used across some apps. The V1 library hasn't aged well, since its specifications and features changed a lot over the time. The V2 library is a clean room remake, keeping all V1's features, but with a cleaner architecture.

### License

```
Copyright 2020 - 2021 Andrei Dobrescu

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
