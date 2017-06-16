package com.example.maxime.overwatchstats.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.activities.MapsActivity;
import com.example.maxime.overwatchstats.adapters.HeroesDataAdapter;
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
        HeroesDataAdapter mAdapter = new HeroesDataAdapter(heroes.getHeroes(), this.getContext());
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            public static final float ALPHA_FULL = 1.0f;

            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    // Get RecyclerView item from the ViewHolder
                    View itemView = viewHolder.itemView;

                    Paint p = new Paint();
                    Bitmap icon;

                    if (dX > 0) {
            /* Note, ApplicationManager is a helper class I created
               myself to get a context outside an Activity class -
               feel free to use your own method */

                        icon = BitmapFactory.decodeResource(
                                getContext().getResources(), R.drawable.atom);
                        icon = Bitmap.createScaledBitmap(icon, 80, 80, false);

            /* Set your color for positive displacement */
                        p.setARGB(250, 250, 205, 210);

                        // Draw Rect with varying right side, equal to displacement dX
                        c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                                (float) itemView.getBottom(), p);

                        // Set the image icon for Right swipe
                        c.drawBitmap(icon,
                                (float) itemView.getLeft() + convertDpToPx(16),
                                (float) itemView.getTop() + ((float) itemView.getBottom() - (float) itemView.getTop() - icon.getHeight())/2,
                                p);
                    } else {
                        icon = BitmapFactory.decodeResource(
                                getContext().getResources(), R.drawable.book);

                        icon = Bitmap.createScaledBitmap(icon, 80, 80, false);

            /* Set your color for negative displacement */
                        p.setARGB(250, 244, 67, 3);

                        // Draw Rect with varying left side, equal to the item's right side
                        // plus negative displacement dX
                        c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                                (float) itemView.getRight(), (float) itemView.getBottom(), p);

                        //Set the image icon for Left swipe
                        c.drawBitmap(icon,
                                (float) itemView.getRight() - convertDpToPx(16) - icon.getWidth(),
                                (float) itemView.getTop() + ((float) itemView.getBottom() - (float) itemView.getTop() - icon.getHeight())/2,
                                p);
                    }

                    // Fade out the view as it is swiped out of the parent's bounds
                    final float alpha = ALPHA_FULL - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);

                } else {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }

            private int convertDpToPx(int dp){
                return Math.round(dp * (getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                String heroName = heroes.getHeroes().get(viewHolder.getAdapterPosition()).getNickname();
                Bundle args = new Bundle();
                args.putString("hero", heroName);

                if(direction == ItemTouchHelper.LEFT) {
                    HeroDescFragment frgm = new HeroDescFragment();
                    frgm.setArguments(args);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, frgm)
                            .addToBackStack("HEROES_DESC")
                            .commit();
                }
                if (direction == ItemTouchHelper.RIGHT) {
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

        Button buttonMap = (Button) mView.findViewById(R.id.button_map);

        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                intent.putExtra("HEROES", heroes);
                startActivity(intent);
            }
        });

        return mView;
    }
}
