package com.example.tripplanner.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tripplanner.R;
import com.google.android.material.snackbar.Snackbar;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.CustomZoomButtonsController;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RouteActivity extends AppCompatActivity {

    private MapView map;
    private double startLat, startLon, endLat, endLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().setUserAgentValue(getPackageName());
        setContentView(R.layout.activity_route);

        map = findViewById(R.id.mapView);
        map.setMultiTouchControls(true);
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);

        // Get route coordinates from intent
        startLat = getIntent().getDoubleExtra("startLat", 19.0760);
        startLon = getIntent().getDoubleExtra("startLon", 72.8777);
        endLat = getIntent().getDoubleExtra("endLat", 18.5204);
        endLon = getIntent().getDoubleExtra("endLon", 73.8567);

        IMapController mapController = map.getController();
        mapController.setZoom(8.0);
        mapController.setCenter(new GeoPoint(startLat, startLon));

        // Fetch route from local GraphHopper server
        fetchRouteFromServer(startLat, startLon, endLat, endLon);
    }

    private void fetchRouteFromServer(double startLat, double startLon, double endLat, double endLon) {
        new Thread(() -> {
            try {
                String serverUrl = "http://10.0.2.2:8989/route";
                String query = String.format(
                        "%s?point=%f,%f&point=%f,%f&profile=car&points_encoded=false",
                        serverUrl, startLat, startLon, endLat, endLon
                );

                URL url = new URL(query);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                InputStream in = new BufferedInputStream(conn.getInputStream());
                StringBuilder result = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                conn.disconnect();
                parseAndDrawGeoJson(result.toString());

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Failed to fetch route", Toast.LENGTH_LONG).show());
            }
        }).start();
    }

    private void parseAndDrawGeoJson(String jsonResponse) {
        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray paths = json.getJSONArray("paths");
            if (paths.length() == 0) return;

            JSONObject path = paths.getJSONObject(0);
            JSONArray points = path.getJSONObject("points").getJSONArray("coordinates");

            List<GeoPoint> geoPoints = new ArrayList<>();
            for (int i = 0; i < points.length(); i++) {
                JSONArray coord = points.getJSONArray(i);
                double lon = coord.getDouble(0);
                double lat = coord.getDouble(1);
                geoPoints.add(new GeoPoint(lat, lon));
            }

            runOnUiThread(() -> drawRoute(geoPoints));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawRoute(List<GeoPoint> geoPoints) {
        Polyline routeOverlay = new Polyline();
        routeOverlay.setPoints(geoPoints);
        routeOverlay.getOutlinePaint().setColor(Color.BLUE);
        routeOverlay.getOutlinePaint().setStrokeWidth(10f);

        map.getOverlays().add(routeOverlay);
        map.invalidate();

        Snackbar.make(map, "Route loaded from server!", Snackbar.LENGTH_LONG).show();
    }
}
