package com.example.user.nehaber;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.nehaber.models.Categories;
import com.example.user.nehaber.models.Category;
import com.example.user.nehaber.models.News;
import com.example.user.nehaber.models.Newses;
import com.example.user.nehaber.models.NewsesArrayList;
import com.nostra13.universalimageloader.core.ImageLoader;


public class SearchActivity extends Activity {

    EditText search_edit_text;
    EditText clear_focus;
    FrameLayout search_edit_text_cancel;
    String mSearchKey;
    NewsesArrayList search_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initSearchBox();

    }

    private class MyListAdapter extends ArrayAdapter<News> {
        NewsesArrayList newses;
        public MyListAdapter(NewsesArrayList newses){
            super(SearchActivity.this, R.layout.item_news, newses);
            this.newses = newses;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent){



            View itemView = convertView;
            if (itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.item_news, parent, false);
            }

            News currentNews = newses.get(position);

            String title_text = currentNews.getTitle();
            String desc_text = currentNews.getDescription();

            if(!Validations.isEmpty(mSearchKey)){

                if(title_text.toLowerCase().contains(mSearchKey.toLowerCase()))

                    title_text = title_text.replaceAll("(?i)("+mSearchKey+")", "<font color='#d94338'>$1</font>");

                if(desc_text.toLowerCase().contains(mSearchKey.toLowerCase()))

                    desc_text = desc_text.replaceAll("(?i)("+mSearchKey+")", "<font color='#d94338'>$1</font>");

            }

            ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
            ImageLoader.getInstance().displayImage(currentNews.getImage(), imageView);


            TextView isimText = (TextView)itemView.findViewById(R.id.name_textView);
            isimText.setText(Html.fromHtml(title_text));

            TextView descText = (TextView)itemView.findViewById(R.id.desc_textView);
            descText.setText(Html.fromHtml(desc_text));

            TextView dateText = (TextView)itemView.findViewById(R.id.date_textView);
            dateText.setText(currentNews.getDate());

            return itemView;
        }
    }

    private void initSearchBox() {
        search_edit_text = (EditText)findViewById(R.id.search_edit_text);
        clear_focus = (EditText)findViewById(R.id.clear_focus);

        search_edit_text_cancel = (FrameLayout)findViewById(R.id.search_edit_text_cancel);
        search_edit_text_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear_focus.requestFocus();
                keyboardHide();
                search_edit_text.setText("");
            }
        });

        search_edit_text.requestFocus();
        search_edit_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    keyboardHide();
                    setSearchTasks(search_edit_text.getText().toString());
                    return true;
                }
                return false;
            }
        });

    }

    private void setSearchTasks(String searchKey){
        mSearchKey = searchKey;
        Categories cats = SharedPreferencesUtil.getCategories(SearchActivity.this);
        Newses newses= new Newses(12000);
        search_result= new NewsesArrayList();

        for (Category category : cats) {
            newses.addAll(SharedPreferencesUtil.getNewses(SearchActivity.this, category.getId().toString()));
        }
        if(newses.size()>0){
            for (News news : newses) {
                if(news.getTitle().toLowerCase().contains(searchKey.toLowerCase()) || news.getDescription().toLowerCase().contains(searchKey.toLowerCase()))
                    search_result.add(news);
            }
        }

        final Bundle bnd = new Bundle();

        final ArrayAdapter<News> adapter = new MyListAdapter(search_result);
        final ListView list = (ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, NewsActivity.class);
                bnd.putString("image", search_result.get(position).getImage());
                bnd.putString("title", search_result.get(position).getTitle());
                bnd.putString("desc", search_result.get(position).getDescription());
                bnd.putString("pudate", search_result.get(position).getDate());
               // bnd.putString("search", search_result.get(position).getSearch());
                intent.putExtras(bnd);
                startActivity(intent);
            }
        });
    }


    private void keyboardHide() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(search_edit_text.getWindowToken(), 0);
    }
}