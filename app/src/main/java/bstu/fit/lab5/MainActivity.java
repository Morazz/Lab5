package bstu.fit.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView dayField;
    ListView listView;
    private List<Timetable> ttl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ttl = new ArrayList<>();
        open();
        ListAdapter adapter = new ListAdapter(this, R.layout.rowlayout, ttl);

        dayField = findViewById(R.id.dayField);
        listView = findViewById(R.id.listView);

        listView.setAdapter(adapter);

        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                Timetable selectedItem = (Timetable) parent.getItemAtPosition(position);
                showItem(selectedItem);
            }
        });

         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        inflater.inflate(R.menu.menu_add, menu);
        inflater.inflate(R.menu.menu_sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        dayField.setText(item.getItemId());
        return super.onOptionsItemSelected(item);
        /*
        switch (item.getItemId())
        {
            case R.id.addMenu:
                Add();
                return true;
            case R.id.sortMenu:
                Sort();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

         */
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item_context, menu);
    }


    public void Sort(String d) {
        Toast.makeText(this, d, Toast.LENGTH_SHORT).show();
    }

    public void showItem(Timetable item) {
        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra(Timetable.class.getSimpleName(), item);
        startActivity(intent);
    }

    public void open(){
            ttl = JSONHelper.importFromJSON(this);
        if(ttl!=null){
            Toast.makeText(this, "Данные восстановлены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось открыть данные", Toast.LENGTH_LONG).show();
        }
    }

    public void Add(MenuItem item) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                            long id) {
        Timetable selectedItem = (Timetable) parent.getItemAtPosition(position);
        showItem(selectedItem);
    }
}