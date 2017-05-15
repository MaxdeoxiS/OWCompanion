package com.example.maxime.overwatchstats.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.model.Hero;
import com.example.maxime.overwatchstats.model.Heroes;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Loop through heroes and display them on map
        Heroes heroes = (Heroes) getIntent().getSerializableExtra("HEROES");

        for (Hero hero : heroes.getHeroes()) {
            addHeroMarker(hero);
        }
    }

    public void addHeroMarker(Hero hero) {
        LatLng location = new LatLng(hero.getX(), hero.getY());
        mMap.addMarker(new MarkerOptions()
                            .position(location)
                            .title(hero.getNickname())
                            .snippet(hero.getFirstName() + " " + hero.getLastName())
                            .icon(BitmapDescriptorFactory.fromResource(
                                    getResources()
                                            .getIdentifier(hero.getNickname().toLowerCase(), "drawable", "com.example.maxime.overwatchstats")))
        );
    }
}
