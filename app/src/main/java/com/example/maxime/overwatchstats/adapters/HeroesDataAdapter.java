package com.example.maxime.overwatchstats.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.model.Hero;

import java.util.List;

public class HeroesDataAdapter extends RecyclerView.Adapter<HeroesDataAdapter.HeroViewHolder> {

    public static class HeroViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        HeroViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.hero_name);
            personAge = (TextView)itemView.findViewById(R.id.hero_familyname);
            personPhoto = (ImageView)itemView.findViewById(R.id.hero_pic);
        }
    }
    private List<Hero> heroes;
    Context c;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HeroesDataAdapter(List<Hero> myDataset, Context c) {
        heroes = myDataset;
        this.c = c;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HeroViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hero, parent, false);
        HeroViewHolder pvh = new HeroViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(HeroViewHolder heroViewHolder, int i) {
        Bitmap iconHero = BitmapFactory.decodeResource(
                c.getResources(), c.getResources().getIdentifier(heroes.get(i).getNickname().toLowerCase(), "drawable", c.getPackageName()));

        heroViewHolder.personPhoto.setImageBitmap(Bitmap.createScaledBitmap(iconHero, 100, 100, false));
        heroViewHolder.personName.setText(heroes.get(i).getNickname());
        heroViewHolder.personAge.setText(heroes.get(i).getFirstName() + " " + heroes.get(i).getLastName());
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return heroes.size();
    }
}