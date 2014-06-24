package server;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import database.EventsQueries;
import database.EventsQueriesImpl;

public class Main {
	
	public static void main(String[] args) {
		EventsQueries eventsQueries = new EventsQueriesImpl();
		System.out.println(eventsQueries.addUser("test", "pw", "this is a picture!"));
		System.out.println(eventsQueries.addCategory("Volleyball", 25.0f, 25.0f, "test"));
		System.out.println(eventsQueries.addHappening("Volleyball", new Timestamp(Calendar.getInstance().getTimeInMillis()), new Timestamp(Calendar.getInstance().getTimeInMillis())));
		eventsQueries.getCategoryId("Volleyball", 25.0f, 25.0f, "test");
	}

}
