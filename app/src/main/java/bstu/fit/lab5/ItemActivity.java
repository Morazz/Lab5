package bstu.fit.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemActivity extends AppCompatActivity {

    TextView subjectField;
    TextView dayField;
    TextView weekField;
    TextView timeField;
    TextView auditoryField;
    TextView housingField;
    TextView teacherField;
    ImageView teacherImage;
    TextView isShift;

    Timetable ttElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        ImageButton imageButton;
        imageButton = findViewById(R.id.backButtonItem);
        subjectField = findViewById(R.id.subjectItem);
        dayField = findViewById(R.id.dayItem);
        weekField = findViewById(R.id.weekItem);
        timeField = findViewById(R.id.timeItem);
        auditoryField = findViewById(R.id.auditoryItem);
        housingField = findViewById(R.id.housingItem);
        teacherField = findViewById(R.id.teacherItem);
        teacherImage = findViewById(R.id.imageItem);
        isShift = findViewById(R.id.isShift);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            ttElement = (Timetable) arguments.getSerializable(Timetable.class.getSimpleName());
            subjectField.setText(ttElement.getSubject());
            dayField.setText(ttElement.getDay());
            weekField.setText(ttElement.getWeek());
            timeField.setText(ttElement.getTime().toString());
            auditoryField.setText(ttElement.getAuditory());
            housingField.setText(ttElement.getHousing());
            teacherField.setText(ttElement.getTeacher());
            if (ttElement.getPhotoPath() != null)
                teacherImage.setImageURI(ttElement.getPhotoPath());
            if(ttElement.shift) {
                isShift.setText(getResources().getText(R.string.shift));
                isShift.setTextColor(getResources().getColor(R.color.red));
            }
        }

        imageButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                onBackButtonClick();
            }
        });
    }


    public void onBackButtonClick () {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}