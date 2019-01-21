package com.example.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<GitHubRepo> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        GitHubClient client = retrofit.create(GitHubClient.class);
        Call<List<GitHubRepo>> call = client.reposForUsers("guptavikash46");

        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                List<GitHubRepo> repos = response.body();
                arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1
                , repos);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something's wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
