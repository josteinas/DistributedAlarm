package server;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import database.EventsQueries;
import database.EventsQueriesImpl;

public class Main {
	
	public static void main(String[] args) {
		EventsQueriesImpl.getInstance();
	}

}
