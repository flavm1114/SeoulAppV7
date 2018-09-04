package com.jotjjang.kccistc.seoulappv7;

import android.app.ListFragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class VideoListFragment extends ListFragment {

    private final static ArrayList<VideoEntry> VIDEO_LIST = new ArrayList<>();

    private PageAdapter adapter;
    private View videoContainer;
    private View mainContainer;
    private ListView listView;

    public void addVideoItem(VideoEntry videoEntry) {
        adapter.addVideoItem(videoEntry);
    }

    public void clearVideoEntries() {
        adapter.clearVideoEntries();
    }

    public void addVideoEntries(ArrayList<VideoEntry> list) {
        adapter.addVideoEntries(list);
    }

    public void setLabelVisibility(boolean visible) {
        adapter.setLabelVisibility(visible);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PageAdapter(getActivity(), VIDEO_LIST);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        videoContainer = getActivity().findViewById(R.id.video_container);
        mainContainer = getActivity().findViewById(R.id.main_container);
        listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        setListAdapter(adapter);
        listView.setItemChecked(0, true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.releaseLoaders();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String videoId = VIDEO_LIST.get(position).getVideoId();

        VideoFragment videoFragment =
                (VideoFragment) getFragmentManager().findFragmentById(R.id.video_fragment);

        // The videoBox is INVISIBLE if no video was previously selected, so we need to show it now.
        if (videoContainer.getVisibility() != View.VISIBLE) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                // Initially translate off the screen so that it can be animated in from below.
                videoContainer.setTranslationX(mainContainer.getWidth());
            }
            videoContainer.setVisibility(View.VISIBLE);
        }

        // If the fragment is off the screen, we animate it in.
        if (videoContainer.getTranslationX() > 0) {
            videoContainer.animate().translationX(0).setDuration(MainActivity.ANIMATION_DURATION_MILLIS);
        }
        videoFragment.loadVideo(videoId);
    }

    public void scrollToTop() {
        listView.requestFocusFromTouch();
        listView.setSelection(0);
    }

    public void setUnFocusCheckdItem() {
        if (listView.getCheckedItemCount() > 0) {
            listView.setItemChecked(listView.getCheckedItemPosition(), false);
        }
    }
}
