package com.jotjjang.kccistc.seoulappv7;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ClipData;
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
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
                    ,YouTubePlayer.OnFullscreenListener
                    ,AbsListView.OnScrollListener {

    // 유튜브 플레이어가 밑에서 위로 슬라이딩 되는 시간 조절 상수 입니다
    public static final int ANIMATION_DURATION_MILLIS = 300;
    /** The request code when calling startActivityForResult to recover from an API service error. */
    public static final int RECOVERY_DIALOG_REQUEST = 1;

    //layout contents FOR activity, youtube
    private VideoListFragment videoListFragment;
    private VideoFragment videoFragment;
    private View mainContainer;
    private View videoContainer;
    //public static int VIDEO_CONTAINER_WIDTH;
    private View listContainer;
    private View closeButton;

    //progressbar, loading, scroll contents
    private ProgressBar loadingProgressBar;
    private boolean isScrollEnd;

    //data request contents
    AsyncTask<?,?,?> asyncTask;
    private boolean isLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //layout contents FOR activity, youtube
        videoFragment = (VideoFragment)getFragmentManager().findFragmentById(R.id.video_fragment);
        videoListFragment = (VideoListFragment)getFragmentManager().findFragmentById(R.id.list_fragment);
        mainContainer = findViewById(R.id.main_container);
        videoContainer = findViewById(R.id.video_container);
        listContainer = findViewById(R.id.list_container);
        closeButton = findViewById(R.id.close_button);
        videoContainer.setVisibility(View.GONE);
        //videoFragment.setVideo("27KAUh2c2HY");

        //progressbar, loading, scroll contents
        loadingProgressBar = findViewById(R.id.loading_progress_bar);
        loadingProgressBar.setVisibility(View.GONE);
        isScrollEnd = false;
        videoListFragment.getListView().setOnScrollListener(this);

        //data request contents
        isLoading = false;
        SearchOptionState.setDateTimeForRequest();

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



        checkYouTubeApi();
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

        setOptionState(item);

        //noinspection SimplifiableIfStatement
        if (id == R.id.setting_today) {
//            VideoFragment replaceVideoFragment = new VideoFragment();
//            getFragmentManager().beginTransaction().replace(R.id.video_container,replaceVideoFragment).commit();
//            replaceVideoFragment.setVideo("9oHrFmCm45Q");
//            videoFragment = replaceVideoFragment;
            if(isLoading == false) {
                isLoading = true;
                asyncTask = new RequestTask().execute();
            }
            return true;
        } else if (id == R.id.setting_week) {
            if(isLoading == false) {
                isLoading = true;
                asyncTask = new RequestTask().execute();
            }
            return true;
        } else if (id == R.id.setting_month) {
            if(isLoading == false) {
                isLoading = true;
                asyncTask = new RequestTask().execute();
            }
            return true;
        } else if (id == R.id.setting_year) {
            if(isLoading == false) {
                isLoading = true;
                asyncTask = new RequestTask().execute();
            }
            return true;
        } else if (id == R.id.setting_all) {
            if(isLoading == false) {
                isLoading = true;
                asyncTask = new RequestTask().execute();
            }
            return true;
        } else if (id == R.id.setting_relevance) {
            if(isLoading == false) {
                isLoading = true;
                asyncTask = new RequestTask().execute();
            }
            return true;
        } else if (id == R.id.setting_viewCount) {
            if(isLoading == false) {
                isLoading = true;
                asyncTask = new RequestTask().execute();
            }
            return true;
        } else if (id == R.id.setting_rating) {
            if(isLoading == false) {
                isLoading = true;
                asyncTask = new RequestTask().execute();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setOptionState(MenuItem item) {
        if(item.isChecked() == false) {
            item.setChecked(true);
            if (item.getGroupId() == R.id.setting_group_date) {
                switch (item.getItemId())
                {
                    case R.id.setting_today:
                        SearchOptionState.setDateState(SearchOptionState.DateState.DATE_STATE_TODAY);
                        break;
                    case R.id.setting_week:
                        SearchOptionState.setDateState(SearchOptionState.DateState.DATE_STATE_WEEK);
                        break;
                    case R.id.setting_month:
                        SearchOptionState.setDateState(SearchOptionState.DateState.DATE_STATE_MONTH);
                        break;
                    case R.id.setting_year:
                        SearchOptionState.setDateState(SearchOptionState.DateState.DATE_STATE_YEAR);
                        break;
                    case R.id.setting_all:
                        SearchOptionState.setDateState(SearchOptionState.DateState.DATE_STATE_ALL);
                        break;
                }
            } else if (item.getGroupId() == R.id.setting_group_order) {
                switch (item.getItemId())
                {
                    case R.id.setting_relevance:
                        SearchOptionState.setOrderState(SearchOptionState.OrderState.ORDER_STATE_RELEVANCE);
                        break;
                    case R.id.setting_viewCount:
                        SearchOptionState.setOrderState(SearchOptionState.OrderState.ORDER_STATE_VIEWCOUNT);
                        break;
                    case R.id.setting_rating:
                        SearchOptionState.setOrderState(SearchOptionState.OrderState.ORDER_STATE_RATING);
                        break;
                }
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_hot_clip) {
            if(isLoading == false)
            {
                isLoading = true;
                SearchOptionState.setTopicState(SearchOptionState.TopicState.TOPIC_STATE_HOTCLIP);
                asyncTask = new RequestTask().execute();
            } else
            {
                item.setChecked(false);
            }
        } else if (id == R.id.nav_news) {
            if(isLoading == false) {
                isLoading = true;
                SearchOptionState.setTopicState(SearchOptionState.TopicState.TOPIC_STATE_NEWS);
                asyncTask = new RequestTask().execute();
            } else
            {
                item.setChecked(false);
            }
        } else if (id == R.id.nav_sports) {
            if(isLoading == false) {
                isLoading = true;
                SearchOptionState.setTopicState(SearchOptionState.TopicState.TOPIC_STATE_SPORTS);
                asyncTask = new RequestTask().execute();
            } else
            {
                item.setChecked(false);
            }
        } else if (id == R.id.nav_humor) {
            if(isLoading == false) {
                isLoading = true;
                SearchOptionState.setTopicState(SearchOptionState.TopicState.TOPIC_STATE_HUMOR);
                asyncTask = new RequestTask().execute();
            } else
            {
                item.setChecked(false);
            }
        } else if (id == R.id.nav_e_sports) {

        } else if (id == R.id.nav_food) {

        } else if (id == R.id.nav_kpop) {

        } else if (id == R.id.nav_festival) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && isScrollEnd && isLoading == false) {
            // 화면이 바닦에 닿을때 처리
            // 로딩중을 알리는 프로그레스바를 보인다.
            if(isLoading == false) {
                isLoading = true;
                asyncTask = new NextTask().execute();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        isScrollEnd = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
    }

    @Override
    public void onFullscreen(boolean b) {

    }

    // 비디오 플레이어 닫기 버튼 누를시 호출 됩니다
    public void onClickClose(@SuppressWarnings("unused") View view) {
        videoListFragment.getListView().clearChoices();
        videoFragment.pauseVideo();
        ViewPropertyAnimator animator = videoContainer.animate()
                .translationXBy(videoContainer.getWidth())
                .setDuration(ANIMATION_DURATION_MILLIS);
        runOnAnimationEnd(animator, new Runnable() {
            @Override
            public void run() {
                videoContainer.setVisibility(View.GONE);
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

    private class RequestTask extends AsyncTask<Void, Void, Void> {
        ArrayList<VideoEntry> resultList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                 MyJsonParser.parseJsonData(resultList,
                        MyJsonParser.getYoutubeData(
                                SearchOptionState.getTopicStateString()
                                , SearchOptionState.getBeforeDateStateString()
                                , SearchOptionState.getAfterDateStateString()
                                , SearchOptionState.getOrderStateString()
                                , 10));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            videoListFragment.clearVideoEntries();
            videoListFragment.addVideoEntries(resultList);
            loadingProgressBar.setVisibility(View.GONE);
            isLoading = false;
        }
    }

    private class NextTask extends AsyncTask<Void, Void, Void> {
        ArrayList<VideoEntry> resultList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                MyJsonParser.parseJsonData(resultList,
                        MyJsonParser.getYoutubeData(
                                SearchOptionState.getTopicStateString()
                                , SearchOptionState.getBeforeDateStateString()
                                , SearchOptionState.getAfterDateStateString()
                                , SearchOptionState.getOrderStateString()
                                , 5, SearchOptionState.getNextPageToken()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            videoListFragment.addVideoEntries(resultList);
            loadingProgressBar.setVisibility(View.GONE);
            isLoading = false;
        }
    }

    private class JotJJangTask extends AsyncTask<Void, Void, Void> {
        ArrayList<VideoEntry> resultList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                MyJsonParser.parseJsonData(resultList,
                        MyJsonParser.getYoutubeData(
                                SearchOptionState.getTopicStateString()
                                , SearchOptionState.getBeforeDateStateString()
                                , SearchOptionState.getAfterDateStateString()
                                , SearchOptionState.getOrderStateString()
                                , 5, SearchOptionState.getNextPageToken()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //videoListFragment.addVideoEntries(resultList);
            loadingProgressBar.setVisibility(View.GONE);
            isLoading = false;
        }
    }
}
