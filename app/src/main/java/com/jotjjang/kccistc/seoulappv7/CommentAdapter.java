package com.jotjjang.kccistc.seoulappv7;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {
    Context context;
    private final ArrayList<CommentEntry> entries;
    private final ArrayList<View> entryViews;
    private final LayoutInflater inflater;

    public CommentAdapter(Context context, ArrayList<CommentEntry> entries)
    {
        this.context = context;
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

        RelativeLayout label = view.findViewById(R.id.comment_label);
        ImageView imageViewProfile = view.findViewById(R.id.comment_authorProfileImageUrl);
        TextView textViewAuthorName = view.findViewById(R.id.comment_authorName);
        TextView textViewPublishedAt = view.findViewById(R.id.comment_publishedAt);
        TextView textViewContentText = view.findViewById(R.id.comment_content_text);
        TextView textViewReplyCount = view.findViewById(R.id.comment_reply_count);

        Glide.with(context).load(entry.getAuthorProfileImageUrl()).into(imageViewProfile);
        textViewAuthorName.setText(entry.getAuthorName());
        textViewPublishedAt.setText(entry.getPublishedAt());
        textViewContentText.setText(entry.getCommentText());
        textViewReplyCount.setText(entry.getTotalReplyCount()+"");

        return view;
    }
}