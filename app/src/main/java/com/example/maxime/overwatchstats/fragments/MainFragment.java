package com.example.maxime.overwatchstats.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.model.Friend;
import com.example.maxime.overwatchstats.model.FriendDAO;

public class MainFragment extends Fragment {

    FriendDAO f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View mView = inflater.inflate(R.layout.activity_register, container, false);
        
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView menuPseudo = (TextView) headerView.findViewById(R.id.menu_pseudo);
        TextView menuBattleTag = (TextView) headerView.findViewById(R.id.menu_battleTag);
        ImageView menuAvatar = (ImageView) headerView.findViewById(R.id.icon_menu_header);

        f = new FriendDAO(this.getContext());
        f.open();
        //f.emptyTable();
        Friend friend = f.select(1);
        f.close();

        if (null != friend) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("battleTag", friend.getBattleTag());
            editor.putString("currentBattleTag", friend.getBattleTag());
            editor.apply();
            menuPseudo.setText(friend.getUsername());
            menuBattleTag.setText(friend.getBattleTag());
            //Picasso.with(this.getContext()).load(friend.getAvatar()).into(menuAvatar);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new NewsFragment())
                    .commit();
        }

        final Button button_reg = (Button) mView.findViewById(R.id.btnRegister);
        button_reg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View root = v.getRootView();
                if (root != null) {
                    if (((EditText) root.findViewById(R.id.pseudo)).getText().toString().trim().length() > 0)
                    {
                        registerUser(((EditText) root.findViewById(R.id.pseudo)).getText().toString(), ((EditText) root.findViewById(R.id.battleTag)).getText().toString());
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new NewsFragment())
                                .commit();
                    }
                }
            }
        });

        return mView;
    }

    private void registerUser(String pseudo, String battleTag)
    {
        f.open();
        Friend mySelf = new Friend(pseudo, battleTag);
        f.add(mySelf);
        f.close();
    }

}
