package com.wh.dm.activity;

import com.wh.dm.R;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class CollectMainActivity extends TabActivity{
	
	 private ImageButton btnBack;
	 public static ImageButton btnDel;
	 
   @Override
   public void onCreate(Bundle savedInstanceState){
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.activity_collect_tabhost);
	   
	   btnDel = (ImageButton) findViewById(R.id.DeleteButton);
	   
	   TabHost tabHost=getTabHost();
	   
	   tabHost.addTab(tabHost.newTabSpec("news").setIndicator(createTabView(0))
			   .setContent(new Intent(this,CollectNewsActivity.class)));
	   tabHost.addTab(tabHost.newTabSpec("photo").setIndicator(createTabView(1))
			   .setContent(new Intent(this,CollectPhotoActivity.class)));
	   
	   btnBack = (ImageButton) findViewById(R.id.BackButton);
       btnBack.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {

               finish();

           }
       });

   }
   
   
   private View createTabView(int tab) {

       View view = LayoutInflater.from(this).inflate(R.layout.tab_widget, null);
       TextView tv = (TextView) view.findViewById(R.id.txt_tab_widget);
       ImageView iv = (ImageView) view.findViewById(R.id.img_tab_widget);
       switch (tab) {
           case 0:
               iv.setImageResource(R.drawable.tab_news);
               tv.setText(getResources().getString(R.string.news));
               break;
           case 1:
               iv.setImageResource(R.drawable.tab_potos);
               tv.setText(getResources().getString(R.string.photo));
               break;
       }

       return view;
   }
}
