package com.mdamine.movieapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mdamine.movieapp.Movie;
import com.mdamine.movieapp.R;
import com.mdamine.movieapp.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PopularMoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PopularMoviesFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    private List<Movie> listMovies = new ArrayList<>();

    public PopularMoviesFragment() {
        // Required empty public constructor
    }


    public static PopularMoviesFragment newInstance(String param1, String param2) {
        PopularMoviesFragment fragment = new PopularMoviesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=ffb70bf9fdca2d2bc60c3292f414c479",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJsonResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);

    }

    private void parseJsonResponse(JSONObject response) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (response == null || response.length() == 0) {
            return;
        }

        try {
            JSONArray movies = response.getJSONArray("results");

            for (int i = 0; i < movies.length(); i++) {
                JSONObject movie = movies.getJSONObject(i);

                Log.i("tag", "loop " + i);
                Long id = movie.getLong("id");
                String title = movie.getString("title");
                String date = movie.getString("release_date");
                Date releaseDate = dateFormat.parse(date);
                String vote_average = movie.getString("vote_average");
                String poster_path = movie.getString("poster_path");


                listMovies.add(new Movie(id, title, releaseDate, vote_average, poster_path));

            }

            Log.i("tag", "parse finish");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Toast.makeText(getActivity(), listMovies.size() + " movies", Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        setUpRecyclerView(view);


        return view;
    }


    private void setUpRecyclerView(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        RecyclerAdapter adapter = new RecyclerAdapter(getActivity(), listMovies);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getActivity());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
    }

}

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    List<Movie> mData;
    private LayoutInflater inflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;

    public RecyclerAdapter(Context context, List<Movie> data) {
        inflater = LayoutInflater.from(context);
        this.mData = data;

        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_popular_movies, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie current = mData.get(position);
        holder.setData(current, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView vote;
        TextView date;
        ImageView poster;
        int position;
        Movie current;

        DateFormat dateFormat = new SimpleDateFormat("yyyy");

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvTitle);
            vote = (TextView) itemView.findViewById(R.id.tvVote);
            date = (TextView) itemView.findViewById(R.id.tvDate);
            poster = (ImageView) itemView.findViewById(R.id.img_poster);
        }

        public void setData(Movie current, int position) {
            this.title.setText(current.getTitle());
            this.vote.setText(current.getVote_average());
            this.date.setText(dateFormat.format(current.getReleaseDate()).toString());
            this.position = position;
            this.current = current;

            imageLoader.get("http://image.tmdb.org/t/p/w185/" + current.getPoster_path(), new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    poster.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }
}
