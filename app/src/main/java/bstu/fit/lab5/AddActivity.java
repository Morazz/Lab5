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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    ImageButton imageButton;
    ImageButton imageButtonAdd;
    EditText subjectAdd;
    CalendarView dayAdd;
    TimePicker timeAdd;
    CheckBox shiftAdd;
    EditText auditoryAdd;
    EditText housingAdd;
    EditText teacherAdd;
    ImageView imageAdd;
    RadioGroup weekAdd;

    Timetable ttElement;
    String dayOfWeek;
    Uri imageURI;
    private ArrayAdapter<Timetable> adapter;
    private List<Timetable> tts;

    static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        tts = new ArrayList<>();

        imageButton = findViewById(R.id.backButtonItem);
        imageButtonAdd = findViewById(R.id.backButtonAdd);
        subjectAdd = findViewById(R.id.subjectAdd);
        dayAdd = findViewById(R.id.calendarView);
        timeAdd = findViewById(R.id.timePicker);
        shiftAdd = findViewById(R.id.shiftStatus);
        auditoryAdd = findViewById(R.id.auditoryAdd);
        housingAdd = findViewById(R.id.housingAdd);
        teacherAdd = findViewById(R.id.teacherAdd);
        imageAdd = findViewById(R.id.imageAdd);
        weekAdd = findViewById(R.id.radioGroup);

        imageButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                onBackButtonClick();
            }
        });

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            ttElement = (Timetable) arguments.getSerializable(Timetable.class.getSimpleName());
            subjectAdd.setText(ttElement.getSubject());
            auditoryAdd.setText(ttElement.getAuditory());
            housingAdd.setText(ttElement.getHousing());
            teacherAdd.setText(ttElement.getTeacher());
            if (ttElement.getPhotoPath() != null)
                imageAdd.setImageURI(ttElement.getPhotoPath());
            if(ttElement.shift) {
                shiftAdd.setChecked(true);
            }

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

        imageButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                addItem();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        addItem();
        return super.onOptionsItemSelected(item);
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

    public void onBackButtonClick () {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addItem() {
        String fWeek = getResources().getString(R.string.fWeek);
        Timetable newElement  = new Timetable();
        newElement.setAuditory(auditoryAdd.getText().toString());
        newElement.setHousing(housingAdd.getText().toString());
        newElement.setTeacher(teacherAdd.getText().toString());
        newElement.setSubject(subjectAdd.getText().toString());
        RadioButton radio = findViewById( weekAdd.getCheckedRadioButtonId());
        if (radio.getText().toString().equals(fWeek)) {
            newElement.setWeek(1);
        }
        else
            newElement.setWeek(2);
        newElement.setTime(timeAdd.getHour()+":"+timeAdd.getMinute());
        newElement.setDay(dayOfWeek);
        newElement.setShift(shiftAdd.isChecked());
        newElement.setPhotoPath(imageURI);

        tts.add(newElement);

        boolean result = JSONHelper.exportToJSON(this, tts);
        if(result){
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}