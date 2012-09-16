package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.widget.MutiItemAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;


public class DM_ImgActivity extends Activity {
  
    private ListView lv_dm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dm_img);
        
        lv_dm =(ListView)findViewById(R.id.lv_img); 
        MutiItemAdapter adapter = new MutiItemAdapter(DM_ImgActivity.this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View header = inflater.inflate(R.layout.dm_img_header, null);
        lv_dm.addHeaderView(header);
        
        for(int i=0;i<10;i++){
            if(i%2==0){
                adapter.addItemType2("item"+i);
            }else{
                adapter.addItemType1("item"+i);
            }
        }
        lv_dm.setAdapter(adapter);
        
       
    }
}