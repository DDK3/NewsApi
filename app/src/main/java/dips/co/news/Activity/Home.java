package dips.co.news.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    ArrayList<Settergetter> arrayList = new ArrayList<Settergetter>();
    ArrayList<String> spinnerArray = new ArrayList<String>();
    String[] dip = new String[100];
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] tabname;
    private Spinner spinner;

    private String path=" https://newsapi.org/v1/";
    News_Api service;

    void api()
    {
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
        settab();
        getSource_Name();
    }

    private void settab() {
        tabname = new String[]{"TOP", "LATEST"};
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

    void getSource_Name()
    {

//        FunctionConstant.loading(Category_First.this);
        api();

//        SharedPreferences sharedPreferences= getSharedPreferences("knowledgebold", Context.MODE_PRIVATE);
//        String user_id=sharedPreferences.getString("user_id", "");
//        String method="catdisplay";
//        String method="en";


        Call<JsonObject> call = service.sources();

        call.enqueue(new retrofit.Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {

                if(response.isSuccess())
                {
                    try {
                        JSONObject jObject = new JSONObject(response.body().toString());
//                        String code = jObject.getString("status");
                        String  status = jObject.getString("status");

                        if(status.equals("ok")) {

                            final JSONArray jsonArray = jObject.getJSONArray("sources");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                Settergetter setter_getter = new Settergetter();
//
//                                setter_getter.setSource_name(jsonObject.getString("name"));
//                                setter_getter.setCat_name(jsonObject.getString("category"));

                                 dip[i] = jsonObject.getString("name");
//                                arrayList.add(setter_getter);
                            }


//                            Category_Adapter adapter = new Category_Adapter(Home.this, arrayList);
////                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//                            mRecyclerView.setAdapter(adapter);

//                            String dip[] = new String[arrayList.];

//                            ArrayAdapter<ArrayList> adapter = ArrayAdapter.createFromResource(Home.this,
//                                    arrayList, android.R.layout.simple_spinner_item);


                            setspinner();


                        }

                        else {

                            Toast.makeText(Home.this, status, Toast.LENGTH_LONG).show();
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Home.this, "Failed !!!", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(Home.this, "Server issue", Toast.LENGTH_SHORT).show();

                }
//                FunctionConstant.progressDialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void setspinner() {

//        ArrayAdapter<Settergetter> adapter = new ArrayAdapter<Settergetter>(this,
//                android.R.layout.simple_spinner_item, arrayList);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dip);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML
//        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(spinnerArrayAdapter);
    }


}
