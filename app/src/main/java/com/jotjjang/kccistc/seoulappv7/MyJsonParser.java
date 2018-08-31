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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                        + "&safeSearch=strict"
                        + "&videoEmbeddable=true");

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
                        + "&safeSearch=strict"
                        + "&videoEmbeddable=true");

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

        //Log.e("gdgd", stringBuilder.toString());

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

    public static JSONObject getJotJJang() {
        HttpGet httpGet = new HttpGet("http://jotjjang.com/seoulApp");

        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            InputStreamReader isr = new InputStreamReader(stream, "utf-8");
            BufferedReader reader = new BufferedReader(isr);

            String b;
            while((b = reader.readLine()) != null)
            {
                stringBuilder.append(b);
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

    public static void parseKeyWord(HashMap<String, String> map, JSONObject jsonObject) throws JSONException {
        JSONArray contacts = jsonObject.getJSONArray("keyword");
        for (int i = 0; i < contacts.length(); i++)
        {
            JSONObject c = contacts.getJSONObject(i);
            String key = c.getString("key");
            String val = c.getString("val");
            map.put(key,val);
        }
    }

    public static void parseHotClip(ArrayList<String> idList, JSONObject jsonObject) throws JSONException {
        JSONArray contacts = jsonObject.getJSONArray("hotclip");
        for (int i = 0; i < contacts.length(); i++)
        {
            JSONObject c = contacts.getJSONObject(i);
            String id = c.getString("id");
            idList.add(id);

        }
    }

    ///여기 비디오 watch키워드로 한개만 가져오는 부분 하자(핫클립도 통합) + 조회수 가져오기위해서

    public static JSONObject getOenYoutube(String videoId) {
        //https://www.googleapis.com/youtube/v3/videos?part=snippet,statistics&id=YU84DkyGaXc&key=AIzaSyDM6uBp-0NoUf9OvBWIMze6Z3wYUv2XimM
        HttpGet httpGet = new HttpGet(
                "https://www.googleapis.com/youtube/v3/videos"
                    + "?part=snippet,statistics"
                    + "&id=" + videoId
                    + "&key=" + DeveloperKey.DEVELOPER_KEY);

        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            InputStreamReader isr = new InputStreamReader(stream, "utf-8");
            BufferedReader reader = new BufferedReader(isr);

            String b;
            while((b = reader.readLine()) != null)
            {
                stringBuilder.append(b);
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

    public static VideoEntry parseOneYoutube(JSONObject jsonObject) throws JSONException {
        JSONArray contacts = jsonObject.getJSONArray("items");
        JSONObject c = contacts.getJSONObject(0);
        JSONObject snippet = c.getJSONObject("snippet");
        JSONObject statistics = c.getJSONObject("statistics");

        String title = null;
        String description = null;
        String channelTitle = null;
        try {
            title = new String(snippet.getString("title").getBytes("8859_1"), "utf-8");
            description = new String(snippet.getString("description").getBytes("8859_1"), "utf-8");
            channelTitle = new String(snippet.getString("channelTitle").getBytes("8859_1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String videoId = c.getString("id");
        String publishedDate = snippet.getString("publishedAt").substring(0,10);
        String imgUrl = snippet.getJSONObject("thumbnails").getJSONObject("default").getString("url");
        int viewCount = Integer.parseInt(statistics.getString("viewCount"));

        return new VideoEntry(title,videoId,publishedDate,description,channelTitle,imgUrl,viewCount);
    }
}
