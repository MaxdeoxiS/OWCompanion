package com.example.maxime.overwatchstats;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.maxime.overwatchstats.model.Achievement;
import com.example.maxime.overwatchstats.tools.Methods;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class GetAchievementsStats extends AsyncTask<Void, Void, ArrayList<Achievement>> {

    private final String LOG_TAG = GetAchievementsStats.class.getSimpleName();
    private View myView;
    private Context context;

    private String battleTag;

    ArrayAdapter<String> adapter;

    private ProgressDialog progressDialog;

    private String BATTLE_TAG;


    public GetAchievementsStats(View v, Context c, String b, ArrayAdapter a)
    {
        myView = v;
        context = c;
        battleTag = b;
        adapter = a;
    }

    private ArrayList<Achievement> getStatsDataFromJson(String statsAsString) throws JSONException {

        JSONObject jsonObject = new JSONObject(statsAsString);

        ArrayList<Achievement> p = new ArrayList<>();

        JSONObject achievements = jsonObject.getJSONObject("eu").getJSONObject("achievements");

        for (Achievement.Type t : Achievement.Type.values()) {

            JSONObject currentAchievements = achievements.getJSONObject(t.toString());
            Iterator<String> names;

            for (names = currentAchievements.keys(); names.hasNext();) {
                Achievement a = new Achievement();
                a.setType(t);
                String achvName = names.next();
                a.setTitle(parseAchievement(achvName, false));
                a.setAchieved(currentAchievements.getBoolean(achvName));
                a.setDescription(parseAchievement(achvName, true));
                p.add(a);
            }
        }

        return p;
    }

    private String parseAchievement(String achvName, boolean isDesc) {
        return context.getString(context.getResources().getIdentifier(achvName + ((isDesc) ? "_desc" : ""), "string", context.getPackageName()));
    }

    @Override
    protected ArrayList<Achievement> doInBackground(Void... Params) {

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String statsJsonStr = null;

        try {
            final String BASE_URL = "https://owapi.net/api/v3/u/";

            final String PLATFORM = "pc";
            final String REGION = "eu";
            this.BATTLE_TAG = battleTag;
            String PARAM1 = "achievements";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(this.BATTLE_TAG)
                    .appendPath(PARAM1)
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
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = Methods.setUpProgressDialog(context, "Chargement des haut-faits...", false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<Achievement> result) {
        if(result != null) {
            ArrayList<String> achievementsAsString = new ArrayList<>();
            for(Achievement r : result) {
                achievementsAsString.add(r.getTitle());
            }
            adapter.addAll(achievementsAsString);
            adapter.notifyDataSetChanged();

            progressDialog.dismiss();
        }
    }


}