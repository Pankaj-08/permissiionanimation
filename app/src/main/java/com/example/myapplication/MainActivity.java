package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recyclerView;
    LinearLayout[] linearLayout = new LinearLayout[12];
    Map<Integer, Class<?>> map = new HashMap<Integer, Class<?>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map.put(R.id.icon1,newactivity.class);
        map.put(R.id.icon2,newactivity.class);
        map.put(R.id.icon3,newactivity.class);
        map.put(R.id.icon4,newactivity.class);
        map.put(R.id.icon5,newactivity.class);

        for (Map.Entry<Integer, Class<?>> m:map.entrySet()){
            LinearLayout l=findViewById(m.getKey());
            l.setOnClickListener(this);
        }
    }
    private void recycler()
    {
        MyListData[] myListData = new MyListData[] {
                new MyListData("E-Commerce", R.drawable.servies),
                new MyListData("Media Streaming", R.drawable.online),
                new MyListData("Social Media", R.drawable.message),
                new MyListData("Digital Payment", R.drawable.wal),
                new MyListData("Media Streaming", R.drawable.share),
                new MyListData("Social Media", R.drawable.shop),
                new MyListData("Digital Payment", R.drawable.servies),
                new MyListData("Media Streaming", R.drawable.food),
                new MyListData("Social Media", R.drawable.as),
                new MyListData("Digital Payment", R.drawable.i),
        };
        MyListAdapter adapter = new MyListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onClick(View view) {
        Intent i = new Intent(MainActivity.this,map.get(view.getId()));
        startActivity(i);

    }
}