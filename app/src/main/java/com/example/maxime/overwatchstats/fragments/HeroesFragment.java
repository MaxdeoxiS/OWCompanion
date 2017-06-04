package com.example.maxime.overwatchstats.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.adapters.MyAdapter;
import com.example.maxime.overwatchstats.model.Heroes;

public class HeroesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.fragment_heroes, container, false);

        final Heroes heroes = new Heroes(this.getResources().openRawResource(R.raw.heroes));

        RecyclerView mRecyclerView = (RecyclerView) mView.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        MyAdapter mAdapter = new MyAdapter(heroes.getHeroes());
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                String heroName = heroes.getHeroes().get(viewHolder.getAdapterPosition()).getNickname();
                Bundle args = new Bundle();
                args.putString("hero", heroName);

                if(direction == ItemTouchHelper.LEFT) {
                    Toast.makeText(getContext(), "Swipe to left!", Toast.LENGTH_SHORT).show();
                    HeroDescFragment frgm = new HeroDescFragment();
                    frgm.setArguments(args);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, frgm)
                            .addToBackStack("HEROES_DESC")
                            .commit();
                }
                if (direction == ItemTouchHelper.RIGHT) {
                    Toast.makeText(getContext(), "Swipe to right!" + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();

                    HeroAbilitiesFragment frgm = new HeroAbilitiesFragment();
                    frgm.setArguments(args);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, frgm)
                            .addToBackStack("HEROES_ABILITIES")
                            .commit();

                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);




        return mView;
    }
}
