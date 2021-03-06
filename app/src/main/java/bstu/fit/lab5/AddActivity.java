package bstu.fit.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class AddActivity extends AppCompatActivity {

    EditText subjectAdd;
    CalendarView dayAdd;
    TimePicker timeAdd;
    CheckBox shiftAdd;
    EditText auditoryAdd;
    EditText housingAdd;
    EditText teacherAdd;
    ImageView imageAdd;
    RadioGroup weekAdd;

    Timetable ttElement = new Timetable();
    String dayOfWeek;
    Uri imageURI;
    boolean edit = false;

    static final int GALLERY_REQUEST = 1;
    List<Timetable> ttList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ttList = JSONHelper.importFromJSON(this);

        subjectAdd = findViewById(R.id.subjectAdd);
        dayAdd = findViewById(R.id.calendarView);
        timeAdd = findViewById(R.id.timePicker);
        shiftAdd = findViewById(R.id.shiftStatus);
        auditoryAdd = findViewById(R.id.auditoryAdd);
        housingAdd = findViewById(R.id.housingAdd);
        teacherAdd = findViewById(R.id.teacherAdd);
        imageAdd = findViewById(R.id.imageAdd);
        weekAdd = findViewById(R.id.radioGroup);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            ttElement = (Timetable) arguments.getSerializable(Timetable.class.getSimpleName());
            subjectAdd.setText(ttElement.getSubject());
            auditoryAdd.setText(ttElement.getAuditory());
            housingAdd.setText(ttElement.getHousing());
            teacherAdd.setText(ttElement.getTeacher());
            if (ttElement.getPhotoPath() != null)
                imageAdd.setImageURI(Uri.parse(ttElement.getPhotoPath()));
            if(ttElement.shift) {
                shiftAdd.setChecked(true);
            }
            edit = true;
        }
        dayAdd.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                switch (day) {
                    case Calendar.SUNDAY:
                        dayOfWeek = getResources().getString(R.string.sun);
                        break;
                    case Calendar.MONDAY:
                        dayOfWeek = getResources().getString(R.string.mon);
                        break;
                    case Calendar.TUESDAY:
                        dayOfWeek = getResources().getString(R.string.tue);
                        break;
                    case Calendar.THURSDAY:
                        dayOfWeek = getResources().getString(R.string.thu);
                        break;
                    case Calendar.WEDNESDAY:
                        dayOfWeek = getResources().getString(R.string.wed);
                        break;
                    case Calendar.SATURDAY:
                        dayOfWeek = getResources().getString(R.string.sat);
                        break;
                    case Calendar.FRIDAY:
                        dayOfWeek = getResources().getString(R.string.fri);
                        break;
                }
            }
        });

        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        inflater.inflate(R.menu.menu_back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.addMenu:
                addItem();
                return true;
            case R.id.backMenu:
                back();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    imageURI = imageReturnedIntent.getData();
                    imageAdd.setImageURI(imageURI);
                }
        }
    }

    public void back () {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addItem() {
        if (edit) {
            Timetable tte = findTTByid(ttElement.getId());
            int index = ttList.indexOf(tte);
            setItem(tte);
            //ttList.remove(ttElement);
            //ttList.add(tte);
        }
        else {
            Timetable newElement = new Timetable();
            if (ttList != null) {
                Random r = new Random();
                int id = ttList.size() + r.nextInt(4000);
                newElement.setId(id);
                setItem(newElement);
                ttList.add(newElement);
            } else {
                newElement.setId(1);
                setItem(newElement);
                ttList = new ArrayList<Timetable>();
                ttList.add(newElement);
            }
        }

        boolean result = JSONHelper.exportToJSON(this, ttList);
        if(result){
            Toast.makeText(this, "???????????? ??????????????????", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "???? ?????????????? ?????????????????? ????????????", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void setItem(Timetable tt) {
        String fWeek = getResources().getString(R.string.fWeek);
        tt.setAuditory(auditoryAdd.getText().toString());
        tt.setHousing(housingAdd.getText().toString());
        tt.setTeacher(teacherAdd.getText().toString());
        tt.setSubject(subjectAdd.getText().toString());
        RadioButton radio = findViewById( weekAdd.getCheckedRadioButtonId());
        if (radio.getText().toString().equals(fWeek)) {
            tt.setWeek(1);
        }
        else
            tt.setWeek(2);
        tt.setTime(timeAdd.getHour()+":"+timeAdd.getMinute());
        tt.setDay(dayOfWeek);
        tt.setShift(shiftAdd.isChecked());
        if(imageURI != null)
            tt.setPhotoPath(imageURI.toString());
    }

    Timetable findTTByid(int id){
        for (Timetable timetable : ttList) {
            if (timetable.getId() == id) {
                return timetable;
            }
        }
        return null;
    }
}