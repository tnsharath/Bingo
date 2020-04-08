package com.vintile.bingo.ui.main.game;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.vintile.bingo.R;
import com.vintile.bingo.model.Feed;
import com.vintile.bingo.util.FeedAdapterInterface;
import com.vintile.bingo.util.GameAdapterInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sharath on 2020/04/05
 **/
public class OppAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Feed> feeds = new ArrayList<>();
    private static final String TAG = "FeedAdapter";

    private final GameAdapterInterface gameAdapterInterface;

    public OppAdapter(GameAdapterInterface gameAdapterInterface) {
        this.gameAdapterInterface = gameAdapterInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_opp_box, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PostViewHolder) holder).bind(feeds.get(position));
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
        notifyDataSetChanged();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        View view;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            title = itemView.findViewById(R.id.tvNumbers);
        }

        public void bind(Feed feed) {

            if (!feed.isChecked()) {
                title.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.opp_box_selected));
            } else {
                title.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.opp_box));
            }
        }
    }
}