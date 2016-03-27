package jamesglasgow.org.mobocw;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jamesglasgow on 22/02/16.
 */
public class Htmlparser {

    public LinkedList<RoadInfo> ParseStart(String dataToParse){

        URL url1 = null;
        try {
            url1 = new URL(dataToParse);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.e("Checks", "Website " + url1);
        LinkedList<RoadInfo> alist = null;
        alist=parseData(url1);
        return alist;
    }

    private LinkedList<RoadInfo> parseData(URL dataToParse)
    {
        RoadInfo Info=null;
        LinkedList<RoadInfo> alist = null;

        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            //xpp.setInput( new StringReader ( dataToParse ) );
            xpp.setInput(getInputStream(dataToParse), "UTF_8");
            boolean stared=false;
            int eventType = xpp.getEventType();
            //boolean insideItem = false;
            alist  = new LinkedList<RoadInfo>();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                // Found a start tag
                if(eventType == XmlPullParser.START_TAG)
                {
                    // Check which Tag we have
                    if (xpp.getName().equalsIgnoreCase("item"))//channel
                    {
                        if(!stared){
                            stared=true;
                        }
                       // Log.e("MyTag", "Parsing error1");
                    }
                    else
                        // Check which Tag we have
                        if (xpp.getName().equalsIgnoreCase("title")&&stared)
                        {

                            Info = new RoadInfo();
                            //
                            Info.setTitle(xpp.nextText());
                           // Log.e("Testing", "title " + Info.getTitle());
                        }
                        else if (xpp.getName().equalsIgnoreCase("description")&&stared)
                        {
                            //Info.setTitle(xpp.nextText());value.replaceAll("<br>","");
                            String temp =xpp.nextText();
                            temp = temp.replaceAll("<br />"," ");
                            temp = temp.replaceAll("<br/>"," ");
                            Info.setDiscription(temp);
                            Info.setStartDate(FindStartDate(temp));
                            Info.setEndDate(FindEndDate(temp));
                           // Log.e("Testing", "description " + Info.getDiscription());

                        }else if (xpp.getName().equalsIgnoreCase("pubDate")&&stared)
                        {
                            Info.setPubDate(xpp.nextText());
                            //Log.e("Testing", "pubDate " + Info.getPubDate());
                        }
					/*else if (xpp.getName().equalsIgnoreCase("georss"))
					{
						Info.setGPS(xpp.nextText());
						Log.e("MyTag", "Parsing error5");
					}
					else if (xpp.getName().equalsIgnoreCase("pubDate"))
					{
						Info.setPubDate(xpp.nextText());
						Log.e("MyTag", "Parsing error6");
					}*/
                }else
                if(eventType == XmlPullParser.END_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        //Log.e("MyTag","widget is " + widget.toString());
                        alist.add(Info);
                    }
                    else
                    if (xpp.getName().equalsIgnoreCase("channel"))
                    {
                        int size;
                        size = alist.size();
                        //Log.e("MyTag", "channel size is " + size);
                    }
                }

                // Get the next event
                eventType = xpp.next();

            } // End of while
        }
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag", "Parsing error " + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag", "IO error during parsing");
        }
        /*ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout., headlines);
       ListView lv = (ListView)findViewById(R.id.listDis);
        lv.setAdapter(adapter);*/

        //Log.e("MyTag", "End document");
        return alist;

    }
    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }

    }

    public Date FindStartDate(String dis){
        Pattern pattern = Pattern.compile("(?<=Start Date:).*?(?=-)");
        Matcher matcher = pattern.matcher(dis);


        boolean found = false;
        while (matcher.find()) {

            //Log.e("DateFind ", matcher.group().toString());
            String temp = matcher.group().toString();
            //Log.e("temp ", temp);
            Date re =datetoint(temp);
            return re;

            //return matcher.group().toString();
        }
        if (!found) {
            //System.out.println("I didn't find the text");
            return null;
        }

        return null;
    }

    public Date FindEndDate(String dis){
        Pattern pattern = Pattern.compile("(?<=End Date:).*?(?=-)");
        Matcher matcher = pattern.matcher(dis);


        boolean found = false;
        while (matcher.find()) {

            //Log.e("DateFind ", matcher.group().toString());
            String temp = matcher.group().toString();
            //Log.e("temp ", temp);
            Date re =datetoint(temp);
            return re;

            //return matcher.group().toString();
        }
        if (!found) {
            System.out.println("I didn't find the text");
            return null;
        }

        return null;
    }
    public Date datetoint(String Date){
        java.util.Date Clean;

        Date = Date.replaceAll("Monday,","");
        Date = Date.replaceAll("Tuesday,","");
        Date = Date.replaceAll("Wednesday,","");
        Date = Date.replaceAll("Thursday,","");
        Date = Date.replaceAll("Friday,","");
        Date = Date.replaceAll("Saturday,","");
        Date = Date.replaceAll("Sunday,","");

        Date = Date.replaceAll("January","01");
        Date = Date.replaceAll("February","02");
        Date = Date.replaceAll("March","03");
        Date = Date.replaceAll("April","04");
        Date = Date.replaceAll("May","05");
        Date = Date.replaceAll("June","06");
        Date = Date.replaceAll("July","07");
        Date = Date.replaceAll("August","08");
        Date = Date.replaceAll("September","09");
        Date = Date.replaceAll("October","10");
        Date = Date.replaceAll("November","11");
        Date = Date.replaceAll("December","12");
        Date = Date.replaceAll(" ","");
        //Log.e("DateFind ints", Date);
        Clean= StringtoDate(Date);
        //int temp = Integer.parseInt(Date);
       // Log.e("DateFind Integer", ""+temp);

        return Clean;
    }
    public Date StringtoDate(String DateS){
        String DateString =DateS;

        DateFormat inputFormat = new SimpleDateFormat("ddMMyyyy");
        //inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));


        Date parsed = null;
        try {
            parsed = inputFormat.parse(DateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
       // String outputText = inputFormat.format(parsed);
       // Log.e("DateFind DateClass", ""+parsed);
        //Log.e("DateFind DateClasstime", ""+parsed.getTime());
        return parsed;
    }

}
