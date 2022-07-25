package com.example.covid_19tracker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19tracker.classes.Delta;
import com.example.covid_19tracker.classes.District;
import com.example.covid_19tracker.classes.DistrictData;
import com.example.covid_19tracker.classes.State;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "tag";
    RecyclerView recView;
    FloatingActionButton fabUp;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recView = findViewById(R.id.recView);
        fabUp = findViewById(R.id.fabUp);

        RequestQueue rQueue = Volley.newRequestQueue(this);

        String jsonUrl = "https://data.covid19india.org/state_district_wise.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, jsonUrl, null, this::fetchData, this::logError);
        rQueue.add(jsonObjectRequest);

        recView.setOnScrollChangeListener((view, i, i1, i2, i3) -> {
            if(i3 == RecyclerView.SCROLL_STATE_IDLE) fabUp.setVisibility(View.GONE);
            else fabUp.setVisibility(View.VISIBLE);
        });

        fabUp.setOnClickListener(view -> {
            recView.smoothScrollToPosition(0);
            fabUp.setVisibility(View.GONE);
        });
    }

    private void logError(VolleyError error) {
        Log.e(TAG, "error: " + error.getLocalizedMessage());
        Toast.makeText(this, "Could not fetch the data..", Toast.LENGTH_LONG).show();
    }

    private void fetchData(JSONObject response) {
        ArrayList<State> states = new ArrayList<>();

        Iterator<String> statesKeySet = response.keys();

        while (statesKeySet.hasNext()) {
            String stateKey = statesKeySet.next();
            ArrayList<District> districts = new ArrayList<>();
            Log.d(TAG, stateKey);

            try {
                JSONObject state = response.getJSONObject(stateKey);
                JSONObject districtObj = state.getJSONObject("districtData");
                Iterator<String> districtsKeySet = districtObj.keys();

                String statecode = state.getString("statecode");

                while (districtsKeySet.hasNext()) {
                    String districtKey = districtsKeySet.next();

                    JSONObject districtDataObj = districtObj.getJSONObject(districtKey);
                    DistrictData districtData = getDistrictData(districtDataObj);

                    districts.add(new District(districtKey, districtData));
                }

                states.add(new State(stateKey, districts, statecode));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // it was an unassigned state
        states.remove(0);

        // states array should be filled by now
        recView.setAdapter(new RecViewAdapter(states, this));
        recView.setLayoutManager(new LinearLayoutManager(this));
    }

    private DistrictData getDistrictData(JSONObject districtDataObj) throws JSONException {
         return new DistrictData(
                districtDataObj.getString("notes"),
                districtDataObj.getInt("active"),
                districtDataObj.getInt("confirmed"),
                districtDataObj.getInt("migratedother"),
                districtDataObj.getInt("deceased"),
                districtDataObj.getInt("recovered"),
                getDelta(districtDataObj.getJSONObject("delta"))
        );
    }

    private Delta getDelta(JSONObject delta) throws JSONException {
        return new Delta(
                delta.getInt("confirmed"),
                delta.getInt("deceased"),
                delta.getInt("recovered")
        );
    }
}