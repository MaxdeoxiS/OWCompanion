package com.example.maxime.overwatchstats.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.fragments.HeroDescFragment;
import com.example.maxime.overwatchstats.model.Hero;
import com.example.maxime.overwatchstats.model.Heroes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static com.example.maxime.overwatchstats.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        MyInfoWindowAdapter() {
            myContentsView = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoContents(Marker marker) {

            TextView tvTitle = ((TextView) myContentsView.findViewById(R.id.title));
            tvTitle.setText(marker.getTitle());
            TextView tvSnippet = ((TextView) myContentsView.findViewById(R.id.snippet));
            tvSnippet.setText(marker.getSnippet());

            return myContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }

    }

    private GoogleMap mMap;
    private Marker currentMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
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

        mMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
        mMap.setOnInfoWindowClickListener(this);
        // Loop through heroes and display them on map
        Heroes heroes = (Heroes) getIntent().getSerializableExtra("HEROES");

        for (Hero hero : heroes.getHeroes()) {
            addHeroMarker(hero);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (currentMarker != null) {
                    currentMarker.setIcon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons((String) (((ArrayList) currentMarker.getTag()).get(1)), 80, 80)));
                }
                currentMarker = marker;
                LatLng position = (LatLng) (((ArrayList) marker.getTag()).get(0));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 6));

                marker.setIcon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons((String) (((ArrayList) marker.getTag()).get(1)), 300, 300)));
                marker.showInfoWindow();

                mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                    @Override
                    public void onCameraMove() {
                        currentMarker.setIcon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons((String) (((ArrayList) currentMarker.getTag()).get(1)), 80, 80)));
                    }
                });

                return true;
            }

        });
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //mMap.getUiSettings().setAllGesturesEnabled(false);
        Bundle args = new Bundle();
        args.putString("nickname", (String) (((ArrayList) marker.getTag()).get(1)));
        HeroDescFragment frgm = new HeroDescFragment();
        frgm.setArguments(args);

        getSupportFragmentManager().beginTransaction().replace(R.id.map, frgm)
                .addToBackStack("HERO_DESC")
                .commit();

    }

    public void addHeroMarker(Hero hero) {
        LatLng location = new LatLng(hero.getX(), hero.getY());
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(location)
                .title(hero.getNickname())
                .snippet(hero.getFirstName() + " " + hero.getLastName())
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(hero.getNickname().toLowerCase(), 80, 80)))
        );
        ArrayList tags = new ArrayList<>();
        tags.add(0, location);
        tags.add(1, hero.getNickname().toLowerCase());
        marker.setTag(tags);
    }

    public Bitmap resizeMapIcons(String iconName, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "drawable", getPackageName()));
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false);
    }
}
