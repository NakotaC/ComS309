package com.example.androidapp.Leaderboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.androidapp.Connectivity.VolleySingleton;
import com.example.androidapp.MainAuth.HomeActivity;
import com.example.androidapp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * The following method is the main method. It is invoked whenever the leadeboard screen starts up.
 */
public class LeaderboardActivity extends AppCompatActivity implements View.OnClickListener
{

    //variable declaration
    public Button button1;
    public TextView text1;
    public Button button2;
    public Button button3;
    private MaterialToolbar toolbar1;
    private MaterialButton addWins;
    private EditText textInput1;
    private EditText textInput2;
    private ListAdapterLeaderboard adapter1;
    private ListView list1;
    private LinkedList clanListRandom;
    private static final String URL_LEADERBOARD_JSON_ARRAY =
            "http://coms-309-033.class.las.iastate.edu:8080/users";
    private static final String URL_WINS_JSON_ARRAY =
            "http://coms-309-033.class.las.iastate.edu:8080/wins";
    private static final String URL_POST_REQUEST =
            "http://coms-309-033.class.las.iastate.edu:8080/wins/";

    /**
     * The following method is used to initialize all the elements of the screen. Also, it is used
     * to invoke a layout on a screen.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        toolbar1 = findViewById(R.id.toolbar1);
        addWins = findViewById(R.id.addWinsButton);
        textInput1 = findViewById(R.id.editTextUserId);
        textInput2 = findViewById(R.id.editTextAmount);
        list1 = findViewById(R.id.list1);


        toolbar1.setOnClickListener(this);
        addWins.setOnClickListener(this);


        adapter1 = new ListAdapterLeaderboard(this, new LinkedList<>());
        list1.setAdapter(adapter1);

        makeLeaderboardJsonArrayReq();


    }

    /**
     * The following method is used whenever a user clicks the back button or the add wins button.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id1 = v.getId();
        if (id1 == R.id.toolbar1) {
            startActivity(new Intent(LeaderboardActivity.this, HomeActivity.class));
        }
        else if (id1 == R.id.addWinsButton)
        {
        postRequest();
        }
    }

    /**
     * The following method is used to parse user information from backend to fill up the
     * stats(leaderboard) table. It uses Volley library for such purposes.
     */
    private void makeLeaderboardJsonArrayReq() {
        LinkedList<String> names = new LinkedList();
        LinkedList<String> wins = new LinkedList();
        JsonArrayRequest jsonArrReq1 = new JsonArrayRequest(
                Request.Method.GET,
                URL_LEADERBOARD_JSON_ARRAY,
                null, // Pass null as the request body since it's a GET request
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Volley Response", response.toString());
                        System.out.println(response);

                        // Parse the JSON array and add data to the adapter
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                names.add(i, jsonObject.getString("username"));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
//                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
//                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
//                params.put("param1", "value1");
//                params.put("param2", "value2");
                return params;
            }
        };

        JsonArrayRequest jsonArrReq2 = new JsonArrayRequest(
                Request.Method.GET,
                URL_WINS_JSON_ARRAY,
                null, // Pass null as the request body since it's a GET request
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Volley Response", response.toString());
                        System.out.println(response);

                        // Parse the JSON array and add data to the adapter
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                wins.add(i, jsonObject.getString("wins"));

                                // Create a ListItemObject and add it to the adapter
                                LeaderboardItemObject item = new LeaderboardItemObject(names.get(i),
                                        wins.get(i));
                                adapter1.add(item);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                });

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrReq1);
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrReq2);
    }


    /**
     * The following method is used to add a user to the leaderboard screen. It sends a post request
     * to the backend server.
     */
    private void postRequest() {

        // Convert input to JSONObject
        JSONObject postBody = null;
        String finalUrlPostRequest = "";
        try {
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            finalUrlPostRequest = URL_POST_REQUEST + textInput1.getText().toString() + "/";
            finalUrlPostRequest = finalUrlPostRequest + textInput2.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                finalUrlPostRequest,
                postBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley Response to POST", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                //                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //                params.put("param1", "value1");
                //                params.put("param2", "value2");
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
