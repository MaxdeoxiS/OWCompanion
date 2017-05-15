package com.example.maxime.overwatchstats;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.example.maxime.overwatchstats.adapters.HeroesAdapter;
import com.example.maxime.overwatchstats.model.HeroItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetHeroesStats extends AsyncTask<Void, Void, ArrayList<HeroItem>> {

    private final String LOG_TAG = GetHeroesStats.class.getSimpleName();
    private View myView;
    private Context context;
    private HeroesAdapter adapter;
    // Gamemode. Either quickplay or competitive
    private String mode;

    public GetHeroesStats(View v, Context c, HeroesAdapter a, String m)
    {
        myView = v;
        context = c;
        adapter = a;
        mode = m;
    }

    public GetHeroesStats(View v, Context c, HeroesAdapter a)
    {
        myView = v;
        context = c;
        adapter = a;
        mode = "quickplay";
    }

    private ArrayList<HeroItem> getStatsDataFromJson(String statsAsString) throws JSONException {

        JSONObject jsonObject = new JSONObject(statsAsString);

        ArrayList<HeroItem> p = new ArrayList<HeroItem>();

        JSONArray stats = jsonObject.getJSONObject("stats").getJSONObject("top_heroes").getJSONArray(this.mode);

        int jsonArraySize = stats.length();

        for (int i = 0; i < jsonArraySize; i++) {
            JSONObject currentNode = stats.getJSONObject(i);
            HeroItem h = new HeroItem(currentNode.getString("hero"), currentNode.getString("played"), currentNode.getString("img"));
            p.add(h);
        }

        return p;
    }

    @Override
    protected ArrayList<HeroItem> doInBackground(Void... Params) {

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String statsJsonStr = null;


        try {
            final String BASE_URL = "http://ow-api.herokuapp.com/";

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String pref_battleTag = preferences.getString("currentBattleTag", "");

            final String PLATFORM = "pc";
            final String REGION = "eu";
            final String BATTLE_TAG = pref_battleTag;
            final String DATATYPE = "stats";


            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(DATATYPE)
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
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
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
    protected void onPostExecute(ArrayList<HeroItem> heroesItems) {
        if (heroesItems != null) {
            adapter.addAll(heroesItems);
            adapter.notifyDataSetChanged();
        }
    }
}