/**
 *
 */
package dl.automation.core.utils;

import java.time.Duration;
import java.time.LocalDateTime;


public class DateTimeUtils {
    public static Duration getDuration(LocalDateTime startTime, LocalDateTime endTime) {
        return java.time
                .Duration
                .between(startTime, endTime);
    }

    public static String getDurationAsString(Duration duarion) {
        long durationMilliSecs = duarion.toMillis();
        long minites = durationMilliSecs / 60000;
        long remaing = durationMilliSecs % 60000;
        long secs = remaing / 1000;
        remaing = remaing % 1000;
        long millis = remaing;
        return String.join(":", String.valueOf(minites), String.valueOf(secs), String.valueOf(millis));
    }
}
