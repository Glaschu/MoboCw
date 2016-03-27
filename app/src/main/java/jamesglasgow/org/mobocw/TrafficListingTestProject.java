package jamesglasgow.org.mobocw;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class TrafficListingTestProject extends AppCompatActivity implements View.OnClickListener {

    private String CurrentUrl = "http://www.trafficscotland.org/rss/feeds/roadworks.aspx";
    private String PlanedUrl = "http://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private String IncidentUrl = "http://trafficscotland.org/rss/feeds/currentincidents.aspx";
    private Button Planed;
    private Button Current;
    private Button Incident;
    private ListView RssEntryListView;
    private Integer Num;
    private boolean selectDi;
    private int sizesave;
    public LinkedList<RoadInfo> alist = null;
    public int Day_x,Month_x,Year_x;
    static final int DIALOG_ID=0;
    ProgressDialog pdh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        //dates
        final Calendar cal= Calendar.getInstance();
        Year_x=cal.get(Calendar.YEAR);
        Month_x=cal.get(Calendar.MONTH);
        Day_x=cal.get(Calendar.DAY_OF_MONTH);

        //list view

        RssEntryListView = (ListView) findViewById(R.id.listDis);
        Planed = (Button) findViewById(R.id.Planed);
        Planed.setOnClickListener(this);
        Current = (Button) findViewById(R.id.Current);
        Current.setOnClickListener(this);
        Incident = (Button) findViewById(R.id.Incident);
        Incident.setOnClickListener(this);

        ///ui
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Seach Rss Feeds", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                showlistDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    @Override
    protected Dialog onCreateDialog(int id){
        if(id==DIALOG_ID) {
            return new DatePickerDialog(this, dpickerLister, Year_x, Month_x, Day_x);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerLister= new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Year_x=year;
            Month_x=monthOfYear +1;
            Day_x=dayOfMonth;
            String DateTemp;
            Log.e("datetest ", "" + Month_x);
            if(Month_x<10)
            {DateTemp=Day_x+"-0"+Month_x+"-"+Year_x;
                Log.e("datetest ", "" + DateTemp);
            }else{
             DateTemp=Day_x+""+Month_x+""+Year_x;
            }
            RssAdapter.SetSearchDateOn(DateTemp);
            RssAdapter.SearchBool(true);
            selectDi=false;
            new ShowDialogAsyncTask().execute();

        }
    };

    public void AmbientbtnClicked(MenuItem item) {

        selectDi=true;
        Num=2;
        //setContentView(R.layout.ambientlayout);
        new ShowDialogAsyncTask().execute();
        Intent i = new Intent(getApplicationContext(), AmbientActivity.class);
        i.putExtra("listSize",sizesave);
        startActivity(i);
    }



    private class ShowDialogAsyncTask extends AsyncTask<Void, Integer, Void>
    {

        int progress_status;

        @Override
        protected void onPreExecute()
        {
            // update the UI immediately after the task is executed
            super.onPreExecute();

            //Toast.makeText(TrafficListingTestProject.this, "Invoke onPreExecute()", Toast.LENGTH_SHORT).show();

            progress_status = 0;
            //txt_percentage.setText("Downloading 0%");

        }

        @Override
        protected Void doInBackground(Void... params)
        {

            while(progress_status<100)
            {

                progress_status += 100;

                publishProgress(progress_status);
                // Sleep but normally do something useful here such as access a web resource
                //SystemClock.sleep(300); // or Thread.sleep(300);
                if (Num==1)
                {

                    Htmlparser info =new Htmlparser();
                    alist=info.ParseStart(PlanedUrl);

                }
                if (Num==2)
                {
                    //LinkedList<RoadInfo> alist = null;
                    Htmlparser info =new Htmlparser();
                    alist=info.ParseStart(CurrentUrl);
                    //RssEntryListView.setAdapter(new RssAdapter(this,alist));

                }
                if (Num==3)
                {
                    //LinkedList<RoadInfo> alist = null;
                    Htmlparser info =new Htmlparser();
                    alist=info.ParseStart(IncidentUrl);
                    //RssEntryListView.setAdapter(new RssAdapter(this,alist));

                }
                // Really need to do some calculation on progress
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);

            //progressBar.setProgress(values[0]);

            pdh= new ProgressDialog(TrafficListingTestProject.this);
            pdh.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pdh.setMessage("Loading Feed Please Wait");
            pdh.setIndeterminate(true);
            pdh.setCancelable(false);
            pdh.show();
            //txt_percentage.setText("Downloading " +values[0] + "%");

        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            pdh.hide();
            //Toast.makeText(TrafficListingTestProject.this,"Invoke onPostExecute()", Toast.LENGTH_SHORT).show();
            if(!selectDi) {
                RssEntryListView.setAdapter(new RssAdapter(TrafficListingTestProject.this, alist));
            }else{
                sizesave=alist.size();
            }
            //txt_percentage.setText("Download complete");
            //startButton.setEnabled(true);
            //progressBar.setVisibility(View.INVISIBLE);

        }

    }



    public void onClick(View v) {
        // TODO Auto-generated method stub
        System.out.println("in on click");
        if (v==Planed)
        {


            selectDi=false;
            Num=1;
            RssAdapter.SearchBool(false);
            new ShowDialogAsyncTask().execute();
            //LinkedList<RoadInfo> alist = null;
            //Htmlparser info =new Htmlparser();
            //alist=info.ParseStart(PlanedUrl);
            //RssEntryListView.setAdapter(new RssAdapter(this,alist));
        }
        if (v==Current)
        {
            selectDi=false;
            Num=2;
            RssAdapter.SearchBool(false);
            new ShowDialogAsyncTask().execute();
            //LinkedList<RoadInfo> alist = null;
            //Htmlparser info =new Htmlparser();
            //alist=info.ParseStart(CurrentUrl);
            //RssEntryListView.setAdapter(new RssAdapter(this,alist));

        }
        if (v==Incident)
        {
            selectDi=false;
            Num=3;
            RssAdapter.SearchBool(false);
            new ShowDialogAsyncTask().execute();
            //LinkedList<RoadInfo> alist = null;
            //Htmlparser info =new Htmlparser();
            //alist=info.ParseStart(IncidentUrl);
            //RssEntryListView.setAdapter(new RssAdapter(this,alist));

        }
    }
    private void showlistDialog()
    {

        final String[] colours ={"Planned","Current"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Colour");
        builder.setItems(colours,new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int whichItem)
            {
                Toast.makeText(getApplicationContext(), colours[whichItem], Toast.LENGTH_SHORT).show();
                switch(whichItem)
                {
                    case 0 :
                        //selectDi=1;
                        showDialog(DIALOG_ID);
                        Num=1;
                        //mainView.setBackgroundColor(Color.RED);
                        break;
                    case 1 :
                        Num=2;
                        showDialog(DIALOG_ID);
                        //mainView.setBackgroundColor(Color.GREEN);
                        break;
                   // case 2 :
                     //   Num=3;
                       // showDialog(DIALOG_ID);
                        //mainView.setBackgroundColor(Color.BLUE);
                       // break;
                }
            }
        });
        builder.setCancelable(false);

        AlertDialog alert = builder.create();
        alert.show();
    }



}
