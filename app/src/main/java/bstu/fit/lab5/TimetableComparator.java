package bstu.fit.lab5;

import java.sql.Time;
import java.util.Comparator;

public class TimetableComparator implements Comparator<Timetable> {
    public int compare(Timetable tta, Timetable ttb){

        return tta.getSubject().compareTo(ttb.getSubject());
    }
}
