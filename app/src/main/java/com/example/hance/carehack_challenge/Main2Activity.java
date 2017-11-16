package com.example.hance.carehack_challenge;

/**
 * Created by hance on 16/11/17.
 */
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.*;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;

public class Main2Activity extends Activity
{
    GoogleAccountCredential mCredential;
    private Context context;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    String calendarId,details,titles,email;
    private static final String BUTTON_TEXT = "Call Google Calendar API";
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};
    private com.google.api.services.calendar.Calendar mService = null;
    private Exception mLastError = null;


    Main2Activity( com.google.api.services.calendar.Calendar service,String calenderId,String titles,String details,String email) {
        this.calendarId=calenderId;
        this.titles=titles;
        this.details=details;
        this.email=email;
        oninsert(service);

    }


    public void oninsert(final com.google.api.services.calendar.Calendar service) {
        Log.e("oninsert", "event creation in class");
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {



                Event event = new Event()
                        .setSummary("Hospital")
                        .setLocation("Ernakulam")
                        .setDescription("Appointment is made for "+titles+" Head Physician is "+details);

                DateTime startDateTime = new DateTime("2017-12-17T18:10:00+06:00");
                EventDateTime start = new EventDateTime()
                        .setDateTime(startDateTime)
                        .setTimeZone("Asia/Dhaka");
                event.setStart(start);

                DateTime endDateTime = new DateTime("2017-12-17T18:10:00+06:00");
                EventDateTime end = new EventDateTime()
                        .setDateTime(endDateTime)
                        .setTimeZone("Asia/Dhaka");
                event.setEnd(end);

                String[] recurrence = new String[]{"RRULE:FREQ=DAILY;COUNT=2"};
                event.setRecurrence(Arrays.asList(recurrence));

                EventAttendee[] attendees = new EventAttendee[]{
                        new EventAttendee().setEmail(calendarId),
                        new EventAttendee().setEmail("hancepbenny@gmail.com"),
                };
                event.setAttendees(Arrays.asList(attendees));

                EventReminder[] reminderOverrides = new EventReminder[]{
                        new EventReminder().setMethod("email").setMinutes(24 * 60),
                        new EventReminder().setMethod("popup").setMinutes(10),
                };
                Event.Reminders reminders = new Event.Reminders()
                        .setUseDefault(false)
                        .setOverrides(Arrays.asList(reminderOverrides));
                event.setReminders(reminders);


                try {
                    event = service.events().insert(calendarId, event).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.printf("Event created: %s\n", event.getHtmlLink());

                //code to do the HTTP request
            }
        });
        thread.start();


    }


}