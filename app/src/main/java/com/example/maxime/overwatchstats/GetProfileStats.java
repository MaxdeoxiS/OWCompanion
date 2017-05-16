package com.example.maxime.overwatchstats;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maxime.overwatchstats.model.Profile;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetProfileStats extends AsyncTask<Void, Void, Object> {

    private final String LOG_TAG = GetProfileStats.class.getSimpleName();
    private View myView;
    private Context context;

    private String battleTag;

    private Button button_add_friend;

    private ImageView profile_pic;
    private ImageView profile_level;
    private ImageView profile_stars = null;

    private TextView profile_text;
    private TextView profile_text_level;
    private TextView profile_rank;

    public GetProfileStats(View v, Context c, String b)
    {
        myView = v;
        context = c;
        profile_pic = (ImageView) myView.findViewById(R.id.img_profile_avatar);
        profile_level = (ImageView) myView.findViewById(R.id.img_profile_level);
        profile_stars = (ImageView) myView.findViewById(R.id.img_profile_stars);
        profile_text = (TextView) myView.findViewById(R.id.profile_text);
        profile_text_level = (TextView) myView.findViewById(R.id.profile_level);
        profile_rank = (TextView) myView.findViewById(R.id.profile_rank);
        button_add_friend = (Button) myView.findViewById(R.id.player_searched_button);
        battleTag = b;
    }

    private Object getStatsDataFromJson(String statsAsString) throws JSONException {

        JSONObject json = new JSONObject(statsAsString);

        Profile p = new Profile();

        p.setUsername(json.getString("username"));
        p.setLevel(json.getString("level"));
        p.setAvatar(json.getString("portrait"));
        p.setImg_lvl(json.getString("levelFrame"));
        if(!json.getString("star").equals(""))
            p.setImg_stars(json.getString("star"));
        if(!json.getJSONObject("competitive").getString("rank").equals(""))
            p.setRank(json.getJSONObject("competitive").getString("rank"));

        return p;
    }

    @Override
    protected Object doInBackground(Void... Params) {

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String statsJsonStr = null;

        try {
            final String BASE_URL = "http://ow-api.herokuapp.com/";

            final String PLATFORM = "pc";
            final String REGION = "eu";
            String BATTLE_TAG = battleTag;
            String PARAM1 = "profile";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(PARAM1)
                    .appendPath(PLATFORM)
                    .appendPath(REGION)
                    .appendPath(BATTLE_TAG)
                    .build();

            URL url = new URL(builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            int status = urlConnection.getResponseCode();
            if (status != 200) {
                cancel(true);
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            statsJsonStr = buffer.toString();

            Log.v(LOG_TAG, "Forecast string: " + statsJsonStr);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        try {
            return getStatsDataFromJson(statsJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object result) {
        Profile p = (Profile)result;
        if (result != null) {
            if(null != profile_pic)
                Picasso.with(context).load(p.getAvatar()).into(profile_pic);
            if(null != profile_level)
                Picasso.with(context).load(p.getImg_lvl()).into(profile_level);
            if(null != profile_stars)
                Picasso.with(context).load(p.getImg_stars()).into(profile_stars);
            if(null != profile_text)
                (profile_text).setText(p.getUsername());
            if(null != profile_text_level)
                (profile_text_level).setText(p.getLevel());
            if(null != profile_rank)
                (profile_text_level).setText(p.getRank());
            if(null != button_add_friend)
                button_add_friend.setVisibility(View.VISIBLE);
        }
    }

}