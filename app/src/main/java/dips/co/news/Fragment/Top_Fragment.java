package dips.co.news.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class Top_Fragment extends Fragment {

    ArrayList<Settergetter> arrayListTop = new ArrayList<Settergetter>();
    private RecyclerView recyclerViewTop;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmen_top, container, false);
        recyclerViewTop = (RecyclerView) view.findViewById(R.id.recycler_view_top);
        recyclerViewTop.setLayoutManager(new LinearLayoutManager(getActivity()));

        getTopNewsList();
        return view;
    }


    private void getTopNewsList() {

        api();
        String source = source_id;
//        String sortBy = "top";
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
                                arrayListTop.add(settergetter);
                            }

                            News_Adapter adapter = new News_Adapter(getContext(), arrayListTop);
                            recyclerViewTop.setAdapter(adapter);

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
