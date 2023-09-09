package com.codsoft.quotegenerator;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private List<Integer> listQuotes = new ArrayList<>();
    private int quoteNumber = 0;
    private String mainText = "";
    private TextView tvText;
    private FloatingActionButton fabNewQuote;
    private RecyclerView recyclerView;
    private List<Integer> favoriteQuotes = new ArrayList<>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvText = findViewById(R.id.quoteText);
        fabNewQuote = findViewById(R.id.fab);

        favoriteQuotes = new ArrayList<>();

        listQuotes.add(R.string.quote_1);
        listQuotes.add(R.string.quote_2);
        listQuotes.add(R.string.quote_3);
        listQuotes.add(R.string.quote_4);
        listQuotes.add(R.string.quote_5);
        listQuotes.add(R.string.quote_6);
        listQuotes.add(R.string.quote_7);
        listQuotes.add(R.string.quote_8);
        listQuotes.add(R.string.quote_9);
        listQuotes.add(R.string.quote_10);
        listQuotes.add(R.string.quote_11);
        listQuotes.add(R.string.quote_12);
        listQuotes.add(R.string.quote_13);
        listQuotes.add(R.string.quote_14);
        listQuotes.add(R.string.quote_15);
        listQuotes.add(R.string.quote_16);
        listQuotes.add(R.string.quote_17);
        listQuotes.add(R.string.quote_18);
        listQuotes.add(R.string.quote_19);
        listQuotes.add(R.string.quote_20);

        quoteOnAppLoaded();
        clickNewQuote();

        Button favButton = findViewById(R.id.favBtn);
        final boolean[] isFilled = {false};
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (quoteNumber > 0 && quoteNumber <= listQuotes.size()) {
            isFilled[0] = favoriteQuotes.contains(listQuotes.get(quoteNumber - 1));
        }

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFilled[0]) {
                    favButton.setBackgroundResource(R.drawable.round_favorite_border_24);
                    isFilled[0] = false;

                    if (quoteNumber > 0 && quoteNumber <= listQuotes.size()) {
                        favoriteQuotes.remove(Integer.valueOf(listQuotes.get(quoteNumber - 1)));
                        saveFavoriteQuotesToSharedPreferences(favoriteQuotes);
                        Toast.makeText(MainActivity.this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    favButton.setBackgroundResource(R.drawable.filled_button_background);
                    isFilled[0] = true;

                    if (quoteNumber > 0 && quoteNumber <= listQuotes.size()) {
                        favoriteQuotes.add(listQuotes.get(quoteNumber - 1));
                        saveFavoriteQuotesToSharedPreferences(favoriteQuotes);
                        Toast.makeText(MainActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        restoreFavoriteQuotesFromSharedPreferences();
    }

    private void saveFavoriteQuotesToSharedPreferences(List<Integer> favoriteQuotes) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        StringBuilder favorites = new StringBuilder();
        for (int quote : favoriteQuotes) {
            favorites.append(quote).append(",");
        }
        editor.putString("favoriteQuotes", favorites.toString());
        editor.apply();
    }

    private List<Integer> restoreFavoriteQuotesFromSharedPreferences() {
        List<Integer> favoriteQuotes = new ArrayList<>();
        String favoritesString = sharedPreferences.getString("favoriteQuotes", "");
        String[] favoritesArray = favoritesString.split(",");
        for (String favorite : favoritesArray) {
            if (!favorite.isEmpty()) {
                favoriteQuotes.add(Integer.parseInt(favorite));
            }
        }
        return favoriteQuotes;
    }

    private void clickNewQuote() {
        fabNewQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabNewQuote.setEnabled(false);

                if (quoteNumber == listQuotes.size()) {
                    quoteOnAppLoaded();
                } else {
                    typeText(getString(listQuotes.get(quoteNumber)));
                    ++quoteNumber;
                }
            }
        });
    }

    private void quoteOnAppLoaded() {
        fabNewQuote.setEnabled(false);
        quoteNumber = 0;
        Collections.shuffle(listQuotes);
        typeText(getString(listQuotes.get(quoteNumber)));
        ++quoteNumber;
    }

    private void typeText(String text) {
        mainText = "";
        final long textDelay = 50L;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder sb = new StringBuilder();
                String updatedText = "";

                for (int i = 0; i < text.length(); i++) {
                    mainText = sb.append(updatedText + text.charAt(i)).toString();
                    try {
                        Thread.sleep(textDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        Handler handler = new Handler();
        Log.d("Main", "Handler started");

        Runnable runnable = new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                tvText.setText(mainText + "|");
                handler.postDelayed(this, 10);

                if (text.equals(mainText)) {
                    handler.removeCallbacks(this);
                    tvText.setText(mainText);
                    fabNewQuote.setEnabled(true);
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_share) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, mainText);
            startActivity(Intent.createChooser(shareIntent, "Share this quote!"));
            return true;
        } if (item.getItemId() == R.id.nav_fav) {
            Intent intent = new Intent(MainActivity.this, FavoriteQuotesActivity.class);
            intent.putIntegerArrayListExtra("favoriteQuotes", new ArrayList<>(favoriteQuotes)); // Pass favoriteQuotes as an intent extra
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
