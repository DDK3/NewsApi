package dips.co.news.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dips.co.news.Adapter.ViewPagerAdapter;
import dips.co.news.Interface.News_Api;
import dips.co.news.R;
import dips.co.news.Util.Settergetter;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class Home extends AppCompatActivity {

    ArrayList<Settergetter> fullData = new ArrayList<Settergetter>();
    String[] dip;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Spinner spinner;
    public static String source_id;

    News_Api service;
    void api()
    {
        String path = "https://newsapi.org/v1/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(path)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(News_Api.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialize();

        getSource_Name();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                source_id = fullData.get(position).getSourceId();
                settab();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Home.this, "Please select any sources", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void settab() {
        String[] tabname = new String[]{"TOP", "LATEST"};
        tabLayout.addTab(tabLayout.newTab().setText(tabname[0]));
        tabLayout.addTab(tabLayout.newTab().setText(tabname[1]));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initialize() {
        spinner = (Spinner) findViewById(R.id.spinner);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    void getSource_Name() {

        api();

        Call<JsonObject> call = service.sources();

        call.enqueue(new retrofit.Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {

                if(response.isSuccess()) {

                    try {
                        JSONObject jObject = new JSONObject(response.body().toString());
                        String  status = jObject.getString("status");

                        if(status.equalsIgnoreCase("ok")) {

                            final JSONArray jsonArray = jObject.getJSONArray("sources");
                            dip = new String[jsonArray.length()];

                            for (int i = 0; i < jsonArray.length(); i++) {
                                Settergetter settergetter = new Settergetter();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                settergetter.setSource_name(jsonObject.optString("name"));
                                settergetter.setSourceId(jsonObject.optString("id"));
//                                dip[i] = jsonObject.getString("name");
                                fullData.add(settergetter);
                            }

                            for (int i=0; i<fullData.size(); i++){
                                dip[i] = fullData.get(i).getSource_name();
                            }

                            setspinner();

                        } else {
                            Toast.makeText(Home.this, status, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Home.this, "Failed !!!", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(Home.this, "Server issue", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void setspinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dip);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}
