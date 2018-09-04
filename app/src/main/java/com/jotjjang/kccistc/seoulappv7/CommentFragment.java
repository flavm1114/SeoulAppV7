package com.jotjjang.kccistc.seoulappv7;


import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class CommentFragment extends ListFragment {

    private final static ArrayList<CommentEntry> COMMENT_LIST = new ArrayList<>();
    private CommentAdapter adapter;
    private ListView listView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CommentAdapter(getActivity(), COMMENT_LIST);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        setListAdapter(adapter);
    }

    public void addCommentItem(CommentEntry commentEntry) {
        adapter.addCommentItem(commentEntry);
    }
}
