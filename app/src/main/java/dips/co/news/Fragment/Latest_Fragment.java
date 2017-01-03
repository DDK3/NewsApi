package dips.co.news.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dips.co.news.R;

/**
 * Created by Dipesh on 03-Jan-17.
 */

public class Latest_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmen_latest, container, false);

//        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view1);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
////        return inflater.inflate(R.layout.music, container, false);
//        getlist();

        return view;
    }
}
