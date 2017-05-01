package com.example.vimadhavan.assignment112;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

/**
 * Created by vimadhavan on 4/24/2017.
 */

public class DownloadFileFromURL extends AsyncTask<String,LoadProgres,String> {

    private LoaderListener listener;
    private Bitmap bmp=null;
    private ItemViewHolder viewHolder;

    public DownloadFileFromURL(LoaderListener listener,ItemViewHolder viewHolder) {
        this.listener = listener;
        this.viewHolder=viewHolder;
    }

    @Override
    protected String doInBackground(String... f_url) {

        int count;

        try {
            URL url = new URL(f_url[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
            long lenghtOfFile = conection.getContentLength();
            String type=conection.getContentType();
            Log.d("getContentType..",type);


            InputStream input = new BufferedInputStream(url.openStream());

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {

                if (isCancelled()) {
                    Log.i("DownloadTask","Cancelled");
                    input.close();
                    return "Cancelled";
                }else{
                    total += count;
                    LoadProgres progressData=new LoadProgres();
                    progressData.setTotalSize((float) (lenghtOfFile*1.00));
                    progressData.setLoaded((float) (total*10.00));
                    publishProgress(progressData);


                }

            }

            Log.d("Completed::", lenghtOfFile+"::::::::::::"+String.valueOf(total));

            input.close();

            try {
                // Download Image from URL
                InputStream input1 = new URL(f_url[0]).openStream();
                // Decode Bitmap
                bmp = BitmapFactory.decodeStream(input1);
            } catch (Exception e) {
                e.printStackTrace();
                return  e.getMessage();
            }


        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            return "Error: "+e.getMessage();
        }
        return null;
    }

    private String getStringSizeLengthFile(long size) {

        DecimalFormat df = new DecimalFormat("0.00");

        float sizeKb = 1024.0f;
        float sizeMo = sizeKb * sizeKb;
        float sizeGo = sizeMo * sizeKb;
        float sizeTerra = sizeGo * sizeKb;


        if(size < sizeMo)
            return df.format(size / sizeKb)+ " Kb";
        else if(size < sizeGo)
            return df.format(size / sizeMo) + " Mo";
        else if(size < sizeTerra)
            return df.format(size / sizeGo) + " Go";

        return "";
    }

    @Override
    protected void onProgressUpdate(LoadProgres... progres) {
        super.onProgressUpdate(progres);

        Log.d("onProgressUpdate::", "::::::::::::"+progres[0].getPercentage());
        this.listener.onProgress(progres[0]);
        this.viewHolder.progressBar.setProgress((int) progres[0].getPercentage());
        this.viewHolder.descTxt.setVisibility(View.GONE);
        this.viewHolder.progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onPostExecute(String errorMsg) {

        super.onPostExecute(errorMsg);
        this.viewHolder.descTxt.setVisibility(View.VISIBLE);
        this.viewHolder.progressBar.setVisibility(View.GONE);

        if(errorMsg==null){
            if(bmp!=null){
                this.listener.onLoaded(bmp);
                this.viewHolder.thumb.setImageBitmap(bmp);
            }else{
                listener.onError("Image file is not in proper format");
            }

        }else{
            listener.onError(errorMsg);
        }

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onStartLoading();
    }
}
