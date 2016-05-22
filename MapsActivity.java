package introduction.cours.androidstudio.introduction;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    double longitude;
    double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle extras = getIntent().getExtras();
        longitude = extras.getDouble("my_longitude");
        latitude = extras.getDouble("my_latitude");

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

        // Add a marker in Sydney and move the camera
        LatLng marqueur1 = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(marqueur1).title("Danger Signalé à cette position !"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marqueur1));


        LatLng marqueur2 = new LatLng(48.814846, 2.378454);
        mMap.addMarker(new MarkerOptions().position(marqueur2).title("Danger Signalé à cette position !"));

        LatLng marqueur3 = new LatLng(48.814789, 2.378518);
        mMap.addMarker(new MarkerOptions().position(marqueur3).title("Danger Signalé à cette position !"));

        LatLng marqueur4 = new LatLng(48.814521, 2.378797);
        mMap.addMarker(new MarkerOptions().position(marqueur4).title("Danger Signalé à cette position !"));

    }
}
