package com.example.vimadhavan.assignment112;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by vimadhavan on 5/1/2017.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTxt,descTxt;
    public ImageView thumb;
    public ProgressBar progressBar;


    public ItemViewHolder(View itemView) {
        super(itemView);
        titleTxt = (TextView) itemView.findViewById(R.id.item_title);
        descTxt = (TextView) itemView.findViewById(R.id.item_dec);
        thumb=(ImageView) itemView.findViewById(R.id.thumb);
        progressBar=(ProgressBar) itemView.findViewById(R.id.progressBar);

    }
}
