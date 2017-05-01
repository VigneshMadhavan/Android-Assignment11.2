package com.example.vimadhavan.assignment112;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by vimadhavan on 5/1/2017.
 */

public class MyRecycleAdapter extends RecyclerView.Adapter<ItemViewHolder> implements LoaderListener {

    private ArrayList<Item> items;
    private LoaderListener listener;

    public MyRecycleAdapter(ArrayList<Item> items,LoaderListener listener) {
        this.items = items;
        this.listener=listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, null);

        ItemViewHolder viewHolder = new ItemViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.titleTxt.setText(items.get(position).getTitle());
        holder.descTxt.setText(items.get(position).getDesc());
        items.get(position).setHolder(holder);
        if(items.get(position).getReadyForLoad()){
            DownloadFileFromURL downloadFile=new DownloadFileFromURL(listener,holder);
            downloadFile.execute(items.get(position).getImageURL());
            items.get(position).setReadyForLoad(false);
        }

    }



    public void loadImage(int position){
        if(items.get(position).getHolder()!=null) {
            DownloadFileFromURL downloadFile = new DownloadFileFromURL(this, items.get(position).getHolder());
            downloadFile.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, items.get(position).getImageURL());
        }else{
            items.get(position).setReadyForLoad(true);
        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onProgress(LoadProgres progres) {

    }

    @Override
    public void onLoaded(Bitmap bitmap) {
        listener.onLoaded(bitmap);
    }

    @Override
    public void onSaved(String filePath) {

    }

    @Override
    public void onStartLoading() {

    }

    @Override
    public void onError(String msg) {

    }



}
