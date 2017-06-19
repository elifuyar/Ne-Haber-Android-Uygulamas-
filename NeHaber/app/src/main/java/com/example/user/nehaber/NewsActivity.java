package com.example.user.nehaber;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;


public class NewsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Bundle alinan = getIntent().getExtras();
        String image = alinan.getString("image");
        String title = alinan.getString("title");
        String puDate = alinan.getString("pudate");
        String description = alinan.getString("desc");
        //String search = (String) alinan.get("search");

       /* if(!Validations.isEmpty(search)){

            if(description.toLowerCase().contains(search.toLowerCase()))

                description = description.replaceAll("(?i)("+search+")", "<font color='#d94338'>$1</font>");

            if(title.toLowerCase().contains(search.toLowerCase()))

                title = title.replaceAll("(?i)("+search+")", "<font color='#d94338'>$1</font>");

        }*/


        ImageView iconn = (ImageView)findViewById(R.id.image_view);
        ImageLoader.getInstance().displayImage(image, iconn);

        TextView titlee = (TextView)findViewById(R.id.baslik_textView);
        titlee.setText(title);

        TextView  datee = (TextView)findViewById(R.id.tarih_textView);
        datee.setText(puDate);

        TextView  desc = (TextView)findViewById(R.id.detay_textView);
        desc.setText(description);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
