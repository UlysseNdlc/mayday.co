package introduction.cours.androidstudio.introduction;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class SecondActivity extends Activity implements OnClickListener, LocationListener {
    Button btnStop;
    TextView textViewTime;
    private LocationManager lManager;
    // private String choix_source = "";
    private Location location;
    private double latitude;
    private double longitude;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //On spécifie que l'on va avoir besoin de gérer l'affichage du cercle de chargement
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.content_second);
        btnStop = (Button) findViewById(R.id.btnStop);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        textViewTime.setText("00:00:10s");

        final CounterClass timer = new CounterClass(10000,1000);

        timer.start();

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Auto-generate method stub
                timer.cancel();
                textViewTime.setText("Alerte Annulée !");
                longitude=0;
                latitude=0;
                btnStop.setVisibility(View.GONE);
            }
        });

        lManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (lManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // choix_source = "NETWORK_PROVIDER";
            //On demande au service de localisation de nous notifier tout changement de position
            //sur la source (le provider) choisie, toute les minutes (60000millisecondes).
            //Le paramètre this spécifie que notre classe implémente LocationListener et recevra
            //les notifications.
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 0, this);
        }

        reinitialisationEcran();

        //On affecte un écouteur d'évènement aux boutons
        // findViewById(R.id.choix_source).setOnClickListener(this);

        findViewById(R.id.afficherCarte).setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")

    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            //TODO auto-generate constructor stub
        }

        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @SuppressLint("NewApi")

        @Override
        public void onTick(long millisUntilFinished) {

            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);
            textViewTime.setText(hms);
        }

        @Override
        public void onFinish() {
            //TODO Auto-generated
            textViewTime.setText("Coordonnées transmises");
            btnStop.setVisibility(View.GONE);
        }
    }
    //Méthode déclencher au clique sur un bouton
    public void onClick(View v) {
        switch (v.getId()) {
            // case R.id.choix_source:
            //choisirSource();
            // break;
            // case R.id.confirmerDanger:
            // afficherLocation();
            // abonnementNetwork();
            //break;
            case R.id.afficherCarte:
                Intent i = new Intent(SecondActivity.this, MapsActivity.class);
                i.putExtra("my_latitude", latitude);
                i.putExtra("my_longitude", longitude);
                startActivity(i);

                break;
            default:
                break;
        }
    }

    //Réinitialisation de l'écran
    private void reinitialisationEcran() {
        ((TextView) findViewById(R.id.latitude)).setText("0.0");
        ((TextView) findViewById(R.id.longitude)).setText("0.0");

        ((TextView) findViewById(R.id.adresse)).setText("");


        findViewById(R.id.afficherCarte).setEnabled(false);
    }

    private void abonnementGps() {
        //on démarre le cercle de chargement
        setProgressBarIndeterminateVisibility(true);

        //On demande au service de localisation de nous notifier tout changement de position
        //sur la source (le provider) choisie, toute les minutes (60000millisecondes).
        //Le paramètre this spécifie que notre classe implémente LocationListener et recevra
        //les notifications.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, this);
    }

    private void abonnementNetwork() {
        //on démarre le cercle de chargement
        setProgressBarIndeterminateVisibility(true);

        //On demande au service de localisation de nous notifier tout changement de position
        //sur la source (le provider) choisie, toute les minutes (60000millisecondes).
        //Le paramètre this spécifie que notre classe implémente LocationListener et recevra
        //les notifications.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 0, this);
    }
    private void afficherLocation() {
        //On affiche les informations de la position a l'écran
        ((TextView) findViewById(R.id.latitude)).setText(String.valueOf(location.getLatitude()));
        ((TextView) findViewById(R.id.longitude)).setText(String.valueOf(location.getLongitude()));


        setProgressBarIndeterminateVisibility(true);

        //Le geocoder permet de récupérer ou chercher des adresses
        //gràce à un mot clé ou une position
        Geocoder geo = new Geocoder(SecondActivity.this);
        try {
            //Ici on récupère la premiere adresse trouvé gràce à la position que l'on a récupéré
            List
                    <Address> adresses = geo.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);

            if (adresses != null && adresses.size() == 1) {
                Address adresse = adresses.get(0);
                //Si le geocoder a trouver une adresse, alors on l'affiche
                ((TextView) findViewById(R.id.adresse)).setText(String.format("%s, %s %s",
                        adresse.getAddressLine(0),
                        adresse.getPostalCode(),
                        adresse.getLocality()));
            } else {
                //sinon on affiche un message d'erreur
                ((TextView) findViewById(R.id.adresse)).setText("L'adresse n'a pu être déterminée");
            }
        } catch (IOException e) {
            e.printStackTrace();
            ((TextView) findViewById(R.id.adresse)).setText("L'adresse n'a pu être déterminée");
        }
        //on stop le cercle de chargement
        setProgressBarIndeterminateVisibility(false);
    }

    public void onLocationChanged(Location location) {
        latitude=location.getLatitude();
        longitude=location.getLongitude();
        //Lorsque la position change...
        Log.i("Tuto géolocalisation", "La position a changé.");
        //... on stop le cercle de chargement
        setProgressBarIndeterminateVisibility(false);
        //... on active le bouton pour afficher l'adresse
        findViewById(R.id.afficherCarte).setEnabled(true);
        //... on sauvegarde la position
        this.location = location;
        //... on l'affiche
        afficherLocation();
        //... et on spécifie au service que l'on ne souhaite plus avoir de mise à jour
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lManager.removeUpdates(this);
    }

    public void onProviderDisabled(String provider) {
        //Lorsque la source (GSP ou réseau GSM) est désactivé
        Log.i("Tuto géolocalisation", "La source a été désactivé");
        //...on affiche un Toast pour le signaler à l'utilisateur
        Toast.makeText(SecondActivity.this,
                String.format("La source \"%s\" a été désactivé", provider),
                Toast.LENGTH_SHORT).show();
        //... et on spécifie au service que l'on ne souhaite plus avoir de mise à jour
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lManager.removeUpdates(this);
        //... on stop le cercle de chargement
        setProgressBarIndeterminateVisibility(false);
    }

    public void onProviderEnabled(String provider) {
        Log.i("Tuto géolocalisation", "La source a été activé.");
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i("Tuto géolocalisation", "Le statut de la source a changé.");
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Second Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://introduction.cours.androidstudio.introduction/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Second Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://introduction.cours.androidstudio.introduction/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}