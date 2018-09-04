package com.jotjjang.kccistc.seoulappv7;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

public class VideoFragment extends YouTubePlayerFragment
        implements YouTubePlayer.OnInitializedListener{
    private YouTubePlayer player = null;
    private String videoId =  null;
    private boolean isFullScreen = false;
    private boolean isCue = false;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initialize(DeveloperKey.DEVELOPER_KEY, this);
    }

    @Override
    public void onDestroy() {
        if (player != null) {
            player.release();
        }
        super.onDestroy();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        this.player = youTubePlayer;
        //player.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT | YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
        player.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
        player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean b) {
                if(b == true) {
                    Log.e("videoplayer","true");
                    isFullScreen = true;
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    Log.e("videoplayer","false");
                    isFullScreen = false;
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });
        if (!b && videoId != null) {
            Log.e("initialize sucess", "cue");
            if(isCue == true) {
                player.cueVideo(videoId);
            } else {
                player.loadVideo(videoId);
            }
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        this.player = null;
    }

    public void loadVideo(String videoId) {
        if (videoId != null && !videoId.equals(this.videoId)) {
            this.videoId = videoId;
            isCue = false;
            if (player != null) {
                Log.e("thisis load","load");
                player.loadVideo(videoId);
            }
        }
    }

    public void cueVideo(String videoId) {
        if (videoId != null && !videoId.equals(this.videoId)) {
            this.videoId = videoId;
            isCue = true;
            if (player != null) {
                Log.e("thisis Cue","cue");
                player.cueVideo(videoId);
            }
        }
    }

    public String getVideoId() {
        if(videoId != null){
            return videoId;
        } else {
            return "";
        }
    }

    public void pauseVideo() {
        if (player != null) {
            player.pause();
        }
    }

    public boolean getIsFullScreen() {
        return this.isFullScreen;
    }

    public void setFullscreen(boolean b) {
        player.setFullscreen(b);
    }
}
