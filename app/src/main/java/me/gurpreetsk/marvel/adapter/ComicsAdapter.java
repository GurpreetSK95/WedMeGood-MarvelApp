package me.gurpreetsk.marvel.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gurpreetsk.marvel.R;
import me.gurpreetsk.marvel.activity.DetailsActivity;
import me.gurpreetsk.marvel.model.Comic;

/**
 * Created by gurpreet on 14/04/17.
 */

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.MyViewHolder> {

    private List<Comic> comics;
    private Context context;


    public ComicsAdapter(Context context, List<Comic> comics) {
        this.comics = comics;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recyclerview_element, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(context.getString(R.string.key_is_comic), true);
                intent.putExtra(context.getString(R.string.key_comic),
                        comics.get(holder.getAdapterPosition()));
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, holder.imageviewComicThumbnail,
                                context.getString(R.string.transition_name));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    context.startActivity(intent, options.toBundle());
                } else {
                    context.startActivity(intent);
                }
            }
        });
        Picasso.with(context)
                .load(comics.get(holder.getAdapterPosition()).getThumbnail())
                .error(context.getResources().getDrawable(R.drawable.ic_error))
                .into(holder.imageviewComicThumbnail);
        holder.textviewComicTitle.setText(comics.get(holder.getAdapterPosition()).getTitle());
    }


    @Override
    public int getItemCount() {
        return comics.size();
    }


    public void swap(List<Comic> comics) {
        this.comics.clear();
        this.comics.addAll(comics);
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageview_recyclerview_element)
        ImageView imageviewComicThumbnail;
        @BindView(R.id.textview_recyclerview_element)
        TextView textviewComicTitle;
        @BindView(R.id.recyclerview_element)
        CardView cardView;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
