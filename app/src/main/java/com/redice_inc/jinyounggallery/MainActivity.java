package com.redice_inc.jinyounggallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Gallery> galleryList = new ArrayList<Gallery>();
    private GalleryAdapter galleryAdapter;

    public final static String ORIGINAL_IMAGE_URL = "com.redice_inc.jinyounggallery.ORIGINAL_IMAGE_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        loadNextPage(1);

        int gridCount = 3;

        GridLayoutManager layoutManager = new GridLayoutManager(this, gridCount);
        GridItemOffsetDecoration itemOffsetDecoration = new GridItemOffsetDecoration(this, R.dimen.item_offset, gridCount, false);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.grid);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemOffsetDecoration);

        galleryAdapter = new GalleryAdapter(this, galleryList);
        galleryAdapter.setOnItemClickListener(new GalleryAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view, Gallery gallery) {
                Intent intent = new Intent(view.getContext(), GalleryFullViewActivity.class);
                intent.putExtra(ORIGINAL_IMAGE_URL, gallery.getOriginalImageUrl());
                view.getContext().startActivity(intent);
            }
        });
        recyclerView.setAdapter(galleryAdapter);
        recyclerView.addOnScrollListener(createInfiniteScrollListener(layoutManager));
    }

    private InfiniteScrollListener createInfiniteScrollListener(GridLayoutManager layoutManager) {
        return new InfiniteScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextPage(page);
            }
        };
    }

    private void loadNextPage(int page) {
        GalleryAPI galleryAPI = GalleryAPI.retrofit.create(GalleryAPI.class);
        Call<ArrayList<Gallery>> call = galleryAPI.loadGalleries(page);
        call.enqueue(new Callback<ArrayList<Gallery>>() {
            @Override
            public void onResponse(Call<ArrayList<Gallery>> call, Response<ArrayList<Gallery>> response) {
                ArrayList<Gallery> galleries = response.body();

                if (galleries.size() == 0) {
                    return;
                }

                galleryList.addAll(galleries);
                galleryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Gallery>> call, Throwable t) {
                Log.d("error", t.getMessage());
            }
        });
    }
}
