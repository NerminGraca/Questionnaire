package helpers;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class DateHelper {
	
	public static final long MINUTE_IN_MILISECONDS = 60000;
	public static final long DAY_IN_MILISECONDS = 86400000;
	public static final DateTimeZone TIME_ZONE = DateTimeZone.UTC;

	public static Date getCurrentUTCDate() {
		return DateTime.now(TIME_ZONE).toDate();
	}
	
	public static long getDateFromReferenceDateInMinutes(long referenceDateMillis, int numberOfMinutes) {
		return referenceDateMillis + (numberOfMinutes * MINUTE_IN_MILISECONDS);
	}

	public static long getDateFromReferenceDate(long referenceDateMillis, int numberOfDays) {
		return referenceDateMillis + (numberOfDays * DAY_IN_MILISECONDS);
	}

	public static boolean isExpired(long currentDate, long expirationDate) {
		return expirationDate > currentDate;
	}

}
