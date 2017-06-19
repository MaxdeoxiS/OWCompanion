package com.example.maxime.overwatchstats;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maxime.overwatchstats.model.Profile;
import com.example.maxime.overwatchstats.tools.Methods;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetProfileStats extends AsyncTask<Void, Void, Object> {

    private final String LOG_TAG = GetProfileStats.class.getSimpleName();
    private View myView;
    private Context context;

    private String battleTag;

    private Button button_add_friend;

    private ImageView profile_pic;
    private ImageView profile_level;
    private ImageView profile_stars = null;
    private ImageView profile_rank_img;

    private TextView profile_text;
    private TextView profile_text_level;
    private TextView profile_rank;

    ProgressDialog progressDialog;

    private OnAsyncRequestComplete caller;

    private String BATTLE_TAG;

    public interface OnAsyncRequestComplete {
        public void asyncResponse(ArrayList<Object> response);
    }

    public GetProfileStats(View v, Context c, String b, OnAsyncRequestComplete caller)
    {
        myView = v;
        context = c;
        profile_pic = (ImageView) myView.findViewById(R.id.img_profile_avatar);
        profile_level = (ImageView) myView.findViewById(R.id.img_profile_level);
        profile_stars = (ImageView) myView.findViewById(R.id.img_profile_stars);
        profile_rank_img = (ImageView) myView.findViewById(R.id.img_profile_rank);
        profile_text = (TextView) myView.findViewById(R.id.profile_text);
        profile_text_level = (TextView) myView.findViewById(R.id.profile_level);
        profile_rank = (TextView) myView.findViewById(R.id.profile_rank);
        button_add_friend = (Button) myView.findViewById(R.id.player_searched_button);
        battleTag = b;
        this.caller = caller;
    }

    private Object getStatsDataFromJson(String statsAsString) throws JSONException {

        JSONObject json = new JSONObject(statsAsString);

        Profile p = new Profile();

        p.setUsername(this.BATTLE_TAG.substring(0, this.BATTLE_TAG.length() - 5));

        JSONObject stats = json.getJSONObject("eu").getJSONObject("stats").getJSONObject("quickplay").getJSONObject("overall_stats");
        p.setLevel((Integer.parseInt(stats.getString("prestige")) * 100 + Integer.parseInt(stats.getString("level"))) + "");
        p.setAvatar(stats.getString("avatar"));
        p.setImg_lvl(stats.getString("rank_image"));
        if(!stats.getString("prestige").equals("0"))
            p.setImg_stars(stats.getString("rank_image").substring(0, stats.getString("rank_image").length() - 10) + "Rank.png");
        if (!stats.getString("comprank").equals("null")) {

            p.setRank(stats.getString("comprank"));
            int rank = Integer.parseInt(stats.getString("comprank"));
            int i;
            if(rank < 1500)
                i = 1;
            else if(rank < 2000)
                i = 2;
            else if(rank < 2500)
                i = 3;
            else if(rank < 3000)
                i = 4;
            else if(rank < 3500)
                i = 5;
            else if(rank < 4000)
                i = 6;
            else i = 7;

            p.setImg_rank("https://blzgdapipro-a.akamaihd.net/game/rank-icons/season-2/rank-" + i + ".png");
        }

        p.setQp_losses(stats.getString("losses"));
        p.setQp_wins(stats.getString("wins"));
        p.setQp_winrate(stats.getString("win_rate"));

        JSONObject rankedStats = json.getJSONObject("eu").getJSONObject("stats").getJSONObject("competitive").getJSONObject("overall_stats");

        p.setRanked_losses(rankedStats.getString("losses"));
        p.setRanked_wins(rankedStats.getString("wins"));
        p.setRanked_winrate(rankedStats.getString("win_rate"));

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
            final String BASE_URL = "https://owapi.net/api/v3/u/";

            final String PLATFORM = "pc";
            final String REGION = "eu";
            this.BATTLE_TAG = battleTag;
            String PARAM1 = "stats";

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
        progressDialog = Methods.setUpProgressDialog(context, "Chargement du profil...", false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Object result) {
        if(result != null) {
            populateViews(result);
            progressDialog.dismiss();
        }
    }

    public void populateViews(Object result){
        Profile p = (Profile)result;
        ArrayList<String> winStats = new ArrayList<>();
        winStats.add(p.getQp_wins());
        winStats.add(p.getQp_losses());
        winStats.add(p.getQp_winrate());
        winStats.add(p.getRanked_wins());
        winStats.add(p.getRanked_losses());
        winStats.add(p.getRanked_winrate());

        ArrayList<Object> responses = new ArrayList<>();
        responses.add(winStats);

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
            (profile_rank).setText(p.getRank());
        if(null != button_add_friend)
            button_add_friend.setVisibility(View.VISIBLE);
        if(null != profile_rank_img)
            Picasso.with(context).load(p.getImg_rank()).into(profile_rank_img);
        responses.add(result);
        caller.asyncResponse(responses);
    }

}