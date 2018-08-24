package com.jotjjang.kccistc.seoulappv7;

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

public class MyJsonParser {

    public static JSONObject getYoutubeDate(String searchKeyWord,int count) {
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

    public static ArrayList<VideoEntry> parseJsonData(JSONObject jsonObject) throws JSONException{
        ArrayList<VideoEntry> list = new ArrayList<>();

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
            String changeString = "";
            try {
                changeString = new String(title.getBytes("8859_1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            publishedDate = c.getJSONObject("snippet").getString("publishedAt").substring(0,10);
            String imgUrl = c.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("default").getString("url");

            description = c.getJSONObject("snippet").getString("description");
            channelTitle = c.getJSONObject("snippet").getString("channelTitle");

            list.add(new VideoEntry(changeString, videoId,publishedDate,description,channelTitle,imgUrl));
        }

        return list;
    }
}
