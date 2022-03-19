package com.example.starsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.StatefulAdapter;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.starsapplication.adapter.StarAdapter;
import com.example.starsapplication.beans.Star;
import com.example.starsapplication.service.StarService;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private List<Star> stars;
    private RecyclerView recyclerView;
    StarAdapter starAdapter = null;
    private StarService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stars = new ArrayList<>();
        service = StarService.getInstance();
        init();
        recyclerView = findViewById(R.id.recycle_view);
        starAdapter = new StarAdapter(this, service.findAll());
        recyclerView.setAdapter(starAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void init(){
        service.create(new Star("Courteney Cox", "https://pbs.twimg.com/profile_images/1491471803893444608/t2bkDjvr_400x400.jpg", 3.5f));
        service.create(new Star("Jennifer Aniston", "https://cache.cosmopolitan.fr/data/photo/w2000_ci/1jw/look-rachel-green-friends.webp", 3));
        service.create(new Star("Lisa Kudrow", "https://upload.wikimedia.org/wikipedia/en/f/f6/Friendsphoebe.jpg", 5));
        service.create(new Star("David Schwimmer", "https://www.thelist.com/img/gallery/things-friends-fans-never-noticed-about-ross-geller/intro-1621874931.webp", 1));
        service.create(new Star("Matt LeBlanc", "https://static-koimoi.akamaized.net/wp-content/new-galleries/2020/05/friends-trivia-18-matt-leblanc-aka-joey-tribbiani-auditioned-with-just-11-got-himself-this-after-his-first-paycheck-001.jpg", 5));
        service.create(new Star("Matthew Perry", "https://pyxis.nymag.com/v1/imgs/079/792/3ed0d94be0a9bd3d023f00532889bab152-30-chandler-bing.2x.rsquare.w330.jpg", 1));
    }
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView)
                menuItem.getActionView();
        searchView.setOnQueryTextListener(new
                                                  SearchView.OnQueryTextListener() {
                                                      @Override
                                                      public boolean onQueryTextSubmit(String query) {
                                                          return true;
                                                      }
                                                      @Override
                                                      public boolean onQueryTextChange(String newText) {
                                                          if (starAdapter != null){
                                                              starAdapter.getFilter().filter(newText);
                                                          }
                                                          return true;

                                                      }
                                                  });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.share){
            String txt = "Stars";
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Stars")
                    .setText(txt)
                    .startChooser();
        }
        return super.onOptionsItemSelected(item);
    }


}