package com.h4_technology.eobee.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.h4_technology.eobee.DataHelper;
import com.h4_technology.eobee.R;
import com.h4_technology.eobee.adapter.ProcedureListAdapter;
import com.h4_technology.eobee.model.ProcedureListItem;
import com.h4_technology.eobee.model.ProviderListItem;
import com.h4_technology.eobee.model.TopSearchTermModel;
import com.h4_technology.eobee.model.ZipcodeLatLongModel;
import com.h4_technology.eobee.singleton.NetworkService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final ArrayList<String> ARG_PARAM1 = new ArrayList<>();
    private ProgressBar spin;
    private Spinner spinner;
    private Button b;
    private ListView listView;
    private EditText zipcode;
    private CheckBox useMyLocation;

    private OnFragmentInteractionListener mListener;
    private TopSearchTermModel[] mTopSearchTerms;
    private ZipcodeLatLongModel[] mZipLatLongs;
    private ZipcodeLatLongModel mGeography;
    private ProcedureListItem mProcedureListItem;
    private Activity mActivity;
    private RelativeLayout relativeLayout;
    private ArrayList<String> distances;
    private List<ProcedureListItem> procedureNames;
    private int distance;
    private int position;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param distances Parameter 1.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(ArrayList<String> distances) {

        Log.d("HomeFragment", "newInstance");
        HomeFragment fragment = new HomeFragment();
        //Bundle args = new Bundle();
        //args.putString("distances", distances);
        //fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor

        Log.d("HomeFragment", "default constructor");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Log.d("HomeFragment", "onActivityCreated");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("HomeFragment", "OnCreate");
        /*
        procedureNames.add(new ProcedureListItem("Procedure name 1"));
        procedureNames.add(new ProcedureListItem("Procedure name 2"));
        procedureNames.add(new ProcedureListItem("Procedure name 3"));*/


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("HomeFragment", "OnCreateView");
        View V = inflater.inflate(R.layout.fragment_home, container, false);
        spinner = (Spinner) V.findViewById(R.id.procedures_spinner);
        b = (Button) V.findViewById(R.id.btn_search);
        b.setOnClickListener(this);

        relativeLayout = (RelativeLayout) V.findViewById(R.id.rel_home_layout);
        relativeLayout.setVisibility(View.INVISIBLE);

        this.distances = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.distances)));
        Spinner distanceSpinner = (Spinner) V.findViewById(R.id.distance_spinner);
        ArrayAdapter<CharSequence> distanceAdapter = ArrayAdapter.createFromResource(this.getActivity().getBaseContext(), R.array.distances, R.layout.support_simple_spinner_dropdown_item);
        distanceSpinner.setAdapter(distanceAdapter);

        if (isOnline()){
            spin = (ProgressBar) V.findViewById(R.id.progressBar1);
            spin.setVisibility(View.VISIBLE);

            RequestQueue queue = NetworkService.getInstance(getActivity().getApplicationContext()).getRequestQueue();

            String url = DataHelper.getBaseUrl() + "api/Search";
            JSONArray jsArray = null;
            JsonArrayRequest jsArrRequest = new JsonArrayRequest
                    (Request.Method.GET, url, jsArray, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("Response: ", response.toString());
                            try {
                                if(response.length() > 0) {

                                    mTopSearchTerms = new TopSearchTermModel[response.length()];
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject jsObject = response.getJSONObject(i);
                                        TopSearchTermModel model = new TopSearchTermModel();
                                        model.mSearchTermId = jsObject.getInt("SearchTermId");
                                        model.mSearchTerm = jsObject.getString("SearchTerm");
                                        mTopSearchTerms[i] = model;
                                    }
                                    updateUI();
                                }
                            } catch (JSONException ex) {

                                spin.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Log.e("HomeFragment", error.getMessage());

                            spin.setVisibility(View.GONE);
                        }
                    });


            queue.add(jsArrRequest);

        } else {
            spin.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Network connectivity not detected", Toast.LENGTH_LONG).show();
        }

        return V;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("HomeFragment", "OnAttach");
        if (activity == null) {
            Log.e("HomeFragment","activity was null in onAttach");
        }
        this.mActivity = activity;
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("HomeFragment", "OnDetach");
        mListener = null;
        mActivity = null;
    }

    private void updateUI() {
        Log.d("UpdateUi", "reached Update UI");
        relativeLayout.setVisibility(View.VISIBLE);

        if ( this.getActivity() == null ) {
            Log.e("get activity","Get activity was null");
            Toast.makeText(mActivity, "Null Get Activity detected", Toast.LENGTH_LONG).show();
            spin.setVisibility(View.GONE);
        } else {

            ArrayList<String> proceduresList = new ArrayList<String>();
            for (int i = 0; i < mTopSearchTerms.length; i++) {
                proceduresList.add(i, mTopSearchTerms[i].mSearchTerm);
            }
            ArrayAdapter<String> proceduresAdapter = new ArrayAdapter<String>(this.getActivity().getBaseContext(),
                    android.R.layout.simple_spinner_item,
                    proceduresList);

            spinner.setAdapter(proceduresAdapter);
            spin.setVisibility(View.GONE);
        }
    }


    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_search:

                searchProcedures(
                        this.mTopSearchTerms[spinner.
                                getSelectedItemPosition()]
                );
                break;
            default:
                break;
        }
    }

    private void searchProcedures(TopSearchTermModel item) {
        if (isOnline()){
            spin = (ProgressBar) mActivity.findViewById(R.id.progressBar1);
            spin.setVisibility(View.VISIBLE);

            RequestQueue queue = NetworkService.getInstance(getActivity().getApplicationContext()).getRequestQueue();

            String url = DataHelper.getBaseUrl() + "api/Search/?searchId=" + item.mSearchTermId;
            JSONArray jsArray = null;
            JsonArrayRequest jsArrRequest = new JsonArrayRequest
                    (Request.Method.GET, url, jsArray, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("Response: ", response.toString());
                            try {
                                if(response.length() > 0) {
                                    procedureNames = new ArrayList();
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject jsObject = response.getJSONObject(i);
                                        ProcedureListItem model = new ProcedureListItem(
                                                jsObject.getString("ProcedureDescription"),
                                                jsObject.getInt("ProcedureDescriptionId")
                                        );

                                        procedureNames.add(model);
                                    }
                                    populateList();
                                }
                            } catch (JSONException ex) {

                                spin.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub

                            //spin.setVisibility(View.GONE);
                            Log.e("HomeFragment", error.getMessage());
                        }
                    });


            queue.add(jsArrRequest);

        } else {
            spin.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Network connectivity not detected", Toast.LENGTH_LONG).show();
        }
    }

    private void populateList() {
        listView = (ListView) mActivity.findViewById(R.id.list_procedures);
        listView.setOnItemClickListener(this);
        ProcedureListAdapter adapter = new ProcedureListAdapter(mActivity,this.procedureNames);
        listView.setAdapter(adapter);
        listView.setVisibility(View.VISIBLE);
        spin.setVisibility(View.GONE);
    }



    private void buildProviderFragment(final ZipcodeLatLongModel geography) {

        RequestQueue queue = NetworkService.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        mGeography = geography;
        mProcedureListItem = procedureNames.get(this.position);
        String url = DataHelper.getBaseUrl() + "api/Search/";

        StringRequest myReq = new StringRequest(Request.Method.POST,
                url,
                successListener(),
                errorListener()) {
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ProcedureDescriptionId", String.valueOf(mProcedureListItem.getProcedureDescriptionId()));
                params.put("Latitude", String.valueOf(geography.latitude));
                params.put("Longitude", String.valueOf(geography.longitude));
                params.put("Distance", String.valueOf(distance));
                return params;
            }
        };
        queue.add(myReq);
    }
    public Response.Listener<String> successListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                /*if (mListener != null) {
                    mListener.onProviderSelected(mGeography);
                }*/
                if (response.length() > 0) {
                    Log.d("HomeFragment",response);
                    try {
                        JSONArray providers = new JSONArray(response);
                        ProviderListItem.getNewIstanceList(providers);
                    } catch (JSONException ex) {

                        Log.e("HomeFragment",ex.getMessage());
                        spin = (ProgressBar) mActivity.findViewById(R.id.progressBar1);
                        spin.setVisibility(View.GONE);
                    }
                }
            }


        };
    }
    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spin.setVisibility(View.GONE);
                Log.e("HomeFragment", error.getMessage());
            }
        };

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.position = position;

        this.useMyLocation = (CheckBox) mActivity.findViewById(R.id.chk_use_my_location);
        Spinner distanceSpinner = (Spinner) mActivity.findViewById(R.id.distance_spinner);
        this.distance = Integer.valueOf(distances.get(distanceSpinner.getSelectedItemPosition()));

        if(this.useMyLocation.isChecked()) {
            //use current location
        } else { //get zipcode
            this.zipcode = (EditText) mActivity.findViewById(R.id.txt_zip_code);
            if(this.zipcode.getText().length() <= 0){
                Toast.makeText(mActivity, "zipcode was empty and use my location turned off", Toast.LENGTH_LONG).show();
            } else {
                //zipcode not empty

                relativeLayout.setVisibility(View.INVISIBLE);

                if (isOnline()){
                    spin.setVisibility(View.VISIBLE);

                    RequestQueue queue = NetworkService.getInstance(getActivity().getApplicationContext()).getRequestQueue();

                    String url = DataHelper.getBaseUrl() + "api/geography/?zipcode=" + this.zipcode.getText();
                    JSONArray jsArray = null;
                    JsonArrayRequest jsArrRequest = new JsonArrayRequest
                            (Request.Method.GET, url, jsArray, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    Log.d("Response: ", response.toString());
                                    try {
                                        if(response.length() > 0) {

                                            mZipLatLongs = new ZipcodeLatLongModel[response.length()];
                                            for (int i = 0; i < response.length(); i++) {
                                                JSONObject jsObject = response.getJSONObject(i);
                                                ZipcodeLatLongModel model = new ZipcodeLatLongModel(jsObject.getString("Zipcode"),
                                                        jsObject.getDouble("Latitude"),
                                                        jsObject.getDouble("Longitude"));
                                                mZipLatLongs[i] = model;
                                            }

                                            buildProviderFragment(mZipLatLongs[0]);
                                        }else {

                                            Log.e("HomeFragment onResponse", String.valueOf(response.length()));
                                        }
                                    } catch (JSONException ex) {
                                        spin.setVisibility(View.GONE);
                                        relativeLayout.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO Auto-generated method stub
                                    //spin.setVisibility(View.GONE);
                                    Log.e("HomeFragment", error.getMessage());
                                    relativeLayout.setVisibility(View.VISIBLE);
                                }
                            });


                    queue.add(jsArrRequest);
                } else {
                    spin.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(mActivity, "network down or turned off", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

        public void onProviderSelected(ZipcodeLatLongModel model);
    }



}
