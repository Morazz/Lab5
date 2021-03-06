package bstu.fit.lab5;

import android.net.Uri;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Timetable implements Serializable {
    public int id;
    public String subject;
    public String day;
    public int week;
    public String auditory;
    public String housing;
    public String time;
    public String teacher;
    public String photoPath;
    public Boolean shift;

    public Timetable() {
    }

    public Timetable(String subject, String day, int week, String auditory, String housing, String time, String teacher, String photoPath, Boolean shift) {
        this.subject = subject;
        this.day = day;
        this.week = week;
        this.auditory = auditory;
        this.housing = housing;
        this.time = time;
        this.teacher = teacher;
        this.photoPath = photoPath;
        this.shift = shift;
    }
}