package com.jotjjang.kccistc.seoulappv7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageAdapter extends BaseAdapter{
    private final ArrayList<VideoEntry> entries;
    private final ArrayList<View> entryViews;
    private final Map<YouTubeThumbnailView, YouTubeThumbnailLoader> thumbnailViewToLoaderMap;
    private final LayoutInflater inflater;
    private final ThumbnailListener thumbnailListener;

    private boolean labelsVisible;

    public PageAdapter(Context context, ArrayList<VideoEntry> entries) {
        this.entries = entries;

        entryViews = new ArrayList<View>();
        thumbnailViewToLoaderMap = new HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>();
        inflater = LayoutInflater.from(context);
        thumbnailListener = new ThumbnailListener();

        labelsVisible = true;
    }

    public void releaseLoaders() {
        for (YouTubeThumbnailLoader loader : thumbnailViewToLoaderMap.values()) {
            loader.release();
        }
    }

    public void setLabelVisibility(boolean visible) {
        labelsVisible = visible;
        for (View view : entryViews) {
            view.findViewById(R.id.item_label).setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public VideoEntry getItem(int position) {
        return entries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        VideoEntry entry = entries.get(position);

        // There are three cases here
        if (view == null) {
            // 1) The view has not yet been created - we need to initialize the YouTubeThumbnailView.
            view = inflater.inflate(R.layout.video_item, parent, false);
            YouTubeThumbnailView thumbnail = (YouTubeThumbnailView) view.findViewById(R.id.thumbnail);
            thumbnail.setTag(entry.getVideoId());
            thumbnail.initialize(DeveloperKey.DEVELOPER_KEY, thumbnailListener);
        } else {
            YouTubeThumbnailView thumbnail = (YouTubeThumbnailView) view.findViewById(R.id.thumbnail);
            YouTubeThumbnailLoader loader = thumbnailViewToLoaderMap.get(thumbnail);
            if (loader == null) {
                // 2) The view is already created, and is currently being initialized. We store the
                //    current videoId in the tag.
                thumbnail.setTag(entry.getVideoId());
            } else {
                // 3) The view is already created and already initialized. Simply set the right videoId
                //    on the loader.
                thumbnail.setImageResource(R.drawable.ic_menu_manage);
                loader.setVideo(entry.getVideoId());
            }
        }

        LinearLayout label = view.findViewById(R.id.item_label);
        TextView textTitle = view.findViewById(R.id.item_title);
        textTitle.setText(entry.getTitle());
        TextView textDescription = view.findViewById(R.id.item_description);
        textDescription.setText(entry.getDescription());
        TextView textChannelTitle = view.findViewById(R.id.item_channelTitle);
        textChannelTitle.setText(entry.getChannelTitle());
        TextView textPublishedDate = view.findViewById(R.id.item_publishedDate);
        textPublishedDate.setText(entry.getPublishedDate());

        label.setVisibility(labelsVisible ? View.VISIBLE : View.GONE);
        return view;
    }

    private final class ThumbnailListener implements
            YouTubeThumbnailView.OnInitializedListener,
            YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        @Override
        public void onInitializationSuccess(
                YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
            loader.setOnThumbnailLoadedListener(this);
            thumbnailViewToLoaderMap.put(view, loader);
            view.setImageResource(R.drawable.ic_menu_manage);
            String videoId = (String) view.getTag();
            loader.setVideo(videoId);
        }

        @Override
        public void onInitializationFailure(
                YouTubeThumbnailView view, YouTubeInitializationResult loader) {
            view.setImageResource(R.drawable.ic_menu_gallery);
        }

        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView view, String videoId) {
        }

        @Override
        public void onThumbnailError(YouTubeThumbnailView view, YouTubeThumbnailLoader.ErrorReason errorReason) {
            view.setImageResource(R.drawable.ic_menu_gallery);
        }
    }
}
