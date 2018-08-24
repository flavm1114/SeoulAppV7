package com.jotjjang.kccistc.seoulappv7;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.youtube.player.YouTubePlayer;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
                    ,YouTubePlayer.OnFullscreenListener {

    VideoListFragment videoListFragment;
    VideoFragment videoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        videoFragment = (VideoFragment)getFragmentManager().findFragmentById(R.id.video_fragment);
        videoFragment.setVideoId("CF_CsRTHziU");

        videoListFragment = (VideoListFragment)getFragmentManager().findFragmentById(R.id.list_fragment);
        videoListFragment.addVideoItem(new VideoEntry("서울 역삼동서 대형 트럭 옆으로 넘어져…강남역 방면 정체 / 연합뉴스 (Yonhapnews)"
        ,"4VY-aAGT5Sg","2018-08-20","(서울=연합뉴스) 이효석 기자 = 20일 오후 1시 25분께 서울 강남구 역삼동 르네상스호텔 사거리 강남역 방향에서 대형 화물트럭이 옆으로 넘어지면서..."
        ,"연합뉴스 Yonhapnews","https://i.ytimg.com/vi/4VY-aAGT5Sg/default.jpg"));

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            VideoFragment replaceVideoFragment = new VideoFragment();
            getFragmentManager().beginTransaction().replace(R.id.video_container,replaceVideoFragment).commit();
            replaceVideoFragment.setVideoId("9oHrFmCm45Q");
            videoFragment = replaceVideoFragment;

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            videoListFragment.addVideoItem(new VideoEntry("서울 역삼동서 대형 트럭 옆으로 넘어져…강남역 방면 정체 / 연합뉴스 (Yonhapnews)"
                    ,"4VY-aAGT5Sg","2018-08-20","(서울=연합뉴스) 이효석 기자 = 20일 오후 1시 25분께 서울 강남구 역삼동 르네상스호텔 사거리 강남역 방향에서 대형 화물트럭이 옆으로 넘어지면서..."
                    ,"연합뉴스 Yonhapnews","https://i.ytimg.com/vi/4VY-aAGT5Sg/default.jpg"));
            videoListFragment.addVideoItem(new VideoEntry("서울 역삼동서 대형 트럭 옆으로 넘어져…강남역 방면 정체 / 연합뉴스 (Yonhapnews)"
                    ,"4VY-aAGT5Sg","2018-08-20","(서울=연합뉴스) 이효석 기자 = 20일 오후 1시 25분께 서울 강남구 역삼동 르네상스호텔 사거리 강남역 방향에서 대형 화물트럭이 옆으로 넘어지면서..."
                    ,"연합뉴스 Yonhapnews","https://i.ytimg.com/vi/4VY-aAGT5Sg/default.jpg"));
            videoListFragment.addVideoItem(new VideoEntry("서울 역삼동서 대형 트럭 옆으로 넘어져…강남역 방면 정체 / 연합뉴스 (Yonhapnews)"
                    ,"4VY-aAGT5Sg","2018-08-20","(서울=연합뉴스) 이효석 기자 = 20일 오후 1시 25분께 서울 강남구 역삼동 르네상스호텔 사거리 강남역 방향에서 대형 화물트럭이 옆으로 넘어지면서..."
                    ,"연합뉴스 Yonhapnews","https://i.ytimg.com/vi/4VY-aAGT5Sg/default.jpg"));
            videoListFragment.addVideoItem(new VideoEntry("서울 역삼동서 대형 트럭 옆으로 넘어져…강남역 방면 정체 / 연합뉴스 (Yonhapnews)"
                    ,"4VY-aAGT5Sg","2018-08-20","(서울=연합뉴스) 이효석 기자 = 20일 오후 1시 25분께 서울 강남구 역삼동 르네상스호텔 사거리 강남역 방향에서 대형 화물트럭이 옆으로 넘어지면서..."
                    ,"연합뉴스 Yonhapnews","https://i.ytimg.com/vi/4VY-aAGT5Sg/default.jpg"));
            videoListFragment.addVideoItem(new VideoEntry("서울 역삼동서 대형 트럭 옆으로 넘어져…강남역 방면 정체 / 연합뉴스 (Yonhapnews)"
                    ,"4VY-aAGT5Sg","2018-08-20","(서울=연합뉴스) 이효석 기자 = 20일 오후 1시 25분께 서울 강남구 역삼동 르네상스호텔 사거리 강남역 방향에서 대형 화물트럭이 옆으로 넘어지면서..."
                    ,"연합뉴스 Yonhapnews","https://i.ytimg.com/vi/4VY-aAGT5Sg/default.jpg"));
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFullscreen(boolean b) {

    }

    private class searchTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

        }
    }
}
