package com.example.bloom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class details extends AppCompatActivity {
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Dialog dialog2;
        dialog2 = new Dialog(this);
        dialog2.setContentView(R.layout.loading);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.setCancelable(true);
        dialog2.show();

        TextView citname,cittemp,citfeellike,citdesc,citmin,citmax,citpress,cithum,citsea,citground,winspeed,windir,wingust,citlong,citlat,citsunrise,citsunset;
        citname = findViewById(R.id.dcitynam);
        cittemp = findViewById(R.id.dtemp);
        citfeellike = findViewById(R.id.dflike);
        citdesc = findViewById(R.id.ddesc);
        citmax = findViewById(R.id.dmax);
        citmin = findViewById(R.id.dmin);
        citpress = findViewById(R.id.dpressur);
        cithum = findViewById(R.id.dhumi);
        citsea = findViewById(R.id.dsea);
        citground = findViewById(R.id.dground);
        winspeed = findViewById(R.id.dwinspeed);
        windir = findViewById(R.id.dwindir);
        wingust = findViewById(R.id.dwingust);
        citlong = findViewById(R.id.dlon);
        citlat = findViewById(R.id.dlat);
        citsunrise = findViewById(R.id.dsunrise);
        citsunset = findViewById(R.id.dsunset);


        String cityUrl = getIntent().getStringExtra("city_url");

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, cityUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    JSONObject object = response.getJSONObject("main");

                    String temperature= object.getString("temp");
                    Double tempera = Double.parseDouble(temperature) - 273.15;
                    int tempval = tempera.intValue();
                    cittemp.setText(String.valueOf(tempval));

                    String feellike = object.getString("feels_like");
                    Double tempera2 = Double.parseDouble(feellike) - 273.15;
                    int tempval2 = tempera2.intValue();
                    citfeellike.setText("Feels like "+String.valueOf(tempval2)+"°c");



                    String cityname= response.getString("name");
                    citname.setText(cityname);

                    JSONArray jsonArray = response.getJSONArray("weather");
                    JSONObject jsonObjectweather = jsonArray.getJSONObject(0);
                    description = jsonObjectweather.getString("main");
                    citdesc.setText(description);

                    String tempmax = object.getString("temp_max");
                    Double tempera3 = Double.parseDouble(tempmax) - 273.15;
                    int tempval3 = tempera3.intValue();
                    citmax.setText(String.valueOf(tempval3)+"°c");

                    String tempmin = object.getString("temp_min");
                    Double tempera4 = Double.parseDouble(tempmin) - 273.15;
                    int tempval4 = tempera4.intValue();
                    citmin.setText(String.valueOf(tempval4)+"°c");

                    String pressre = object.getString("pressure");
                    citpress.setText(String.valueOf(pressre)+" hpa");

                    String humid= object.getString("humidity");
                    cithum.setText(String.valueOf(humid)+" %");

                    if(object.has("sea_level"))
                    {   String seal= object.getString("sea_level");
                        citsea.setText(String.valueOf(seal)+"");
                    }
                    else
                    { citsea.setText(String.valueOf("NA"));}

                    if(object.has("grnd_level"))
                    {  String grndl= object.getString("grnd_level");
                    citground.setText(String.valueOf(grndl)+"");}
                    else
                    { citground.setText(String.valueOf("NA"));}

                    JSONObject wind = response.getJSONObject("wind");
                    String winsp = wind.getString("speed");
                    winspeed.setText(String.valueOf(winsp)+" m/s");

                    String windeg = wind.getString("deg");
                    windir.setText(String.valueOf(windeg)+" °");

                    if(wind.has("gust"))
                    {
                    String wingu = wind.getString("gust");
                    wingust.setText(String.valueOf(wingu)+" m/s");}
                    else
                        wingust.setText(String.valueOf("NA"));

                    JSONObject coord = response.getJSONObject("coord");

                    if(coord.has("lon"))
                    {String longi = coord.getString("lon");
                    citlong.setText(String.valueOf(longi));}
                    else
                        citlong.setText(String.valueOf("NA"));

                    String lati = coord.getString("lat");
                    citlat.setText(String.valueOf(lati));

                    JSONObject sun = response.getJSONObject("sys");

                    long sunrise = sun.getLong("sunrise");
                    long javaTimestamp = sunrise * 1000L;
                    Date date = new Date(javaTimestamp);
                    String sr = new SimpleDateFormat("hh:mm a").format(date);
                    citsunrise.setText(String.valueOf(sr));

                    long sunset = sun.getLong("sunset");
                    long javaTimestamp1 = sunset * 1000L;
                    Date date1 = new Date(javaTimestamp1);
                    String sr1 = new SimpleDateFormat("hh:mm a").format(date1);
                    citsunset.setText(String.valueOf(sr1));


                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog2.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                TastyToast.makeText(getApplicationContext(), "Some Error Occured . Please try again.", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            }
        });
        queue.add(request);

    }
}