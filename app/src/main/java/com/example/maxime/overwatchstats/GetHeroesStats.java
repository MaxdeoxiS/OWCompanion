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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GetHeroesStats extends AsyncTask<Void, Void, ArrayList<HeroItem>> {

    private final String LOG_TAG = GetHeroesStats.class.getSimpleName();
    private View myView;
    private Context context;
    private HeroesAdapter adapter;
    // Gamemode. Either quickplay or competitive
    private String mode;

    private static final Map<String, String> heroesHexa = createMap();

    private static Map<String, String> createMap()
    {
        Map<String,String> myMap = new HashMap<String,String>();
        myMap.put("genji", "029");
        myMap.put("hanzo", "005");
        myMap.put("mccree", "042");
        myMap.put("widowmaker", "00A");
        myMap.put("pharah", "008");
        myMap.put("soldier76", "06E");
        myMap.put("ana", "13B");
        myMap.put("mei", "0DD");
        myMap.put("reaper", "002");
        myMap.put("roadhog", "040");
        myMap.put("junkrat", "065");
        myMap.put("zarya", "068");
        myMap.put("sombra", "12E");
        myMap.put("zenyatta", "020");
        myMap.put("lucio", "079");
        myMap.put("winston", "009");
        myMap.put("torbjorn", "006");
        myMap.put("dva", "07A");
        myMap.put("tracer", "003");
        myMap.put("mercy", "004");
        myMap.put("bastion", "015");
        myMap.put("symmetra", "016");
        myMap.put("reinhardt", "007");
        myMap.put("orisa", "13E");
        return myMap;
    }

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

        JSONObject stats = jsonObject.getJSONObject("eu").getJSONObject("heroes").getJSONObject("playtime").getJSONObject(this.mode);

        String heroImg = "https://blzgdapipro-a.akamaihd.net/game/heroes/small/0x02E0000000000";

        Iterator<String> names;

        for (names = stats.keys(); names.hasNext();) {
            String heroName = names.next();
              HeroItem h = new HeroItem(heroName, stats.getString(heroName), heroImg + heroesHexa.get(heroName) + ".png");
              p.add(h);
        }

        return p;
    }

    @Override
    protected ArrayList<HeroItem> doInBackground(Void... Params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String statsJsonStr = null;

        try {
            final String BASE_URL = "https://owapi.net/api/v3/u/";

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String pref_battleTag = preferences.getString("currentBattleTag", "");

            final String BATTLE_TAG = pref_battleTag;
            final String DATATYPE = "heroes";


            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(BATTLE_TAG)
                    .appendPath(DATATYPE)
                    .build();

            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            urlConnection.setRequestProperty("Accept","*/*");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            statsJsonStr = buffer.toString();

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);

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
        Collections.sort(heroesItems, new Comparator<HeroItem>() {
            @Override
            public int compare(HeroItem heroItem, HeroItem t1) {
                float diff = t1.getPlayTimeAsFloat() - heroItem.getPlayTimeAsFloat();
                if (diff < 0)
                    return -1;
                else if (diff == 0)
                    return 0;
                else
                    return 1;
            }
        });
            adapter.addAll(heroesItems);
            adapter.notifyDataSetChanged();
        }
    }
}