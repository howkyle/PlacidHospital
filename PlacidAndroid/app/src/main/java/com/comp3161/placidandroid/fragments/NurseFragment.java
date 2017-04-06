package com.comp3161.placidandroid.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.comp3161.placidandroid.adapters.NurseListAdapter;
import com.comp3161.placidandroid.models.Nurse;
import com.comp3161.placidandroid.models.Patient;
import com.comp3161.placidandroid.rest.Rest;
import com.comp3161.placidandroid.rest.RestPatient;
import com.comp3161.placidandroid.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stone on 4/8/17.
 */

public class NurseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    View view;
    RecyclerView recyclerView;
    NurseListAdapter mNurseListAdapter;
    TextView mTvPlaceHolder;
    ImageView mImgPlaceHolder;
    ProgressBar mProgressBar;
    List<Nurse> mNurses =new ArrayList<>();
    String mUrl = Constants.VIEW_NURSES;
    SwipeRefreshLayout swipeRefreshLayout;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_nurse,container,false);

        initViews();

        setUpSwipeRefresh();

        setUpProgressBar();

        setUpRecyclerView();



        new LoadNursesTask(getActivity(),mUrl).execute();

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.filter:
                showDialog();
                return true;

            default:
                break;
        }

        return false;
    }

    private void showDialog()
    {
        LayoutInflater li = LayoutInflater.from(view.getContext());
        View promptsView = li.inflate(R.layout.dialog_nurse_filter, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                view.getContext());

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText editTextPatient = (EditText)promptsView.findViewById(R.id.editTextPatient);
        final EditText editTextDate = (EditText)promptsView.findViewById(R.id.editTextDate);

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
                        String patient = editTextPatient.getText().toString();
                        String date = editTextDate.getText().toString();

                        if(TextUtils.isEmpty(patient)){
                            Toast.makeText(view.getContext(),"Enter patient",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(TextUtils.isEmpty(date)){
                            Toast.makeText(view.getContext(),"Enter date",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        sendQueryData(patient,date);
                        dialog.dismiss();





                    }
                });
            }
        });

        // show it
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void sendQueryData(String patient,String date)
    {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("patient",patient);
            jsonObject.put("date",date);
            mNurses = new ArrayList<>();
            mNurseListAdapter.updateNurseList(mNurses);
            Toast.makeText(getActivity(),"Querying..",Toast.LENGTH_SHORT).show();
            new LoadNursesTask(getActivity(),Constants.NURSE_ADMINISTERED).execute();
        }catch (JSONException j){
            Log.d("JsonException",j.getMessage());
        }
    }




    private void setUpSwipeRefresh() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary,
                R.color.colorPrimaryDark);

        swipeRefreshLayout.setOnRefreshListener(this);
    }





    private void initViews()
    {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.nurse_listview);
        mTvPlaceHolder = (TextView) view.findViewById(R.id.txt_notpresent);
        mImgPlaceHolder = (ImageView) view.findViewById(R.id.no_content);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }

    private void setUpProgressBar()
    {
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.GONE);

    }

    private void setUpRecyclerView()
    {
        mNurseListAdapter = new NurseListAdapter(getActivity(),mNurses);
        recyclerView.setAdapter(mNurseListAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private List<Nurse> getPatientsFromJson(String json)
    {
        List<Nurse> nurses =new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                String name = (String) jsonArray.get(i);
                Nurse nurse = new Nurse(name);
                nurses.add(nurse);
            }

        }catch (JSONException j){
            Log.d("Exception",j.getMessage());
            return null;
        }

        return nurses;
    }

    @Override
    public void onRefresh() {
        new LoadNursesTask(getActivity(),mUrl).execute();
    }

    private class LoadNursesTask extends AsyncTask<Void,Void,Boolean>
    {

        Context context;
        String url;
        public LoadNursesTask(Context context,String url)
        {
            this.context = context;
            this.url = url;

        }
        @Override
        protected void onPreExecute() {
            mImgPlaceHolder.setVisibility(View.GONE);
            mTvPlaceHolder.setVisibility(View.GONE);

            if (mNurses.size() == 0) { // check if any news are present
                mProgressBar.setVisibility(View.VISIBLE);

            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Rest rest = new Rest();
            String response = rest.sendGetRequest(url);
            Log.d("Nurses",response);
            if(TextUtils.isEmpty(response))
            {
                return false;
            }
            mNurses = getPatientsFromJson(response);

            if(mNurses==null || mNurses.size()==0)
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
                mNurseListAdapter.updateNurseList(mNurses);
            }

            if (mNurses==null || mNurses.size() == 0) {
                mImgPlaceHolder.setVisibility(View.VISIBLE);
                mTvPlaceHolder.setVisibility(View.VISIBLE);
            }
        }

    }




}
