package database;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
	
	public static String toMySQLDatetime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		return sdf.format(date);
	}

}
