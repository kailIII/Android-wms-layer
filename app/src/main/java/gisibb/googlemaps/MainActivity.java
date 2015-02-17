package gisibb.googlemaps;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;

public class MainActivity extends FragmentActivity {
    protected static final String LOG_TAG = "ExampleApp";

    private GoogleMap map;
    private TileOverlay tileOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            if (map != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(41.0230, 28.9372), 10));

        TileProvider tileProvider = WMSTileProviderFactory.getTileProvider(providers[0]);
        tileOverlay = map.addTileOverlay(new TileOverlayOptions().tileProvider(tileProvider));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TileProvider tileProvider;
        switch (item.getItemId()) {
            case R.id.menu_google:
                tileOverlay.remove();
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                return true;
            case R.id.menu_sehirharitasi:
                tileOverlay.remove();
                map.setMapType(GoogleMap.MAP_TYPE_NONE);
                tileProvider = WMSTileProviderFactory.getTileProvider(providers[0]);
                tileOverlay = map.addTileOverlay(new TileOverlayOptions()
                        .tileProvider(tileProvider));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(41.0230, 28.9372), 10));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private WMSProvider[] providers = {
           new WMSProvider()
                   //.url must be wms service url
                    .url("http://*****************************************/MapServer/WMSServer")
                    .crs("EPSG:3857")
                    .layers("0"),

    };
}