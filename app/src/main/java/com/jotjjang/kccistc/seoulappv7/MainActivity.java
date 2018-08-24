package com.jotjjang.kccistc.seoulappv7;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
                    ,YouTubePlayer.OnFullscreenListener {

    // 유튜브 플레이어가 밑에서 위로 슬라이딩 되는 시간 조절 상수 입니다
    public static final int ANIMATION_DURATION_MILLIS = 300;
    /** The request code when calling startActivityForResult to recover from an API service error. */
    public static final int RECOVERY_DIALOG_REQUEST = 1;

    VideoListFragment videoListFragment;
    VideoFragment videoFragment;
    View mainContainer;
    View videoContainer;
    View closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        videoFragment = (VideoFragment)getFragmentManager().findFragmentById(R.id.video_fragment);
        videoFragment.setVideo("CF_CsRTHziU");
        mainContainer = findViewById(R.id.main_container);
        videoContainer = findViewById(R.id.video_container);
        closeButton = findViewById(R.id.close_button);
        //videoContainer.setVisibility(View.GONE);

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
            replaceVideoFragment.setVideo("9oHrFmCm45Q");
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

    // 비디오 플레이어 닫기 버튼 누를시 호출 됩니다
    public void onClickClose(@SuppressWarnings("unused") View view) {
        videoListFragment.getListView().clearChoices();
        videoFragment.pauseVideo();
        ViewPropertyAnimator animator = mainContainer.animate()
                .translationYBy(-videoContainer.getHeight())
                .setDuration(ANIMATION_DURATION_MILLIS);
        runOnAnimationEnd(animator, new Runnable() {
            @Override
            public void run() {
                videoContainer.setVisibility(View.VISIBLE);
            }
        });
    }
    @TargetApi(16)
    private void runOnAnimationEnd(ViewPropertyAnimator animator, final Runnable runnable) {
        if (Build.VERSION.SDK_INT >= 16) {
            animator.withEndAction(runnable);
        } else {
            animator.setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    runnable.run();
                }
            });
        }
    }

    // Utility methods for layouting.

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }

    private static void setLayoutSize(View view, int width, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    private static void setLayoutSizeAndGravity(View view, int width, int height, int gravity) {
        ViewGroup.LayoutParams params = view.getLayoutParams();

        params.width = width;
        params.height = height;
        //params.gravity = gravity;
        view.setLayoutParams(params);
    }

    //유튜브 api 상태 체크
    private void checkYouTubeApi() {
        YouTubeInitializationResult errorReason =
                YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this);
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else if (errorReason != YouTubeInitializationResult.SUCCESS) {
            String errorMessage =
                    String.format(getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
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
