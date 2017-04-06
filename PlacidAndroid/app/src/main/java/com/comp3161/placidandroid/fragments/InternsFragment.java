package com.comp3161.placidandroid.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.comp3161.placidandroid.R;
import com.comp3161.placidandroid.adapters.InternListAdapter;
import com.comp3161.placidandroid.models.Intern;
import com.comp3161.placidandroid.rest.Rest;
import com.comp3161.placidandroid.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stone on 4/8/17.
 */

public class InternsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    View view;
    RecyclerView recyclerView;
    InternListAdapter mInternListAdapter;
    TextView mTvPlaceHolder;
    ImageView mImgPlaceHolder;
    ProgressBar mProgressBar;
    List<Intern> mInterns =new ArrayList<>();
    String mUrl = Constants.VIEW_INTERN;
    SwipeRefreshLayout swipeRefreshLayout;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_interns,container,false);

        initViews();

        setUpSwipeRefresh();

        setUpProgressBar();

        setUpRecyclerView();



        new LoadInternsTask(getActivity(),mUrl).execute();

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
                sendMostPatientsQuery(Constants.INTERN_MOST_PATIENTS);
                return true;

            default:
                break;
        }

        return false;
    }

    private void sendMostPatientsQuery(String url)
    {
        mInterns = new ArrayList<>();
        mInternListAdapter.updateInternList(mInterns);
        new LoadInternsQueryTask(getActivity(),url).execute();

    }




    private void setUpSwipeRefresh() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary,
                R.color.colorPrimaryDark);

        swipeRefreshLayout.setOnRefreshListener(this);
    }





    private void initViews()
    {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.intern_listview);
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
        mInternListAdapter = new InternListAdapter(getActivity(),mInterns);
        recyclerView.setAdapter(mInternListAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private List<Intern> getInternsFromJson(String json)
    {
        List<Intern> interns =new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                String name = (String) jsonArray.get(i);
                Intern intern = new Intern(name);
                interns.add(intern);
            }

        }catch (JSONException j){
            Log.d("Exception",j.getMessage());
            return null;
        }

        return interns;
    }

    @Override
    public void onRefresh() {
        new LoadInternsTask(getActivity(),mUrl).execute();
    }

    private class LoadInternsTask extends AsyncTask<Void,Void,Boolean>
    {

        Context context;
        String url;
        public LoadInternsTask(Context context,String url)
        {
            this.context = context;
            this.url = url;

        }
        @Override
        protected void onPreExecute() {
            mImgPlaceHolder.setVisibility(View.GONE);
            mTvPlaceHolder.setVisibility(View.GONE);

            if (mInterns.size() == 0) { // check if any news are present
                mProgressBar.setVisibility(View.VISIBLE);

            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Rest rest = new Rest();
            String response = rest.sendGetRequest(url);
            Log.d("Interns",response);
            if(TextUtils.isEmpty(response))
            {
                return false;
            }
            mInterns = getInternsFromJson(response);

            if(mInterns==null || mInterns.size()==0)
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
                mInternListAdapter.updateInternList(mInterns);
            }

            if (mInterns==null || mInterns.size() == 0) {
                mImgPlaceHolder.setVisibility(View.VISIBLE);
                mTvPlaceHolder.setVisibility(View.VISIBLE);
            }
        }

    }

    private class LoadInternsQueryTask extends AsyncTask<Void,Void,Boolean>
    {

        Context context;
        String url;
        public LoadInternsQueryTask(Context context,String url)
        {
            Toast.makeText(context,"Querying",Toast.LENGTH_SHORT).show();
            this.context = context;
            this.url = url;

        }
        @Override
        protected void onPreExecute() {
            mImgPlaceHolder.setVisibility(View.GONE);
            mTvPlaceHolder.setVisibility(View.GONE);

            if (mInterns.size() == 0) { // check if any news are present
                mProgressBar.setVisibility(View.VISIBLE);

            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Rest rest = new Rest();
            String response = rest.sendGetRequest(url);
            Log.d("Interns",response);
            if(TextUtils.isEmpty(response))
            {
                return false;
            }
            mInterns = getInternsFromJson(response);

            if(mInterns==null || mInterns.size()==0)
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
                mInternListAdapter.updateInternList(mInterns);
            }

            if (mInterns==null || mInterns.size() == 0) {
                mImgPlaceHolder.setVisibility(View.VISIBLE);
                mTvPlaceHolder.setVisibility(View.VISIBLE);
            }
        }


    }
}
