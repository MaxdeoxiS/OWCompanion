package com.example.maxime.overwatchstats.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.maxime.overwatchstats.R;


public class DialogCompareFriendsTimePlayed extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction

            String[] friends = getArguments().getStringArray("friends");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setItems(friends, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new CompareHeroesTimePlayedFragment())
                            .addToBackStack("COMPARE")
                            .commit();
                }
            });

            // Create the AlertDialog object and return it
            return builder.create();
        }
}
