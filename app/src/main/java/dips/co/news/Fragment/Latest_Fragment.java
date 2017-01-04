package dips.co.news.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dips.co.news.Adapter.News_Adapter;
import dips.co.news.Interface.News_Api;
import dips.co.news.R;
import dips.co.news.Util.Settergetter;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static dips.co.news.Activity.Home.source_id;

/**
 * Created by Dipesh on 03-Jan-17.
 */

public class Latest_Fragment extends Fragment {

    ArrayList<Settergetter> arrayListLatest = new ArrayList<Settergetter>();
    private RecyclerView recyclerViewLatest;

    News_Api service;

    void api() {
        String path = "https://newsapi.org/v1/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(path)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(News_Api.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmen_latest, container, false);
        recyclerViewLatest = (RecyclerView) view.findViewById(R.id.recycler_view_latest);
        recyclerViewLatest.setLayoutManager(new LinearLayoutManager(getActivity()));

        getLatestNewsList();

        return view;
    }

    private void getLatestNewsList() {

        api();
        String source = source_id;
//        String sortBy = "latest";
        Call<JsonObject> call = service.articles(source);

        call.enqueue(new retrofit.Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {

                if (response.isSuccess()) {

                    try {
                        JSONObject jObject = new JSONObject(response.body().toString());
                        String status = jObject.getString("status");

                        if (status.equalsIgnoreCase("ok")) {

                            final JSONArray jsonArray = jObject.getJSONArray("articles");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                Settergetter settergetter = new Settergetter();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                settergetter.setNews_title(jsonObject.optString("title"));
                                settergetter.setNews_description(jsonObject.optString("description"));
                                settergetter.setNews_author(jsonObject.optString("author"));
                                settergetter.setNews_DateTime(jsonObject.optString("publishedAt"));
                                settergetter.setNews_image_path(jsonObject.optString("urlToImage"));
                                arrayListLatest.add(settergetter);
                            }

                            News_Adapter adapter = new News_Adapter(getContext(), arrayListLatest);
                            recyclerViewLatest.setAdapter(adapter);

                        } else {
                            Toast.makeText(getActivity(), status, Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Failed !!!", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Server problem", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

}
