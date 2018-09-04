package com.jotjjang.kccistc.seoulappv7;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
                    ,AbsListView.OnScrollListener {

    // 유튜브 플레이어가 밑에서 위로 슬라이딩 되는 시간 조절 상수 입니다
    public static final int ANIMATION_DURATION_MILLIS = 300;
    /** The request code when calling startActivityForResult to recover from an API service error. */
    public static final int RECOVERY_DIALOG_REQUEST = 1;

    //layout contents FOR activity, youtube
    private VideoListFragment videoListFragment;
    private VideoFragment videoFragment;
    private CommentFragment commentFragment;
    private View mainContainer;
    private View videoContainer;
    private View listContainer;
    private View closeButton;

    //progressbar, loading, scroll contents
    private ProgressBar loadingProgressBar;
    private boolean isScrollEnd;

    //data request contents
    AsyncTask<?,?,?> asyncTask;
    private boolean isLoading;

    ArrayList<VideoEntry> hotclip;
    HashMap<String,String> mapKeyword;

    //comment fragment part
    private boolean isCommentOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //layout contents FOR activity, youtube
        videoFragment = (VideoFragment)getFragmentManager().findFragmentById(R.id.video_fragment);
        videoListFragment = (VideoListFragment)getFragmentManager().findFragmentById(R.id.list_fragment);
        commentFragment = (CommentFragment)getFragmentManager().findFragmentById(R.id.comment_fragment);
        getFragmentManager().beginTransaction().hide(commentFragment).commit();
        mainContainer = findViewById(R.id.main_container);
        videoContainer = findViewById(R.id.video_container);
        listContainer = findViewById(R.id.list_container);
        closeButton = findViewById(R.id.close_button);
        //videoContainer.setVisibility(View.GONE);

        //progressbar, loading, scroll contents
        loadingProgressBar = findViewById(R.id.loading_progress_bar);
        loadingProgressBar.setVisibility(View.GONE);
        isScrollEnd = false;
        videoListFragment.getListView().setOnScrollListener(this);

        //data request contents
        isLoading = false;
        isCommentOpen = false;

        SearchOptionState.setTopicState(SearchOptionState.TopicState.TOPIC_STATE_HOTCLIP);

        Intent intent = getIntent();
        SearchOptionState.setKeyWordMap((HashMap<String, String>) intent.getSerializableExtra("keyword"));
        hotclip = (ArrayList<VideoEntry>) intent.getSerializableExtra("hotclip");
        videoListFragment.clearVideoEntries();
        videoListFragment.addVideoEntries(hotclip);
        videoFragment.cueVideo(hotclip.get(0).getVideoId());

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
    protected void onResume() {
        super.onResume();
        SearchOptionState.setDateTimeForRequest();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Recreate the activity if user performed a recovery action
            recreate();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (videoFragment.getIsFullScreen()) {
            videoFragment.setFullscreen(false);
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(SearchOptionState.getTopicState() == SearchOptionState.TopicState.TOPIC_STATE_HOTCLIP) {
            menu.getItem(0).setEnabled(false);
            menu.getItem(1).setEnabled(false);
        }else {
            menu.getItem(0).setEnabled(true);
            menu.getItem(1).setEnabled(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(setOptionState(item) == true)
        {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private boolean setOptionState(MenuItem item) {
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
                if(isLoading == false) {
                    isLoading = true;
                    asyncTask = new RequestTask().execute();
                }
                return true;
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
                if(isLoading == false) {
                    isLoading = true;
                    asyncTask = new RequestTask().execute();
                }
                return true;
            }
        }
        return false;
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
                videoListFragment.scrollToTop();
                loadingProgressBar.setVisibility(View.VISIBLE);
                SearchOptionState.setInitJotJJangIndex();
                SearchOptionState.setTopicState(SearchOptionState.TopicState.TOPIC_STATE_HOTCLIP);
                videoListFragment.setUnFocusCheckdItem();
                videoListFragment.clearVideoEntries();
                videoListFragment.addVideoEntries(hotclip);
                loadingProgressBar.setVisibility(View.GONE);
                isLoading = false;
            } else
            {
                item.setChecked(false);
            }
        } else if (id == R.id.nav_seoul_news) {
            if(isLoading == false) {
                isLoading = true;
                videoListFragment.scrollToTop();
                SearchOptionState.setTopicState(SearchOptionState.TopicState.TOPIC_STATE_SEOUL_NEWS);
                asyncTask = new RequestTask().execute();
            } else
            {
                item.setChecked(false);
            }
        } else if (id == R.id.nav_seoul_festival) {
            if(isLoading == false) {
                isLoading = true;
                videoListFragment.scrollToTop();
                SearchOptionState.setTopicState(SearchOptionState.TopicState.TOPIC_STATE_SEOUL_FESTIVAL);
                asyncTask = new RequestTask().execute();
            } else
            {
                item.setChecked(false);
            }
        } else if (id == R.id.nav_seoul_food) {
            if(isLoading == false) {
                isLoading = true;
                videoListFragment.scrollToTop();
                SearchOptionState.setTopicState(SearchOptionState.TopicState.TOPIC_STATE_SEOUL_FOOD);
                asyncTask = new RequestTask().execute();
            } else
            {
                item.setChecked(false);
            }
        } else if (id == R.id.nav_news) {
            if(isLoading == false) {
                isLoading = true;
                videoListFragment.scrollToTop();
                SearchOptionState.setTopicState(SearchOptionState.TopicState.TOPIC_STATE_NEWS);
                asyncTask = new RequestTask().execute();
            } else
            {
                item.setChecked(false);
            }
        } else if (id == R.id.nav_sports) {
            if(isLoading == false) {
                isLoading = true;
                videoListFragment.scrollToTop();
                SearchOptionState.setTopicState(SearchOptionState.TopicState.TOPIC_STATE_SPORTS);
                asyncTask = new RequestTask().execute();
            } else
            {
                item.setChecked(false);
            }
        } else if (id == R.id.nav_humor) {
            if(isLoading == false) {
                isLoading = true;
                videoListFragment.scrollToTop();
                SearchOptionState.setTopicState(SearchOptionState.TopicState.TOPIC_STATE_HUMOR);
                asyncTask = new RequestTask().execute();
            } else
            {
                item.setChecked(false);
            }
        } else if (id == R.id.nav_kpop) {
            if(isLoading == false) {
                isLoading = true;
                videoListFragment.scrollToTop();
                SearchOptionState.setTopicState(SearchOptionState.TopicState.TOPIC_STATE_KPOP);
                asyncTask = new RequestTask().execute();
            } else
            {
                item.setChecked(false);
            }
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
            if(isLoading == false && SearchOptionState.getTopicState() == SearchOptionState.TopicState.TOPIC_STATE_HOTCLIP) {
                isLoading = true;
                asyncTask = new HotClipNextTask().execute();
            } else if (isLoading == false && SearchOptionState.getTopicState() != SearchOptionState.TopicState.TOPIC_STATE_HOTCLIP) {
                isLoading = true;
                asyncTask = new NextTask().execute();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        isScrollEnd = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
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

    public void onClickComment(View view) {
        if(isCommentOpen == false)
        {
            getFragmentManager().beginTransaction().setCustomAnimations(
                    R.animator.slide_left,R.animator.slide_right
                    ,R.animator.slide_left, R.animator.slide_right)
                    .show(getFragmentManager().findFragmentById(R.id.comment_fragment)).commit();
            isCommentOpen = true;
            commentFragment.addCommentItem(new CommentEntry(null,null,null,null,null,0,null,true,0,true));
        } else
        {
            getFragmentManager().beginTransaction().setCustomAnimations(
                    R.animator.slide_left,R.animator.slide_right
                    ,R.animator.slide_left, R.animator.slide_right)
                    .hide(getFragmentManager().findFragmentById(R.id.comment_fragment)).commit();
            isCommentOpen = false;
        }
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

            ArrayList<String> idList = new ArrayList<>();
            for(int i = 0; i < resultList.size(); i++) {
                idList.add(resultList.get(i).getVideoId());
            }

            HashMap<String,Integer> viewCountHashMap = null;
            try {
                viewCountHashMap = MyJsonParser.parseYoutubeViewCount(MyJsonParser.getYoutubeViewCount(idList));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for(int i = 0; i < resultList.size(); i++) {
                VideoEntry videoEntry = resultList.get(i);
                videoEntry.setViewCount(viewCountHashMap.get(videoEntry.getVideoId()));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            videoListFragment.setUnFocusCheckdItem();
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

            ArrayList<String> idList = new ArrayList<>();
            for(int i = 0; i < resultList.size(); i++) {
                idList.add(resultList.get(i).getVideoId());
            }

            HashMap<String,Integer> viewCountHashMap = null;
            try {
                viewCountHashMap = MyJsonParser.parseYoutubeViewCount(MyJsonParser.getYoutubeViewCount(idList));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for(int i = 0; i < resultList.size(); i++) {
                VideoEntry videoEntry = resultList.get(i);
                videoEntry.setViewCount(viewCountHashMap.get(videoEntry.getVideoId()));
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

    private class HotClipNextTask extends AsyncTask<Void, Void, Void> {
        ArrayList<String> idList = new ArrayList<>();
        ArrayList<VideoEntry> resultList = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = MyJsonParser.getJotJJangNext(SearchOptionState.getNextJotJJangIndex());
            try {
                MyJsonParser.parseHotClip(idList, jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                resultList = MyJsonParser.parseYoutubeItems(MyJsonParser.getYoutubeItems(idList));
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
}
