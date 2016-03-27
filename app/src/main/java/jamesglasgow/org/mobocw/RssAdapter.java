package jamesglasgow.org.mobocw;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by jamesglasgow on 23/02/16.
 */

public class RssAdapter extends BaseAdapter {
    private List<RoadInfo> items;
    private Context context;
    private LayoutInflater inflater;
    static int Load =0;
    static boolean Searching =false;
    static Date parsed = null;


    public RssAdapter(Context context, List<RoadInfo> items) {
        inflater = LayoutInflater.from(context);
        //this.context = context;
        //if(Load==0&&Searching){
            //Log.e("Crashing","before");
           // this.items = Filter(items);
           // Load=1;
         //   Log.e("Crashing","fill");
       // }else{
         this.items = items;
    }
  //  @Override
    public int getCount() {
        return items.size();
    }

  //  @Override
    public Object getItem(int position) {
        return items.get(position);
    }
    //@Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        TextView textView1;
        TextView textView2;
        LinearLayout lLayout;
        ListView Lview;
    }
   public View getView(int position, View convertView, ViewGroup parent) {


       ViewHolder holder = null;
       // RelativeLayout twoText;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listlayout, null);
            holder.textView1 = (TextView) convertView.findViewById(R.id.textTitle);
            holder.textView2 = (TextView) convertView.findViewById(R.id.textDisc);
            holder.Lview =(ListView) convertView.findViewById(R.id.listDis);
            holder.lLayout = (LinearLayout) convertView.findViewById(R.id.linear_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
if(Searching) {
    if (parsed.after(items.get(position).getStartDate()) && parsed.before(items.get(position).getEndDate()) && Searching) {
        holder.textView1.setText(items.get(position).getTitle());
        holder.textView2.setText(items.get(position).getDiscription());

        Log.e("ListText", "" + Searching);
        if (convertView.getVisibility() == View.GONE) {
            convertView.setVisibility(View.VISIBLE);
            convertView.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
        }

    } else {
        //convertView.setLayoutParams(new AbsListView.LayoutParams(-1,1));
        holder.textView1.setText(items.get(position).getTitle());
        holder.textView2.setText(items.get(position).getDiscription());

        holder.lLayout.setLayoutParams(new AbsListView.LayoutParams(-1, 1));
        holder.lLayout.setVisibility(View.GONE);
        //holder.Lview.setDivider(null);
        //holder.Lview.setDividerHeight(0);
        //holder.lLayout.getListView().setDivider(null);
        //getListView().setDividerHeight(0);
    }
    //Log.e("ListText", "dd" + Searching);
    //Log.e("ListText2", " "+parsed.getTime());
    //Log.e("listparsestart ", "" +parsed);
    //Log.e("listparsestart ", "" +items.get(position).getStartDate());
    //Log.e("ListText3", " "+items.get(position).getStartDate().getTime());
    }else{
    holder.textView1.setText(items.get(position).getTitle());
    holder.textView2.setText(items.get(position).getDiscription());
    }



        return convertView;
    }
    public static void SetSearchDateOn(String DateS){
        String DateString =DateS;

        DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        //inputFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            parsed = inputFormat.parse(DateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Log.e("listparse0 ", "" +DateS);
        //Log.e("listparse ", "" +parsed);
        //Log.e("listparse2", " "+parsed.getTime());

    }
    public static void SearchBool(Boolean Bo){
        Load=0;
        if(!Bo){
            Searching =false;
        }else if(Bo){Searching =true;}

    }


}


