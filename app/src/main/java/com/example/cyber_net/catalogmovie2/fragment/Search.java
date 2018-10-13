package com.example.cyber_net.catalogmovie2.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cyber_net.catalogmovie2.R;
import com.example.cyber_net.catalogmovie2.adapter.MovieAdapter;
import com.example.cyber_net.catalogmovie2.model.ResultsItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.cyber_net.catalogmovie2.utils.Server.NOW_PLAYING;
import static com.example.cyber_net.catalogmovie2.utils.Server.SEARCH;

public class Search extends Fragment {


    @BindView(R.id.txt_search)
    EditText txtSearch;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;
    Unbinder unbinder;
    private Context context;
    private MovieAdapter adapter;
    private List<ResultsItem> list = new ArrayList<>();

    public Search() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        rvSearch.setLayoutManager(new LinearLayoutManager(context));

        getData(txtSearch.getText().toString());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_search)
    public void onViewClicked() {
        getData(txtSearch.getText().toString());
    }

    private void getData(String cari) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, SEARCH+cari, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", "response : " + response);

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    list = Arrays.asList(gson.fromJson(String.valueOf(jsonArray), ResultsItem[].class));

                    adapter = new MovieAdapter(context, list);
                    rvSearch.setAdapter(adapter);

                } catch (Exception e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(context, "Network Error",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, "Network Error",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(context, "Server Error",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(context, "Network Error",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(context, "Data Error",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        //eksekusi
        final RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}
