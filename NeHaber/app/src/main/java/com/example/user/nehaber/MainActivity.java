package com.example.user.nehaber;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.user.nehaber.models.Categories;
import com.example.user.nehaber.models.Category;

import java.util.UUID;


public class MainActivity extends Activity {

    String[] urls = {"ensonhaber","gundem","politika","ekonomi","dunya","saglik","otomobil","kultur-sanat", "teknoloji","medya","yasam","spor"};
    String[] category_names = {"Son Haberler","G�ndem","Politika","Ekonomi","D�nya","Sa�l�k","Otomobil","K�lt�r Sanat","Teknoloji","Medya","Ya�am","Spor"};
    ViewGroup categories_lay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Categories categories = new Categories();

        if (SharedPreferencesUtil.isFirstRun(MainActivity.this)){//sadece ilk �al��ma an�nda olacak olanlar

            for (int i = 0; i < urls.length; i++) {

                Category cat = new Category();
                cat.setName(category_names[i]);
                cat.setUrl("http://www.ensonhaber.com/rss/" + urls[i] + ".xml");
                cat.setId(UUID.randomUUID());
                categories.add(cat);
            }
            SharedPreferencesUtil.setCategories(MainActivity.this, categories);//Categories i sharedPreference kaydettik
        }

        categories = SharedPreferencesUtil.getCategories(MainActivity.this);//SharedPreference ye kaydettigimiz veriyi categories e aktard�k

        categories_lay = (ViewGroup) findViewById(R.id.categories_lay);
        categories_lay.removeAllViews();
        int[] cat_colors = getResources().getIntArray(R.array.category_colors);

        for (int i = 0; i < categories.size(); i++) {

            addCategoryToLayout(cat_colors[i], categories.get(i));
        }

        FrameLayout arama = (FrameLayout)findViewById(R.id.arama_Frame);

        arama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);

            }
        });
    }

    private void addCategoryToLayout(int color, Category cat) {

        View v = getLayoutInflater().inflate(R.layout.item_category, null);
        View layout = v.findViewById(R.id.categoryback);
        TextView title = (TextView) v.findViewById(R.id.category_name);
        layout.setBackgroundColor(color);
        title.setText(cat.getName());
        v.setTag(cat);
        v.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Category cat = (Category) v.getTag();
                Bundle bnd = new Bundle();

                Intent intent = new Intent(MainActivity.this, NewsListActivity.class);

                bnd.putString("name", cat.getName());
                bnd.putString("url", cat.getUrl());
                bnd.putString("id", cat.getId().toString());

                intent.putExtras(bnd);
                startActivity(intent);
            }
        });

        categories_lay.addView(v);

    }
}
