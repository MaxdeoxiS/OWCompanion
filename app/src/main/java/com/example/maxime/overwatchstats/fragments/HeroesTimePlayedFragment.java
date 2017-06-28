package com.example.maxime.overwatchstats.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maxime.overwatchstats.GetHeroesStats;
import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.activities.HeroesStatsActivity;
import com.example.maxime.overwatchstats.adapters.HeroesAdapter;
import com.example.maxime.overwatchstats.model.Friend;
import com.example.maxime.overwatchstats.model.FriendDAO;
import com.example.maxime.overwatchstats.model.HeroItem;
import com.example.maxime.overwatchstats.model.HeroStats;

import java.util.ArrayList;
import java.util.HashMap;


public class HeroesTimePlayedFragment extends Fragment implements
        GetHeroesStats.OnAsyncRequestComplete {

    ListView lv;
    HeroesAdapter adapter;
    HashMap<String, HeroStats> stats;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.activity_profile_quickplay, container, false);

        adapter = new HeroesAdapter(this.getActivity(), new ArrayList<HeroItem>(), "qp_hero_img_1", "qp_hero_bar", "list_heroes");
        lv = (ListView)     mView.findViewById(R.id.quickplay_list_heroes);
        lv.setAdapter(adapter);

        String mode = this.getArguments().getString("mode");
        ArrayList<String> winStats = (ArrayList<String>) this.getArguments().getSerializable("winStats");

        if (mode.equals("competitive")) {
            winStats = new ArrayList<String>(winStats.subList(3, 6));
        }

        TextView wonGames = (TextView) mView.findViewById(R.id.wonGames);
        TextView lostGames = (TextView) mView.findViewById(R.id.lostGames);
        TextView winRate = (TextView) mView.findViewById(R.id.winRate);


        GetHeroesStats test = new GetHeroesStats(getActivity().getWindow().getDecorView().getRootView(), this.getActivity(), adapter, mode, this);
        test.execute();

        wonGames.setText(winStats.get(0));
        lostGames.setText(winStats.get(1));
        winRate.setText(winStats.get(2) + "%");

        Button button_compare = (Button) mView.findViewById(R.id.button_compare);

        FriendDAO f = new FriendDAO(this.getContext());
        f.open();
        ArrayList<Friend> friends = f.getAllFriends();
        f.close();

        ArrayList<String> friendsAsArrayList = new ArrayList<String>();

        for (Friend friend : friends) {
            friendsAsArrayList.add(friend.getUsername());
        }

        final String[] tmp = friendsAsArrayList.toArray(new String[friendsAsArrayList.size()]);

        button_compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogCompareFriendsTimePlayed newFragment = new DialogCompareFriendsTimePlayed();
                Bundle args = new Bundle();
                args.putStringArray("friends", tmp);
                newFragment.setArguments(args);
                newFragment.show(getFragmentManager(), "friends");

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HeroItem currentItem =  (HeroItem)adapterView.getAdapter().getItem(i);
                String heroName = currentItem.getName();
                float playTime = currentItem.getPlayTimeAsFloat();

                if(playTime > 0) {
                    Intent intent = new Intent(getContext(), HeroesStatsActivity.class);
                    intent.putExtra("stats", stats.get(heroName));
                    intent.putExtra("heroName", heroName);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Vous n'avez pas joué ce héros !", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return mView;
    }

    @Override
    public void asyncResponse(HashMap<String, HeroStats> stats)
    {
        this.stats = stats;
    }
}
