package com.jotjjang.kccistc.seoulappv7;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class MyJsonParser {

    public static JSONObject getYoutubeData(String searchKeyWord, int count) {
        HttpGet httpGet = new HttpGet(
                "https://www.googleapis.com/youtube/v3/search?"
                        + "part=snippet&q=" + searchKeyWord
                        + "&key=" + DeveloperKey.DEVELOPER_KEY
                        + "&maxResults=" + count);

        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while((b = stream.read()) != -1)
            {
                stringBuilder.append((char) b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static JSONObject getYoutubeData(String searchKeyWord, String beforeDateKeyWord, String afterDateKeyWord, String orderKeyWord, int count) {
        HttpGet httpGet = new HttpGet(
                "https://www.googleapis.com/youtube/v3/search?"
                        + "part=snippet&q=" + searchKeyWord
                        + "&key=" + DeveloperKey.DEVELOPER_KEY
                        + "&publishedBefore=" + beforeDateKeyWord
                        + "&publishedAfter=" + afterDateKeyWord
                        + "&order=" + orderKeyWord
                        + "&maxResults=" + count
                        + "&regionCode=KR"
                        + "&type=video"
                        + "&safeSearch=strict");

        Log.e("getYoutubeData--URL!", "https://www.googleapis.com/youtube/v3/search?"
                + "part=snippet&q=" + searchKeyWord
                + "&key=" + DeveloperKey.DEVELOPER_KEY
                + "&publishedBefore=" + beforeDateKeyWord
                + "&publishedAfter=" + afterDateKeyWord
                + "&order=" + orderKeyWord
                + "&maxResults=" + count
                + "&regionCode=KR"
                + "&type=video"
                + "&safeSearch=strict");

        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while((b = stream.read()) != -1)
            {
                stringBuilder.append((char) b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static JSONObject getYoutubeData(String searchKeyWord, String beforeDateKeyWord, String afterDateKeyWord, String orderKeyWord, int count, String nextPageToken) {
        HttpGet httpGet = new HttpGet(
                "https://www.googleapis.com/youtube/v3/search?"
                        + "part=snippet&q=" + searchKeyWord
                        + "&key=" + DeveloperKey.DEVELOPER_KEY
                        + "&publishedBefore=" + beforeDateKeyWord
                        + "&publishedAfter=" + afterDateKeyWord
                        + "&order=" + orderKeyWord
                        + "&maxResults=" + count
                        + "&regionCode=KR"
                        + "&pageToken=" + nextPageToken
                        + "&type=video"
                        + "&safeSearch=strict");
        Log.e("getYoutubeData2-URL!", "https://www.googleapis.com/youtube/v3/search?"
                + "part=snippet&q=" + searchKeyWord
                + "&key=" + DeveloperKey.DEVELOPER_KEY
                + "&publishedBefore=" + beforeDateKeyWord
                + "&publishedAfter=" + afterDateKeyWord
                + "&order=" + orderKeyWord
                + "&maxResults=" + count
                + "&regionCode=KR"
                + "&pageToken=" + nextPageToken
                + "&type=video"
                + "&safeSearch=strict");

        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while((b = stream.read()) != -1)
            {
                stringBuilder.append((char) b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static void parseJsonData(ArrayList<VideoEntry> list, JSONObject jsonObject) throws JSONException{

        SearchOptionState.setNextPageToken(jsonObject.getString("nextPageToken"));

        JSONArray contacts = jsonObject.getJSONArray("items");
        for (int i = 0; i < contacts.length(); i++)
        {
            String title;
            String videoId;
            String publishedDate;
            String description;
            String channelTitle;
            JSONObject c = contacts.getJSONObject(i);
            String kind = c.getJSONObject("id").getString("kind");
            if(kind.equals("youtube#video")) {
                videoId = c.getJSONObject("id").getString("videoId");
            } else {
                videoId = c.getJSONObject("id").getString("playlistId");
            }
            title = c.getJSONObject("snippet").getString("title");
            description = c.getJSONObject("snippet").getString("description");
            channelTitle = c.getJSONObject("snippet").getString("channelTitle");

            String changedTitle = "";
            String changedDescription = "";
            String changedChannelTitle = "";
            try {
                changedTitle = new String(title.getBytes("8859_1"), "utf-8");
                changedDescription = new String(description.getBytes("8859_1"), "utf-8");
                changedChannelTitle = new String(channelTitle.getBytes("8859_1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            publishedDate = c.getJSONObject("snippet").getString("publishedAt").substring(0,10);
            String imgUrl = c.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("default").getString("url");

            list.add(new VideoEntry(changedTitle, videoId,publishedDate,changedDescription,changedChannelTitle,imgUrl));
        }
    }

    public static JSONObject getJotJJangKeyWord() {
        HttpGet httpGet = new HttpGet("jotjjang.com/searchkeyword");

        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while((b = stream.read()) != -1)
            {
                stringBuilder.append((char) b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static void parseJotJJangKeyWord(HashMap<String,String> map, JSONObject jsonObject) throws JSONException {
        JSONArray contacts = jsonObject.getJSONArray("items");
        for (int i = 0; i < contacts.length(); i++)
        {
            String topic;
            String str;

            JSONObject c = contacts.getJSONObject(i);
            topic = c.getString("topic");
            str = c.getString("string");

            String changedStr = "";

            try {
                changedStr = new String(str.getBytes("8859_1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            map.put(topic,str);
        }
    }

    private static JSONObject getJotJJangHotClip() {
        HttpGet httpGet = new HttpGet(
                "jotjjang.com/hotclip");

        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while((b = stream.read()) != -1)
            {
                stringBuilder.append((char) b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    private static JSONObject getJotJJangYoutube() {
        HttpGet httpGet = new HttpGet(
                "jotjjang.com/hotclip");

        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while((b = stream.read()) != -1)
            {
                stringBuilder.append((char) b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    public static void parseJotJJangYoutube(ArrayList<VideoEntry> list, JSONObject jsonObject) throws JSONException{


    }
}
