package com.jotjjang.kccistc.seoulappv7;


import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;

public class CommentFragment extends ListFragment {

    private final static ArrayList<CommentEntry> COMMENT_LIST = new ArrayList<>();
    private CommentAdapter adapter;
    private ListView listView;

    private boolean isScrollEnd;
    private boolean isLoading;

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
        listView = getListView();
        listView.setItemsCanFocus(false);
        listView.setClickable(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        setListAdapter(adapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && isScrollEnd && isLoading == false) {
                    // 화면이 바닦에 닿을때 처리
                    // 로딩중을 알리는 프로그레스바를 보인다.
                    if(isLoading == false) {

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
        adapter.addCommentItem(commentEntry);
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
    }
}
