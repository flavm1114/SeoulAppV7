package com.jotjjang.kccistc.seoulappv7;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IntroActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);
        new IntroTask().execute();
    }

    class IntroTask extends AsyncTask<Void, Void, Void> {
        HashMap<String, String> map = new HashMap<>();
        ArrayList<String> idList = new ArrayList<>();
        ArrayList<VideoEntry> entryList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = MyJsonParser.getJotJJang();

            try {
                MyJsonParser.parseKeyWord(map,jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                MyJsonParser.parseHotClip(idList, jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }



            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            startActivity(new Intent(IntroActivity.this, MainActivity.class));
            finish();
        }
    }
}


//    private void executeIntroTask() {
//        IntroTask introTask = new IntroTask(new IntroTask.TaskEndListener() {
//            @Override
//            public void onTaskEnd(Void result) {
//                finish();
//            }
//        });
//    }

//
//class IntroTask extends AsyncTask<Void, Void, Void> {
//    private TaskEndListener listener;
//
//    public IntroTask(TaskEndListener listener) {
//        this.listener = listener;
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//    }
//
//    @Override
//    protected Void doInBackground(Void... params) {
//
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Void result) {
//        if (listener != null) {
//            listener.onTaskEnd(result);
//        }
//    }
//
//    public static interface TaskEndListener<T> {
//        public void onTaskEnd(Void result);
//    }
//}
