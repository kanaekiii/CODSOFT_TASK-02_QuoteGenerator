package com.codsoft.quotegenerator;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteQuotesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoriteQuotesAdapter adapter;
    private List<Integer> favoriteQuotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_quotes);

        recyclerView = findViewById(R.id.favoriteQuotesRecyclerView);

        favoriteQuotes = getIntent().getIntegerArrayListExtra("favoriteQuotes");

        if (favoriteQuotes == null) {
            favoriteQuotes = new ArrayList<>();
        }

        adapter = new FavoriteQuotesAdapter(this, favoriteQuotes);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
