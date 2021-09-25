package com.nisaefendioglu.github;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    LinearLayout linearlayout;
    ImageButton github,search;
    EditText user;
    RecyclerView recyclerview;

    ApiService apiService;
    RepoAdapter repoAdapter;
    LinearLayoutManager linearLayoutManager;

    String username;

    List<Repo> repoList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        apiService = RetrofitConfig.retrofit.create(ApiService.class);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = user.getText().toString();

                call().enqueue(new Callback<List<Repo>>() {
                    @Override
                    public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {

                        repoList = response.body();

                        repoAdapter = new RepoAdapter(MainActivity.this,repoList);

                        recyclerview.setLayoutManager(linearLayoutManager);

                        recyclerview.setAdapter(repoAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<Repo>> call, Throwable t) {

                    }
                });

            }
        });

    }

    void init(){
        linearlayout = findViewById(R.id.linearlayout);
        github = findViewById(R.id.github);
        search = findViewById(R.id.search);
        user = findViewById(R.id.user);
        recyclerview = findViewById(R.id.recyclerview);

    }

    private Call<List<Repo>> call(){
        return apiService.listRepos(this.username);

    }
}