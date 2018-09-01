package com.jotjjang.kccistc.seoulappv7;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

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
            JSONObject jotJJangObject = MyJsonParser.getJotJJang();

            try {
                MyJsonParser.parseKeyWord(map, jotJJangObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                MyJsonParser.parseHotClip(idList, jotJJangObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for(int i = 0; i < idList.size(); i++) {
                JSONObject jsonObject = MyJsonParser.getOneYoutube(idList.get(i));
                VideoEntry entry = null;
                try {
                    entry = MyJsonParser.parseOneYoutube(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                entryList.add(entry);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            intent.putExtra("keyword", map);
            intent.putExtra("hotclip", entryList);

            startActivity(intent);
            finish();
        }
    }
}