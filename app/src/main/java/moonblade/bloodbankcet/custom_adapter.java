package moonblade.bloodbankcet;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nisham on 10/28/2014.
 */
class MySimpleArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    int curyear,curmon,curday,safe_months=3;
    long totdays,totdays_rec,longdate;
    String[] columns;
    int[] to;
    private Date date;

    public MySimpleArrayAdapter(Context context,Cursor c,String[] columns, int[] to,int val,long date_recieved) {
        super(context, R.layout.listviewlayout);
        this.context = context;
        SimpleCursorAdapter adapter;
        this.longdate = date_recieved;
        this.columns=columns;
        this.to=to;
        adapter = new SimpleCursorAdapter(context,R.layout.listviewlayout,c,columns,to,0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setcurrentdate();
        View rowView = inflater.inflate(R.layout.listviewlayout, parent, false);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.red);

        for(int i=0;!columns[i].isEmpty();i++){
            
        }

        if (totdays-totdays_rec<(3*safe_months)) {
            imageView.setImageResource(R.drawable.red);
        } else {
            imageView.setImageResource(R.drawable.green);
        }

        return rowView;
    }

    private void setcurrentdate(){
        final Calendar c = Calendar.getInstance();
        curyear = c.get(Calendar.YEAR);
        curmon = c.get(Calendar.MONTH)+1;
        curday = c.get(Calendar.DAY_OF_MONTH);
//        Toast.makeText(ViewBlood.this,""+curday+" "+curmon+" "+curyear,Toast.LENGTH_SHORT).show();
        totdays=curyear*365+curmon*30+curday;
    }
}