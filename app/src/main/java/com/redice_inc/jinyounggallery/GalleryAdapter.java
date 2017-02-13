package com.redice_inc.jinyounggallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by redice on 2/10/17.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private ArrayList<Gallery> galleries;
    private Context mContext;
    private ClickListener mClickListener;

    public GalleryAdapter(Context context, ArrayList<Gallery> galleries) {
        mContext = context;
        this.galleries = galleries;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView nameView;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            imageView = (ImageView) view.findViewById(R.id.image);
            nameView = (TextView) view.findViewById(R.id.name);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Gallery gallery = galleries.get(position);
            mClickListener.onItemClick(position, view, gallery);
        }
    }

    public interface ClickListener {
        public void onItemClick(int position, View view, Gallery gallery);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_gallery_image, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryAdapter.ViewHolder viewHolder, int i) {
        Gallery gallery = galleries.get(i);

        Glide.clear(viewHolder.imageView);
        Glide.with(viewHolder.imageView.getContext()).load(gallery.getImageUrl())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.imageView);
        viewHolder.nameView.setText(gallery.getName());
    }

    @Override
    public int getItemCount() {
        return galleries.size();
    }
}