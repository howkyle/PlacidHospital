package com.comp3161.placidandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comp3161.placidandroid.R;
import com.comp3161.placidandroid.models.MedicalData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stone on 4/5/17.
 */

public class MedicalDataListAdapter extends RecyclerView.Adapter<MedicalDataListAdapter.MedicalDataViewHolder> {


    public static class MedicalDataViewHolder extends RecyclerView.ViewHolder{

        TextView tvProcedure;
        TextView tvTestResult;
        TextView tvDiagnosis;
        TextView tvTreatment;

        public MedicalDataViewHolder(View view) {
            super(view);
            tvProcedure = (TextView) view.findViewById(R.id.procedure);
            tvTestResult = (TextView) view.findViewById(R.id.test_results);
            tvDiagnosis = (TextView) view.findViewById(R.id.diagnosis);
            tvTreatment = (TextView) view.findViewById(R.id.treatment);
        }
    }

    Context context;
    List<MedicalData> medicalDatas;

    public MedicalDataListAdapter(Context context, List<MedicalData> medicalDatas){
        this.context = context;
        this.medicalDatas = new ArrayList<>(medicalDatas);
    }

    @Override
    public MedicalDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_medical_data_item,
                parent,false);
        MedicalDataViewHolder medicalDataViewHolder = new MedicalDataViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return medicalDataViewHolder;
    }

    @Override
    public void onBindViewHolder(MedicalDataViewHolder holder, int position) {
        MedicalData medicalData = medicalDatas.get(position);

        String procedure = medicalData.getProcedure();
        String test_result = medicalData.getTestResults();
        String diagnosis = medicalData.getDiagnosis();
        String treatment = medicalData.getTreatment();

        holder.tvProcedure.setText(procedure);
        holder.tvTestResult.setText(test_result);
        holder.tvDiagnosis.setText(diagnosis);
        holder.tvTreatment.setText(treatment);
    }

    @Override
    public int getItemCount() {
        return medicalDatas.size();
    }

    public void updateMedicalDataList(List<MedicalData> medicalDatas){
        this.medicalDatas = new ArrayList<>(medicalDatas);
        notifyDataSetChanged();
    }
}
