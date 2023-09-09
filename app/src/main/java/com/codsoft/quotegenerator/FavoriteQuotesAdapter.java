package com.codsoft.quotegenerator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoriteQuotesAdapter extends RecyclerView.Adapter<FavoriteQuotesAdapter.ViewHolder> {

    private Context context;
    private List<Integer> favoriteQuotes;

    public FavoriteQuotesAdapter(Context context, List<Integer> favoriteQuotes) {
        this.context = context;
        this.favoriteQuotes = favoriteQuotes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_quote_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int quoteResourceId = favoriteQuotes.get(position);
        String quoteText = context.getString(quoteResourceId);
        holder.quoteTextView.setText(quoteText);
    }

    @Override
    public int getItemCount() {
        return favoriteQuotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView quoteTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            quoteTextView = itemView.findViewById(R.id.favoriteQuoteText);
        }
    }
}
