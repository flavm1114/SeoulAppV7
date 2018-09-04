package com.jotjjang.kccistc.seoulappv7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {
    private final ArrayList<CommentEntry> entries;
    private final ArrayList<View> entryViews;
    private final LayoutInflater inflater;

    public CommentAdapter(Context context, ArrayList<CommentEntry> entries)
    {
        this.entries = entries;
        entryViews = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return entries.size();
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
        CommentEntry entry = entries.get(position);

        if (view == null) {
            view = inflater.inflate(R.layout.comment_item, parent, false);
        }

        TextView textView = view.findViewById(R.id.comment_item_title);
        return view;
    }

    public void addCommentItem(CommentEntry commentEntry) {
        entries.add(commentEntry);
        notifyDataSetChanged();
    }
}
