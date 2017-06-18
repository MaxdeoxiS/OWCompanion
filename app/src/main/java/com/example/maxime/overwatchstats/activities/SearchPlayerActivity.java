package com.example.maxime.overwatchstats.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.maxime.overwatchstats.GetProfileStats;
import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.model.Friend;
import com.example.maxime.overwatchstats.model.FriendDAO;

import java.util.ArrayList;

public class SearchPlayerActivity extends AppCompatActivity implements
        GetProfileStats.OnAsyncRequestComplete{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            final String query = intent.getStringExtra(SearchManager.QUERY);

            //TODO add a method to check if player exists or not

            GetProfileStats profile = new GetProfileStats(this.getWindow().getDecorView().findViewById(android.R.id.content), this, query, this);
            profile.execute();

            Button button = (Button) findViewById(R.id.profile_button_achievements);
            Button button2 = (Button) findViewById(R.id.profile_button_quickplay);
            Button button3 = (Button) findViewById(R.id.profile_button_ranked);

            button.setEnabled(false);
            button2.setEnabled(false);
            button3.setEnabled(false);

            final Button button_add_friend = (Button) findViewById(R.id.player_searched_button);
            button_add_friend.setText("+");
                    button_add_friend.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            FriendDAO f = new FriendDAO(getBaseContext());
                            f.open();
                            boolean insert_success = f.add(new Friend(query.substring(0, query.length() - 5), query));
                            f.close();
                            Context context = getApplicationContext();
                            CharSequence text = (insert_success) ? getString(R.string.friend_added) : getString(R.string.cant_add_friend);
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    });
        }
    }

    @Override
    public void asyncResponse(ArrayList<String> stats)
    {
    }

}
