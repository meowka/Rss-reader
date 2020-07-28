package com.example.test.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Description;
import com.example.test.Interface.ItemClickListener;
import com.example.test.Model.RSSObject;
import com.example.test.R;
import com.squareup.picasso.Picasso;

class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    TextView txtTitle;
    ImageView imgNews;
    TextView txtInfo;
    private ItemClickListener itemClickListener;

    public FeedViewHolder(@NonNull View itemView) {
        super(itemView);

        txtTitle = itemView.findViewById(R.id.txtTitle);
        txtInfo = itemView.findViewById(R.id.txtInfo);
        imgNews = itemView.findViewById(R.id.imgNews);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), true);
        return false;
    }
}

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private RSSObject rssObject;
    private Context context;
    private LayoutInflater inflater;

    public FeedAdapter(RSSObject rssObject, Context context) {
        this.rssObject = rssObject;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.row, parent, false);
        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, final int position) {

        holder.txtTitle.setText(rssObject.getItems().get(position).getTitle());
        holder.txtInfo.setText(rssObject.getItems().get(position).getAuthor());
        Picasso.get().load(rssObject.getItems().get(position).getThumbnail().replace("http", "https")).into((holder.imgNews));

//        Вместо replace можжно использовать AndroidManifest.xml там, мы разрешаем все http для всех запросов

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int porition, boolean isLongClick) {
                if (!isLongClick) {
                    Intent intent = new Intent(context, Description.class);
                    intent.putExtra("news", rssObject.getItems().get(porition).getDescription());
                    intent.putExtra("newsTitle", rssObject.getItems().get(position).getTitle());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rssObject.items.size();
    }
}
