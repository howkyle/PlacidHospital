package com.comp3161.placidandroid.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.comp3161.placidandroid.R;
import com.comp3161.placidandroid.adapters.PatientListAdapter;
import com.comp3161.placidandroid.models.Patient;
import com.comp3161.placidandroid.rest.RestPatient;
import com.comp3161.placidandroid.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stone on 4/3/17.
 */

public class PatientsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener {

    View view;
    RecyclerView recyclerView;
    PatientListAdapter mPatientListAdapter;
    TextView mTvPlaceHolder;
    ImageView mImgPlaceHolder;
    ProgressBar mProgressBar;
    List<Patient> mPatients=new ArrayList<>();
    String mUrl = Constants.VIEW_PATIENTS;
    SwipeRefreshLayout swipeRefreshLayout;
    private MenuItem searchitem;
    private SearchView searchView;

    

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_patient,container,false);

        initViews();

        setUpSwipeRefresh();

        setUpProgressBar();

        setUpRecyclerView();



        new LoadPatientsTask(getActivity(),mUrl).execute();

        return view;

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

            searchitem = menu.findItem(R.id.action_search);
            searchView = (SearchView) MenuItemCompat.getActionView(searchitem); // set up search view

            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            searchView.setOnQueryTextListener(this);
            searchView.setQueryHint("Search name");

            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    searchView.setQuery("", false); // clears text from search view
                    return true;
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.patient_filter:
                showDialog();
                return true;

            default:
                break;
        }

        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<Patient> filteredModelList = filter(mPatients,query );
        mPatientListAdapter.animateTo(filteredModelList,query);
        recyclerView.scrollToPosition(0);
        return true;

    }

    private List<Patient> filter(List<Patient> models, String query) {
        query = query.toLowerCase();
        final List<Patient> filteredModelList = new ArrayList<>();
        List<Patient> startModelList = new ArrayList<>();
        for (Patient model : models) {
            final String text = model.getFirst_name()+ " " + model.getLast_name();
            if(text.startsWith(query)){
                startModelList.add(model);
            }else if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        startModelList.addAll(filteredModelList);
        return startModelList;
    }

    private void showDialog()
    {
        LayoutInflater li = LayoutInflater.from(view.getContext());
        View promptsView = li.inflate(R.layout.dialog_patient_filter_diagnosis, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                view.getContext());

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText editTextDiagnosis = (EditText)promptsView.findViewById(R.id.editTextDiagnosis);
        //final EditText editTextTitle = (EditText)promptsView.findViewById(R.id.editTextPostTitle);
        final EditText editTextStartDate = (EditText)promptsView.findViewById(R.id.editTextStartDate);

        final EditText editTextEndDate = (EditText)promptsView.findViewById(R.id.editTextEndDate);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        null)
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {

                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                        String diagnosis = editTextDiagnosis.getText().toString();
                        String start_date = editTextStartDate.getText().toString();
                        String end_date = editTextEndDate.getText().toString();

                        if(TextUtils.isEmpty(diagnosis)){
                            Toast.makeText(view.getContext(),"Enter diagnosis",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(TextUtils.isEmpty(start_date)){
                            Toast.makeText(view.getContext(),"Enter start date",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(TextUtils.isEmpty(end_date)){
                            Toast.makeText(view.getContext(),"Enter end date",Toast.LENGTH_SHORT).show();
                            return;
                        }


                        sendQueryData(diagnosis,start_date,end_date);
                        dialog.dismiss();





                    }
                });
            }
        });

        // show it
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

    }


    private void setUpSwipeRefresh() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary,
                R.color.colorPrimaryDark);

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void sendQueryData(String diagnosis,String start_date,String end_date)
    {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("diagnosis",diagnosis);
            jsonObject.put("start_date",start_date);
            jsonObject.put("end_date",end_date);

            mPatients = new ArrayList<>();
            mPatientListAdapter.updatePatientList(mPatients);
            new LoadPatientsQueryTask(getActivity(),Constants.FILTER_DIAGNOIS,
                    jsonObject.toString()).execute();
        }catch (JSONException j){
            Log.d("JSONException",j.getMessage());
        }
    }



    private void initViews()
    {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.member_listview);
        mTvPlaceHolder = (TextView) view.findViewById(R.id.txt_notpresent);
        mImgPlaceHolder = (ImageView) view.findViewById(R.id.no_members);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBarMember);
    }

    private void setUpProgressBar()
    {
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.GONE);

    }

    private void setUpRecyclerView()
    {
        mPatientListAdapter = new PatientListAdapter(getActivity(),mPatients);
        recyclerView.setAdapter(mPatientListAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private List<Patient> getPatientsFromJson(String json)
    {
        List<Patient> patients=new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String first_name = jsonObject.getString("first_name");
                String last_name = jsonObject.getString("last_name");
                String dob = jsonObject.getString("dob");
                String address = jsonObject.getString("address");
                String tel_number = jsonObject.getString("tel_number");
                Patient patient = new Patient(id,first_name,last_name,tel_number,dob,address);
                patients.add(patient);
            }

        }catch (JSONException j){
            Log.d("Exception",j.getMessage());
            return null;
        }

        return patients;
    }

    @Override
    public void onRefresh() {
        new LoadPatientsTask(getActivity(),mUrl).execute();
    }

    private class LoadPatientsTask extends AsyncTask<Void,Void,Boolean>
    {

        Context context;
        String url;
        public LoadPatientsTask(Context context,String url)
        {
            this.context = context;
            this.url = url;

        }
        @Override
        protected void onPreExecute() {
            mImgPlaceHolder.setVisibility(View.GONE);
            mTvPlaceHolder.setVisibility(View.GONE);

            if (mPatients.size() == 0) { // check if any news are present
                mProgressBar.setVisibility(View.VISIBLE);

            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            RestPatient restPatient = new RestPatient();
            String response = restPatient.getPatients(url+"/100");
            Log.d("Patients",response);
            if(TextUtils.isEmpty(response))
            {
                return false;
            }
            mPatients = getPatientsFromJson(response);

            if(mPatients==null || mPatients.size()==0)
            {
                return false;
            }

            return true;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            swipeRefreshLayout.setRefreshing(false);
            mProgressBar.setVisibility(View.GONE);
            if (result) {
                Toast.makeText(context,mPatients.size()+"",Toast.LENGTH_SHORT).show();
                mPatientListAdapter.updatePatientList(mPatients);
            }

            if (mPatients==null || mPatients.size() == 0) {
                mImgPlaceHolder.setVisibility(View.VISIBLE);
                mTvPlaceHolder.setVisibility(View.VISIBLE);
            }
        }


    }

    private class LoadPatientsQueryTask extends AsyncTask<Void,Void,Boolean>
    {

        Context context;
        String url;
        String json;
        public LoadPatientsQueryTask(Context context,String url,String json)
        {
            Toast.makeText(context,"Querying",Toast.LENGTH_SHORT).show();
            this.context = context;
            this.url = url;
            this.json = json;

        }
        @Override
        protected void onPreExecute() {
            mImgPlaceHolder.setVisibility(View.GONE);
            mTvPlaceHolder.setVisibility(View.GONE);

            if (mPatients.size() == 0) { // check if any news are present
                mProgressBar.setVisibility(View.VISIBLE);

            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            RestPatient restPatient = new RestPatient();
            String response = restPatient.registerPatient(url,json);
            Log.d("Patients",response);
            if(TextUtils.isEmpty(response))
            {
                return false;
            }
            mPatients = getPatientsFromJson(response);

            if(mPatients==null || mPatients.size()==0)
            {
                return false;
            }

            return true;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            swipeRefreshLayout.setRefreshing(false);
            mProgressBar.setVisibility(View.GONE);
            if (result) {
                mPatientListAdapter.updatePatientList(mPatients);
            }

            if (mPatients==null || mPatients.size() == 0) {
                mImgPlaceHolder.setVisibility(View.VISIBLE);
                mTvPlaceHolder.setVisibility(View.VISIBLE);
            }
        }


    }




}
