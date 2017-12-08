package com.example.android.moviep1;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    public ImageView imageView;
    public Button button;
    public static String BASE_URL = "https://api.themoviedb.org";
    public static String URL_IMG = "https://image.tmdb.org/t/p/w185";
    public static int PAGE = 1;
    public static String API_KEY = "c7720ac64a6580dc890bb503e5f55335";
    public static String CATEGORY = "popular";
    public Button b_prev, b_next;

    public int i = 0;
    String imeFilma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        b_prev = (Button) findViewById (R.id.b_prev);
        b_next = (Button) findViewById (R.id.b_next);
        textView = (TextView) findViewById (R.id.my_tv);
        final ImageView imageView = (ImageView) findViewById (R.id.imageView);

        //RETROFIT
        Retrofit retrofit = new Retrofit.Builder ()
                .baseUrl (BASE_URL)
                .addConverterFactory (GsonConverterFactory.create ())
                .build ();
        ApiInterface myInterface = retrofit.create (ApiInterface.class);
        Call<MovieResult> call = myInterface.getMovies (CATEGORY, API_KEY, PAGE);

        call.enqueue (new Callback<MovieResult> () {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                MovieResult result = response.body ();
                final List<Result> listOfMovies = result.getResults ();
                //ono sto se pojavi na prvom izboru
                final Result firstMovie = listOfMovies.get (0);
                textView.setText (firstMovie.getTitle ());
                String image_url = URL_IMG + firstMovie.getPosterPath ();
                Picasso.with (MainActivity.this)
                        .load (image_url)
                        .into (imageView);

                //first button
                b_prev.setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        if (i > 0) {
                            i--;
                            Result firstMovie = listOfMovies.get (i);
                            imeFilma = firstMovie.getTitle ();
                            textView.setText (firstMovie.getTitle ());
                            String image_url = URL_IMG + firstMovie.getPosterPath ();
                            Picasso.with (MainActivity.this)
                                    .load (image_url)
                                    .into (imageView);

                        }
                    }
                });
                //second button
                b_next.setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        if (i < 100 - 1) {
                            i++;
                            Result firstMovie = listOfMovies.get (i);
                            textView.setText (firstMovie.getTitle ());
                            String image_url = URL_IMG + firstMovie.getPosterPath ();
                            Picasso.with (MainActivity.this)
                                    .load (image_url)
                                    .into (imageView);
                        }
                    }
                });
        //      Toast.makeText(MainActivity.this,i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                t.printStackTrace ();
            }
        });
    }
}
