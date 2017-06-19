package com.example.user.nehaber;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.nehaber.models.Category;
import com.example.user.nehaber.models.News;
import com.example.user.nehaber.models.Newses;

import com.nostra13.universalimageloader.core.ImageLoader;
import java.net.URL;
import java.util.UUID;

import eu.erikw.PullToRefreshListView;


public class NewsListActivity extends Activity {

    Bundle bnd;
    private Newses newsList;
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        bnd = new Bundle();

        Bundle alinan = getIntent().getExtras();
        String name = alinan.getString("name");
        String url = alinan.getString("url");
        String id = alinan.getString("id");

        category = new Category();


        category.setId(UUID.fromString(id));
        category.setName(name);
        category.setUrl(url);

        new GetNewsesTask().execute();

    }


    private class MyListAdapter extends ArrayAdapter<News> {
        public MyListAdapter(){
            super(NewsListActivity.this, R.layout.item_news, newsList);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            View itemView = convertView;
            if (itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.item_news, parent, false);
            }

            News currentNews = newsList.get(position);

            ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
            ImageLoader.getInstance().displayImage(currentNews.getImage(), imageView);


            TextView isimText = (TextView)itemView.findViewById(R.id.name_textView);
            isimText.setText(currentNews.getTitle());

            TextView descText = (TextView)itemView.findViewById(R.id.desc_textView);
            descText.setText(currentNews.getDescription());

            TextView dateText = (TextView)itemView.findViewById(R.id.date_textView);
            dateText.setText(currentNews.getDate());

            return itemView;
        }
    }




    private class GetNewsesTask extends AsyncTask<URL, Integer, Newses> {
        protected Newses doInBackground(URL... urls) {

            newsList = Request.getNews(category, SharedPreferencesUtil.getNewses(NewsListActivity.this, category.getId().toString()));

            SharedPreferencesUtil.setNewses(NewsListActivity.this, newsList, category.getId().toString()); //newses � SharedPreferences a kaydettik
            return newsList;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Newses result) {
            newsList = result;

            ArrayAdapter<News> adapter = new MyListAdapter();
            final PullToRefreshListView list = (PullToRefreshListView)findViewById(R.id.listView);
            list.setAdapter(adapter);

            list.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new GetNewsesTask().execute();
                    list.onRefreshComplete();
                }
            });

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(NewsListActivity.this, NewsActivity.class);
                    bnd.putString("image", newsList.get(position).getImage());
                    bnd.putString("title", newsList.get(position).getTitle());
                    bnd.putString("desc", newsList.get(position).getDescription());
                    bnd.putString("pudate", newsList.get(position).getDate());
                    intent.putExtras(bnd);
                    startActivity(intent);
                }
            });
        }
    }
}

