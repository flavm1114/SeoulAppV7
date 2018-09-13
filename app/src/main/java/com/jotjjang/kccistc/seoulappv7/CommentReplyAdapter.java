package com.jotjjang.kccistc.seoulappv7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class CommentReplyAdapter extends BaseAdapter {
    Context context;
    private final ArrayList<CommentEntry> entries;
    private final LayoutInflater inflater;

    public CommentReplyAdapter(Context context)
    {
        this.context = context;
        this.entries = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CommentEntry getItem(int position) {
        return entries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
       // CommentEntry entry = entries.get(position);

        if (view == null) {
            view = inflater.inflate(R.layout.comment_reply_item,parent,false);
        }


        return view;
    }
}
