package com.example.jia.news.Activity.Activivty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jia.news.Activity.CollectionData;
import com.example.jia.news.Activity.adapter.CollectionAdapter;
import com.example.jia.news.R;

import java.util.ArrayList;

public class CollectionActivity extends AppCompatActivity {
    private RecyclerView recyclerView_collection;
    private ArrayList<CollectionData> mCollection=new ArrayList<>();
    private CollectionAdapter mCollectionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initGetCollection();
        initViews();
    }

    private void initGetCollection() {
        Intent getCollection=getIntent();
        mCollection= (ArrayList<CollectionData>) getCollection.getSerializableExtra("mCollectionDataList");
    }

    private void initViews() {
        recyclerView_collection= (RecyclerView) findViewById(R.id.recyclerView_collection);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView_collection.setLayoutManager(layoutManager);
        mCollectionAdapter=new CollectionAdapter(mCollection);
        recyclerView_collection.setAdapter(mCollectionAdapter);

    }
}
