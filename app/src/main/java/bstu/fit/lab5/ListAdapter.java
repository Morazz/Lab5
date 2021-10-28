package bstu.fit.lab5;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter<Timetable> {
    private LayoutInflater inflater;
    private int layout;
    private List<Timetable> ttl;

    public ListAdapter(Context context, int resource, List<Timetable> ttl) {
        super(context, resource, ttl);
        this.ttl = ttl;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return ttl.size();
    }

    @Override
    public Timetable getItem(int position) {
        return ttl.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.rowlayout, parent, false);

        TextView subject = view.findViewById(R.id.subjItem);
        TextView time = view.findViewById(R.id.timItem);
        TextView auditory = view.findViewById(R.id.audItem);
        TextView housing = view.findViewById(R.id.housItem);
        TextView shift = view.findViewById(R.id.shiftItem);

        Timetable tte = ttl.get(position);

        if(tte.shift) {
            shift.setText(R.string.shift);
            shift.setTextColor(R.color.red);
        }
        subject.setText(tte.getSubject());
        time.setText(tte.getTime());
        auditory.setText(tte.getAuditory());
        housing.setText(tte.getHousing());

        return view;
    }
}
