package gisibb.googlemaps;

import com.google.android.gms.maps.model.TileProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

/**
 * Created by salih.yalcin on 16.2.2015.
 */
public class WMSTileProviderFactory {

    static final String WMS_SERVICE_PARAMETERS = "?SERVICE=WMS&"
            + "REQUEST=GetMap&"
            + "VERSION=1.1.1&"
            + "FORMAT=image/png&"
            + "WIDTH=256&"
            + "HEIGHT=256&"
            + "TRANSPARENT=true&"
            + "BBOX=%f,%f,%f,%f&"
            + "SRS=%s&"
            + "LAYERS=%s&"
            + "STYLES=%s";

    public static TileProvider getTileProvider(final WMSProvider provider) {
        TileProvider tileProvider = new WMSTileProvider(256,256) {
            @Override
            public synchronized URL getTileUrl(int x, int y, int zoom) {
                double[] bbox = getBoundingBox(x, y, zoom);
                String urlStr = provider.url
                        + String.format(Locale.US, WMS_SERVICE_PARAMETERS,
                        bbox[MINX], bbox[MINY], bbox[MAXX], bbox[MAXY],
                        provider.crs, provider.layers, provider.styles);

                URL url = null;
                try {
                    url = new URL(urlStr);
                } catch (MalformedURLException e) {
                    throw new AssertionError(e);
                }
                return url;
            }
        };

        return tileProvider;
    }
}
