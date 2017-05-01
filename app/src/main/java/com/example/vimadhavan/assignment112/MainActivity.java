package com.example.vimadhavan.assignment112;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderListener,View.OnClickListener {
    private String[] imageURLArray = new String[]{
            "http://farm8.staticflickr.com/7315/9046944633_881f24c4fa_s.jpg",
            "http://farm4.staticflickr.com/3777/9049174610_bf51be8a07_s.jpg"
           };

    private RecyclerView recyclerView;
    private ArrayList<Item> myItems;
    private MyRecycleAdapter myRecycleAdapter;
    private Button downloadBtn;
    private int batch=2;
    private int loaded=0;
    private Toast toast;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadBtn =(Button) findViewById(R.id.downloadBtn);
        recyclerView= (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        downloadBtn.setOnClickListener(this);

        myItems= new ArrayList<Item>();

        for(int i=0;i<imageURLArray.length;i++){
            Item item=new Item("Title "+(i+1),"Description "+(i+1),imageURLArray[i]);
            myItems.add(item);

        }

        myRecycleAdapter=new MyRecycleAdapter(myItems,this);


        recyclerView.setAdapter(myRecycleAdapter);


        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    @Override
    public void onProgress(LoadProgres progres) {

    }

    @Override
    public void onLoaded(Bitmap bitmap) {
        //addItem(loaded);
        //onError("Loaded..."+loaded);

        addItem(batch,batch+1);
        batch++;
    }

    @Override
    public void onSaved(String filePath) {

    }

    @Override
    public void onStartLoading() {

    }

    @Override
    public void onError(String msg) {
        if(toast!=null){
            toast.cancel();
        }
        toast=Toast.makeText(MainActivity.this,msg,Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onClick(View v) {
        downloadBtn.setVisibility(View.GONE);

        addItem(loaded,batch);
    }

    private void addItem(int from,int max){

        if(max>imageURLArray.length){
            max=imageURLArray.length;
        }
        if( from<imageURLArray.length ) {
            for (int i = from; i < max; i++) {
                myRecycleAdapter.loadImage(i);
                loaded++;
            }
        }

    }
}
