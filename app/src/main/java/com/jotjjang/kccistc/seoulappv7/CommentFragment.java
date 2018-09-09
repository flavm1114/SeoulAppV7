package com.jotjjang.kccistc.seoulappv7;


import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.util.ArrayList;

public class CommentFragment extends ListFragment {

    private final static ArrayList<CommentEntry> COMMENT_LIST = new ArrayList<>();
    private CommentAdapter adapter;
    private ListView listView;
    private VideoFragment videoFragment;
    private ProgressBar progressBar;

    private boolean isScrollEnd;
    private boolean isLoading;

    public static String nextCommentToken;
    private ImageView noMoreImageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CommentAdapter(getActivity(), COMMENT_LIST);
        isLoading = false;
        isScrollEnd = false;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        videoFragment = (VideoFragment) getActivity().getFragmentManager().findFragmentById(R.id.video_fragment);
        progressBar = getActivity().findViewById(R.id.loading_progress_bar);
        listView = getListView();
        listView.setItemsCanFocus(false);
        listView.setClickable(false);
        listView.setSelector(R.drawable.empty_selector);
        listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        setListAdapter(adapter);
        noMoreImageView = new ImageView(getActivity());
        noMoreImageView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        noMoreImageView.setAdjustViewBounds(true);
        noMoreImageView.setImageResource(R.drawable.no_more_comment_l);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && isScrollEnd && isLoading == false) {
                    // 화면이 바닦에 닿을때 처리
                    // 로딩중을 알리는 프로그레스바를 보인다.
                    if(isLoading == false) {
                        isLoading = true;
                        new CommentNextTask().execute();
                    }
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isScrollEnd = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
            }
        });
    }

    public void addCommentItem(CommentEntry commentEntry) {
        COMMENT_LIST.add(commentEntry);
        adapter.notifyDataSetChanged();
    }

    public void addCommentItemList(ArrayList<CommentEntry> commentEntries) {
        for(int i = 0; i < commentEntries.size(); i++) {
            COMMENT_LIST.add(commentEntries.get(i));
        }
        adapter.notifyDataSetChanged();
    }

    public void clearCommentEntries() {
        COMMENT_LIST.clear();
        adapter.notifyDataSetChanged();
    }

    public void addFooterNoMore() {
        listView.addFooterView(noMoreImageView);
    }

    public void removeFooter() {
        listView.removeFooterView(noMoreImageView);
    }

    private class CommentNextTask extends AsyncTask<Void, Void, Void> {
        ArrayList<CommentEntry> resultList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                resultList = MyJsonParser.parseYoutubeComments(
                        MyJsonParser.getYoutubeComments(
                                videoFragment.getVideoId(), nextCommentToken,10));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(listView.getFooterViewsCount() == 0 && resultList.size() == 0)
            {
                addFooterNoMore();
            } else if(listView.getFooterViewsCount() > 0 && resultList.size() > 0)
            {
                removeFooter();
            }
            progressBar.setVisibility(View.GONE);
            addCommentItemList(resultList);
            isLoading = false;
        }
    }
}
