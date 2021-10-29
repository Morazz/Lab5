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
    Timetable selectedItem;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ttl = new ArrayList<>();

        open();

        adapter = new ListAdapter(this, R.layout.rowlayout, ttl);

        dayField = findViewById(R.id.dayField);
        listView = findViewById(R.id.listView);

        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                selectedItem = (Timetable) parent.getItemAtPosition(position);
                return false;
            }
        });

        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
                MainActivity.super.onCreateContextMenu(menu, view, menuInfo);
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu_item_context, menu);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        inflater.inflate(R.menu.menu_add, menu);
        inflater.inflate(R.menu.menu_sort, menu);
        inflater.inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.addMenu:
                add();
                return true;
            case R.id.sortMenu:
                sort();
                return true;
            case R.id.searchMenu:
                //sort();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.viewCM:
                showItem(selectedItem);
                break;
            case R.id.deleteCM:
                delete(selectedItem);
                break;
            case R.id.editCM:
                edit(selectedItem);
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void sort() {
        TimetableComparator comp = new TimetableComparator();
        adapter.sort(comp);
    }

    public void delete(Timetable tte) {
        adapter.remove(tte);
        JSONHelper.exportToJSON(this,adapter.ttl);
        open();
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

    public void add() {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    public void edit(Timetable selectedItem) {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra(Timetable.class.getSimpleName(), selectedItem);
        startActivity(intent);
    }
}