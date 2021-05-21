## timelineview-v2

### DateTime interval picker view for Android

<img src="https://raw.githubusercontent.com/andob/timelineview-v2/master/DEMO.gif"/>

With this library, app users can pick DateTime intervals. Under the hood, it uses JodaTime library in order to calculate the intervals.

### Setup

Import it via:

```
repositories {
    maven { url "https://maven.andob.info/repository/open_source" }
}
```



```
dependencies {
    implementation 'net.danlew:android.joda:2.10.3'
    implementation 'ro.andob.timelineview:timelineview-v2:2.1.9'
}
```

In your application class:

```kotlin
class App : Application()
{
    override fun onCreate()
    {
        super.onCreate()

        JodaTimeAndroid.init(this)

        TimelineViewDefaults.timezone = DateTimeZone.forTimeZone(TimeZone.getDefault())
    }
}
```

The activity:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="#777777"
    tools:context=".MainActivity">

    <ro.dobrescuandrei.timelineviewv2.TimelineView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:id="@+id/timelineView"/>

</LinearLayout>
```

```kotlin
class MainActivity : AppCompatActivity()
{
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timelineView.dateTimeInterval = DailyDateTimeInterval.today()
        timelineView.setOnDateTimeIntervalChangedListener { dateTimeInterval ->
            //todo presenter.loadData(dateTimeInterval)
        }
    }
}
```

### TimelineView API

**1. The ``dateTimeInterval`` property**

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

Note that the base class of ``DailyDateTimeInterval``, ``WeeklyDateTimeInterval``, ``MonthlyDateTimeInterval``, ``YearlyDateTimeInterval``, ``CustomDateTimeInterval``, ``InfiniteDateTimeInterval`` is ``DateTimeInterval``, a class with the following structure:

```kotlin
abstract class DateTimeInterval
(
    val fromDateTime : DateTime,
    val toDateTime : DateTime
) : Serializable
```

**2. Subscribing to date time interval change events.**

This event is triggered each time the user selects an interval from the UI and each time the ``dateTimeInterval`` property is set programatically.

```kotlin
timelineView.setOnDateTimeIntervalChangedListener { dateTimeInterval ->
    //todo presenter.loadData(dateTimeInterval)
}
```

**3. Disabling interval types**

By default, the user can choose between Daily, Weekly, Monthly, Yearly, All time and Custom interval options. To disable some of them:

```kotlin
//disabling CustomDateTimeInterval:
//The user cannot select custom interval option from the popup
timelineView.isCustomDateTimeIntervalSupported = false

//keeping only DailyDateTimeInterval, MonthlyDateTimeInterval, YearlyDateTimeInterval
//the user can now select only Day, Month, Year
timelineView.dateTimeIntervalTypeChangeFlow = DateTimeIntervalTypeChangeFlow.build { 
    from(DailyDateTimeInterval::class.java)
        .to(MonthlyDateTimeInterval::class.java)
        .to(YearlyDateTimeInterval::class.java)
}

//with DateTimeIntervalTypeChangeFlow you can specify what happens when the user press down/up 
//buttons. For instance, in the above example, if a day is selected, by pressing up, the 
//selected interval will become the current month. In the below example, if a day is selected, 
//by pressing up, the selected interval will become the current year
timelineView.dateTimeIntervalTypeChangeFlow = DateTimeIntervalTypeChangeFlow.build {
    from(DailyDateTimeInterval::class.java)
        .to(YearlyDateTimeInterval::class.java)
        .to(MonthlyDateTimeInterval::class.java)
}

//keeping only DailyDateTimeInterval. The user can only select daily intervals:
timelineView.dateTimeIntervalTypeChangeFlow = DateTimeIntervalTypeChangeFlow.build {
    from(DailyDateTimeInterval::class.java)
}
```

**4. Customising the horizontal scrolling RecyclerView cells**

For instance, if we want to disable clicking on intervals from the future:

```kotlin
timelineView.timelineRecyclerViewCellTransformer = object : TimelineRecyclerViewCell.Transformer {
    override fun transform(cellView : TimelineRecyclerViewCell, dateTimeInterval : DateTimeInterval) {
        if (dateTimeInterval.fromDateTime.isAfterNow)
            cellView.setOnClickListener(null)
    }
}
```

**5. TimelineViewDefaults**

Singleton class used to keep default TimelineView settings:

```kotlin
//by default, all TimelineViews should use this TimeZone
TimelineViewDefaults.timezone = DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/Bucharest"))

//by default, all TimelineViews should support only Daily and Monthly intervals
TimelineViewDefaults.dateTimeIntervalTypeChangeFlowFactory = {
    DateTimeIntervalTypeChangeFlow.build { 
        from(DailyDateTimeInterval::class.java)
            .to(MonthlyDateTimeInterval::class.java)
    }
}
```

**6. Customising the appearance**

You can customise the look and feel of the TimelineView with XML:

```xml
<ro.dobrescuandrei.timelineviewv2.TimelineView
    android:layout_width="match_parent"
    android:layout_height="50dp"
    app:tv_selected_cell_text_color="#ffffff"
    app:tv_selected_cell_text_size="18sp"
    app:tv_selected_cell_background_color="#000000"
    app:tv_selected_cell_indicator_color="#ffffff"
    app:tv_selected_cell_indicator_width="8dp"
    app:tv_unselected_cell_text_color="#00ff00"
    app:tv_unselected_cell_text_size="16sp"
    app:tv_unselected_cell_background_color="#aaaaaa"
    app:tv_up_icon="@drawable/ic_arrow_up_white_24dp"
    app:tv_down_icon="@drawable/ic_arrow_down_white_24dp"
    app:tv_calendar_icon="@drawable/ic_calendar_range_outline_white_24dp"
    app:tv_left_buttons_container_background="@drawable/fading_right_gradient_background"
    app:tv_right_buttons_container_background="@drawable/fading_left_gradient_background"
    android:id="@+id/timelineView"/>
```

### Why is it named "v2"?

This is a remake of a in-house library I started in 2017, used across some apps. The V1 library hasn't aged well, since its specifications and features changed a lot over the time. The V2 library is a clean room remake, keeping all V1's features, but with a cleaner architecture.

### License

```
Copyright 2020 Andrei Dobrescu

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
