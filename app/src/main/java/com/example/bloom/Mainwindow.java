package com.example.bloom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Mainwindow extends AppCompatActivity {
EditText search;
TextView cityn,temp,s,dec,feel;
ImageView background,cit;
LinearLayout weath;
Dialog dialog2;
Button knowmore;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

      /*  if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.noon));
        }

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.noonbar));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.noon, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.noonbar));
        }*/



        setContentView(R.layout.activity_mainwindow);
        search = findViewById(R.id.searchbart);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    dialog2.setContentView(R.layout.progress);
                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog2.setCancelable(false);
                    dialog2.show();
                    String s =  getdata();


                    return true;
                }
                return false;
            }
        });
        cityn = findViewById(R.id.citynam);
        temp = findViewById(R.id.temp);
        s = findViewById(R.id.textview7);
        dec = findViewById(R.id.desc);
        background = findViewById(R.id.back);
        weath = findViewById(R.id.mainwindow1);
        cit = findViewById(R.id.city);
        dialog2 = new Dialog(this);
        feel = findViewById(R.id.flike);
        knowmore = findViewById(R.id.knowmore);





    }

    public String getdata(){
        String apikey = "ca57ca6bff8f4f6ce40d2c5a6a2c38cc";
        String city = search.getText().toString();
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+apikey;

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    search.getText().clear();
                    JSONObject object = response.getJSONObject("main");
                    String temperature= object.getString("temp");
                    Double tempera = Double.parseDouble(temperature) - 273.15;
                    int tempval = tempera.intValue();
                    temp.setText(String.valueOf(tempval));

                    String feellike = object.getString("feels_like");
                    Double tempera2 = Double.parseDouble(feellike) - 273.15;
                    int tempval2 = tempera2.intValue();
                    feel.setText("Feels like "+String.valueOf(tempval2)+"°c");

                    String cname= object.getString("humidity");
                    JSONObject objectwind = response.getJSONObject("sys");
                    String cityname= response.getString("name");
                    cityn.setText(cityname);

                    JSONArray jsonArray = response.getJSONArray("weather");
                    JSONObject jsonObjectweather = jsonArray.getJSONObject(0);
                    description = jsonObjectweather.getString("main");
                    dec.setText(description);




                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                
                weath.setVisibility(View.VISIBLE);
                background.setVisibility(View.VISIBLE);
                knowmore.setVisibility(View.VISIBLE);
                cit.setVisibility(View.GONE);


                if(description.equalsIgnoreCase("Smoke") || description.equalsIgnoreCase("Clouds") || description.equalsIgnoreCase("Haze") || description.equalsIgnoreCase("Rain")|| description.equalsIgnoreCase("Fog"))
                {background.setImageResource(R.drawable.cloudy);}
                else
                {background.setImageResource(R.drawable.sunny);}
                dialog2.dismiss();

                knowmore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Mainwindow.this, details.class);
                        intent.putExtra("city_url", url);
                        startActivity(intent);

                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog2.dismiss();
                TastyToast.makeText(getApplicationContext(), "Please Enter a Valid City Name.", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            }
        });
        queue.add(request);

        return description;
    }
}