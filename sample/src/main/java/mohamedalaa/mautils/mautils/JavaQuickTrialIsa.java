package mohamedalaa.mautils.mautils;

import java.util.Calendar;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import mohamedalaa.mautils.core_kotlin.CalendarUtils;
import mohamedalaa.mautils.core_kotlin.CollectionsUtils;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 3/19/2019.
 */
public class JavaQuickTrialIsa {

    private void f1() {
        Calendar calendar = CalendarUtils.getCalendar();

        String year = CalendarUtils.getCurrentYearAsString(calendar);
    }

    private void f2(List<String> list) {
        CollectionsUtils.performIfNotNullNorEmpty(list, strings -> {
            //

            return null;
        });
    }

}
