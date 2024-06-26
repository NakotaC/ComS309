package com.example.androidapp.MainAuth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.androidapp.Connectivity.VolleySingleton;
import com.example.androidapp.Game.User;
import com.example.androidapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to handle the Login screen
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * var for the login button
     */
    private Button loginBtn;
    /**
     * var for the back button
     */
    private Button backBtn;
    /**
     * var for the header text
     */
    private TextView header;
    /**
     * var for the username entry field
     */
    private EditText usernameEntry;
    /**
     * var for the wrong password text
     */
    private TextView wrongPassTxt;
    /**
     * var for the username string
     */
    private String username;
    /**
     * var for the password string
     */
    private String password;
    int userId;
    /**
     * var for the password entry field
     */
    private EditText passwordEntry;
    JSONArray inventory, equippedItems, quests;
    JSONObject userObj;
    public static int questNumber;
    /**
     * var for the URL string
     */
    //private static final String URL_STRING_REQ = "https://ed481f0d-bd99-4a49-8fe0-e84d74d506f6.mock.pstmn.io/login11";
    private static final String URL_STRING_REQ = "http://coms-309-033.class.las.iastate.edu:8080/users/login";

    /**
     * initializes the screen and the elements to make it operate
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.login_button);
        backBtn = findViewById(R.id.back_button);
        header = findViewById(R.id.header);
        passwordEntry = findViewById(R.id.password_entry);
        usernameEntry = findViewById(R.id.username_entry);
        wrongPassTxt = findViewById(R.id.wrongPass);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               password = passwordEntry.getText().toString();
               username = usernameEntry.getText().toString();
               makeUserReq();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

    }

    private void makeUserReq() {
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "http://coms-309-033.class.las.iastate.edu:8080/users/login",
                //URL_STRING_REQ,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the successful response here
                        Log.d("Volley Response", response.toString());


                        if(response.has("username")){
                            User user = null;
                            try {
                                userId = response.getInt("id");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            userObj = response;
                              inventoryRequest();


                        }else{
                            wrongPassTxt.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Log.e("Volley Error", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("username", username);
                headers.put("password", password);
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

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(objectRequest);
    }

    private void inventoryRequest() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(
                Request.Method.GET,
                "http://coms-309-033.class.las.iastate.edu:8080/inventory/" + userId,
                //"https://ed481f0d-bd99-4a49-8fe0-e84d74d506f6.mock.pstmn.io/inventory/1",
                 null,
                 // Pass null as the request body since it's a GET request
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Volley Response", response.toString());


                            inventory = response;
                          equippedItemRequest();


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

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrReq);
    }
    private void equippedItemRequest() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(
                Request.Method.GET,
                "http://coms-309-033.class.las.iastate.edu:8080/equippedItems/" + userId,
                //"https://ed481f0d-bd99-4a49-8fe0-e84d74d506f6.mock.pstmn.io/equippedItems/1",
                null, // Pass null as the request body since it's a GET request
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Volley Response", response.toString());

                        equippedItems = response;
                        questRequest();


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

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrReq);
    }
    private void questRequest() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(
                Request.Method.GET,
                "http://coms-309-033.class.las.iastate.edu:8080/quest/" + userId + "/",
                //"https://ed481f0d-bd99-4a49-8fe0-e84d74d506f6.mock.pstmn.io/equippedItems/1",
                null, // Pass null as the request body since it's a GET request
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Volley Response", response.toString());

                        quests = response;
                        try
                        {
                            questNumber = quests.getInt(0);
                        }
                        catch (JSONException e)
                        {
                            throw new RuntimeException(e);
                        }
                        questPutRequest();
                        login();

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

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrReq);
    }
    private void questPutRequest() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(
                Request.Method.PUT,
                "http://coms-309-033.class.las.iastate.edu:8080/quest/" + userId + "/",
                //"https://ed481f0d-bd99-4a49-8fe0-e84d74d506f6.mock.pstmn.io/equippedItems/1",
                null, // Pass null as the request body since it's a GET request
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Volley Response", response.toString());


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

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrReq);
    }
    private void login(){
        User user = new User();
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        try{
        user = new User(userObj, inventory, equippedItems, quests);
    } catch (JSONException e) {
        throw new RuntimeException(e);
    }
        intent.putExtra("USEROBJ", user);

    startActivity(intent);
    }

}

