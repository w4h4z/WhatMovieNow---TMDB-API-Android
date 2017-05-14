package id.sch.smktelkom_mlg.privateassignment.xirpl224.whatmovienow.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;

import id.sch.smktelkom_mlg.privateassignment.xirpl224.whatmovienow.R;
import id.sch.smktelkom_mlg.privateassignment.xirpl224.whatmovienow.adapter.SourceAdapter;
import id.sch.smktelkom_mlg.privateassignment.xirpl224.whatmovienow.model.Source;
import id.sch.smktelkom_mlg.privateassignment.xirpl224.whatmovienow.model.SourcesResponse;
import id.sch.smktelkom_mlg.privateassignment.xirpl224.whatmovienow.service.GsonGetRequest;
import id.sch.smktelkom_mlg.privateassignment.xirpl224.whatmovienow.service.VolleySingleton;


/**
 * Created by Wahaz on 14/05/2017.
 */

public class NowFragment extends Fragment {
    ArrayList<Source> mList = new ArrayList<>();
    SourceAdapter mAdapter;

    public NowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_now, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SourceAdapter(this.getActivity(), mList);
        recyclerView.setAdapter(mAdapter);

        downloadDataSource();
    }

    private void downloadDataSource() {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=80651057d12eaab3eceb3e1fd79cc100&language=en-US";

        GsonGetRequest<SourcesResponse> myRequest = new GsonGetRequest<SourcesResponse>
                (url, SourcesResponse.class, null, new Response.Listener<SourcesResponse>() {

                    @Override
                    public void onResponse(SourcesResponse response) {
                        Log.d("FLOW", "onResponse: " + (new Gson().toJson(response)));
                        if (response.page.equals("1")) {
                            mList.addAll(response.results);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("FLOW", "onErrorResponse: ", error);
                    }
                });
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(myRequest);
    }
}
